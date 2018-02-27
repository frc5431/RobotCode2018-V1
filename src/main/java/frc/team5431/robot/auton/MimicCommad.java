package frc.team5431.robot.auton; //Change to allow negative distance values

import java.util.ArrayList;

import frc.team5431.robot.Constants;
import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;
import frc.team5431.robot.Titan.Command;
import frc.team5431.robot.components.DriveBase.TitanPIDSource;
import frc.team5431.robot.components.Intake.IntakeState;
import frc.team5431.robot.pathfinding.Mimic;
import frc.team5431.robot.pathfinding.Mimic.Stepper;
import frc.team5431.robot.vision.Vision;

public class MimicCommad extends Titan.Command<Robot> {
	public static enum Paths {
		RIGHT_SCALE
	}
	
	private int currentStep = 0;
	private int skippedSteps = 0;
	private double previousLeft = 0.0;
	private double previousRight = 0.0;
	private double futureLeft = 1.0;
	private double futureRight = 1.0;
	private final double integral = 0.05;
	private double futureLeftIntegral = 1.0;
	private double futureRightIntegral = 1.0;
	private final ArrayList<Stepper> steps;
	
	public MimicCommad(final Paths mimic) {
		name = "PathfindingStep";
		
		//Collect the mimic file
		Mimic.Repeater.prepare(mimic.toString().toLowerCase());
		steps = Mimic.Repeater.getData();
		
		properties = String.format("Steps %d", steps.size());
	}
	
	@Override
	public void init(final Robot robot) {
		robot.getDriveBase().setHome();
		double power = 0.0;
		double angle = 0.0;
		try {
			final Stepper step = steps.get(0);
			power = (step.leftPower + step.rightPower) / 2.0;
			angle = step.angle;
		} catch (Throwable ignored) {}
		robot.getDriveBase().driveAtAnglePID(power, angle, TitanPIDSource.NAVX_MIMIC, Vision.TargetMode.Normal);
	}

	@Override
	public CommandResult periodic(final Robot robot) {
		boolean nextStep = true;
		try {
			final Stepper step = steps.get(currentStep);
			
			if(step.isHome) {
				robot.getDriveBase().reset(); //Do not call setHome because that disables PID
			} else if(step.isSwitch) {
				robot.getIntake().shootCube();
				if(robot.getIntake().getState() != IntakeState.STAY_UP) {
					skippedSteps = 0;
					nextStep = false;
				}
			} else {
				final double power = (step.leftPower + step.rightPower) / 2.0;
				
				robot.getDriveBase().updateStepResults(power, step.angle);
				if(!robot.getDriveBase().hasTravelled(step.leftDistance) && !(Math.abs(power) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) {
					Titan.l("Mimic is falling behind!");
					nextStep = false;
				}
			}
		} catch (IndexOutOfBoundsException e) {}
		if(nextStep || skippedSteps > 5) {
			if(((currentStep++) + 1) > steps.size()) return CommandResult.COMPLETE;
			skippedSteps = 0;
		} else {
			skippedSteps++;
		}
		return CommandResult.IN_PROGRESS;
		/*try {
			Stepper step = steps.get(currentStep);
			if(!step.isDrive && !step.isTurn) {
				robot.getDriveBase().setHome();
			} else if(step.isDrive && !wasDrive) {
				robot.getDriveBase().driveAtAnglePID(step.drivePower, step.angle, TitanPIDSource.NAVX, Vision.TargetMode.Normal);
				nextStep = false;
			} else if(step.isDrive  && wasDrive) {
				robot.getDriveBase().updateStepResults(step.drivePower, step.angle);
				if(!robot.getDriveBase().hasTravelled(step.distance) && !(Math.abs(step.drivePower) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) {
					Titan.l("Pathfinding is falling behind!");
					nextStep = false;
				}
			} else if(step.isTurn && !wasTurn) {
				robot.getDriveBase().turnPID(step.angle, TitanPIDSource.NAVX, Vision.TargetMode.Normal);
				nextStep = false;
			} else if(step.isTurn && wasTurn) {
				robot.getDriveBase().updateStepResults(step.drivePower, step.angle);
				if(!robot.getDriveBase().hasTurned(step.angle) && !(Math.abs(step.angle) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_TURN)) {
					Titan.l("Pathfinding is falling behind!");
					nextStep = false;
				}
			} else {
				Titan.e("Invalid step command!");
			}
			
			wasDrive = step.isDrive;
			wasTurn = step.isTurn;
		} catch (IndexOutOfBoundsException e) {}
		if(nextStep) if(((currentStep++) + 1) > steps.size()) return StepResult.COMPLETE;*/
	}

	@Override
	public void done(final Robot robot) {
		robot.getDriveBase().disableAllPID();
		robot.getDriveBase().drive(0.0, 0.0);
	}
}

//FIX DUAL ENCODER <!>
/*public class MimicStep extends Step {
	public static enum Paths {
		RIGHT_SCALE
	}
	
	private int currentStep = 0;
	private int skippedSteps = 0;
	private double previousLeft = 0.0;
	private double previousRight = 0.0;
	private double futureLeft = 1.0;
	private double futureRight = 1.0;
	private final double integral = 0.05;
	private double futureLeftIntegral = 1.0;
	private double futureRightIntegral = 1.0;
	private final ArrayList<Stepper> steps;
	
	public MimicStep(final Paths mimic) {
		name = "PathfindingStep";
		
		//Collect the mimic file
		Mimic.Repeater.prepare(mimic.toString().toLowerCase());
		steps = Mimic.Repeater.getData();
		
		properties = String.format("Steps %d", steps.size());
	}
	
	@Override
	public void init(final Robot robot) {
		robot.getDriveBase().setHome();
	}

	@Override
	public StepResult periodic(final Robot robot) {
		boolean nextLeftStep = true;
		boolean nextRightStep = true;
		try {
			final Stepper step = steps.get(currentStep);
			final double lPower = (step.leftPower * futureLeft);
			final double rPower = (step.rightPower * futureRight);
			
			final double curLeft = robot.getDriveBase().getLeftDistance();
			final double curRight = robot.getDriveBase().getRightDistance();
			
			final boolean moveFLeft = (step.leftDistance > previousLeft) || (step.leftPower > 0);
			final boolean moveFRight = (step.rightDistance > previousRight) || (step.rightPower > 0);
			
			final double leftDisp = Math.abs(curLeft - step.leftDistance);
			final double rightDisp = Math.abs(curRight - step.rightDistance);
			
			if(moveFLeft) {
				if(curLeft < step.leftDistance) {
					Titan.l("Mimic left is falling behind!");
					nextLeftStep = false;
				}
			} else {
				if(curLeft > step.leftDistance) {
					Titan.l("Mimic right is too far ahead!");
					nextLeftStep = false;
				}
			}
			
			if(moveFRight) {
				if(curRight < step.rightDistance) {
					Titan.l("Mimic right is falling behind!");
					nextRightStep = false;
				}
			} else {
				if(curRight > step.rightDistance) {
					Titan.l("Mimic right is too far ahead!");
					nextRightStep = false;
				}
			}
			
			futureLeftIntegral = Math.min(integral * leftDisp, 0.1);
			futureRightIntegral = Math.min(integral * rightDisp, 0.1);
			
			if(!nextLeftStep && nextRightStep) {
 				futureLeft += futureLeftIntegral;
				futureRight -= futureRightIntegral;
			}///step.rightPower / (Math.abs(step.leftDistance - step.rightDistance) / 2.0);
			else if(nextLeftStep && !nextRightStep) {
				futureLeft -= futureLeftIntegral;
				futureRight += futureRightIntegral;
			} else if(nextLeftStep && nextRightStep){
				if(futureLeft < 1.0) futureLeft += 0.1; //((1 - futureLeft) * 0.5);
				else if(futureLeft > 1.0) futureLeft -= 0.1; //((1 - futureLeft) * 0.5);
				
				if(futureRight < 1.0) futureRight += 0.1; //((1 - futureRight) * 0.5);
				else if(futureRight > 1.0) futureRight -= 0.1; //((1 - futureRight) * 0.5);
			}
			
			if(futureLeft <= 0.3) futureLeft = 0.4;
			else if(futureLeft >= 1.6) futureLeft = 1.5;
			
			if(futureRight <= 0.3) futureRight = 0.4;
			else if(futureRight >= 1.6) futureRight = 1.5;
			
			Titan.l("FutureLeft %.2f", futureLeft);
			Titan.l("FutureRight: %.2f", futureRight);
			
			if(!nextLeftStep && 
					((Math.abs(step.leftPower) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) ||
						(Math.abs(step.leftDistance) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_DISTANCE)) { // && !(Math.abs(step.drivePower) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) {
				nextLeftStep = true;
			}
			
			if(!nextRightStep && 
					((Math.abs(step.rightPower) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) ||
						(Math.abs(step.rightDistance) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_DISTANCE)) { // && !(Math.abs(step.drivePower) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) {
				nextRightStep = true;
			}
			
			previousLeft = curLeft;
			previousRight = curRight;
			
			robot.getDriveBase().drive(lPower, rPower);
		} catch (IndexOutOfBoundsException e) {}
		if(nextLeftStep && nextRightStep || skippedSteps > 5) {
			if(((currentStep++) + 1) > steps.size()) return StepResult.COMPLETE;
			skippedSteps = 0;
		} else {
			skippedSteps++;
		}
		return StepResult.IN_PROGRESS;
		try {
			Stepper step = steps.get(currentStep);
			if(!step.isDrive && !step.isTurn) {
				robot.getDriveBase().setHome();
			} else if(step.isDrive && !wasDrive) {
				robot.getDriveBase().driveAtAnglePID(step.drivePower, step.angle, TitanPIDSource.NAVX, Vision.TargetMode.Normal);
				nextStep = false;
			} else if(step.isDrive  && wasDrive) {
				robot.getDriveBase().updateStepResults(step.drivePower, step.angle);
				if(!robot.getDriveBase().hasTravelled(step.distance) && !(Math.abs(step.drivePower) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED)) {
					Titan.l("Pathfinding is falling behind!");
					nextStep = false;
				}
			} else if(step.isTurn && !wasTurn) {
				robot.getDriveBase().turnPID(step.angle, TitanPIDSource.NAVX, Vision.TargetMode.Normal);
				nextStep = false;
			} else if(step.isTurn && wasTurn) {
				robot.getDriveBase().updateStepResults(step.drivePower, step.angle);
				if(!robot.getDriveBase().hasTurned(step.angle) && !(Math.abs(step.angle) < Constants.AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_TURN)) {
					Titan.l("Pathfinding is falling behind!");
					nextStep = false;
				}
			} else {
				Titan.e("Invalid step command!");
			}
			
			wasDrive = step.isDrive;
			wasTurn = step.isTurn;
		} catch (IndexOutOfBoundsException e) {}
		if(nextStep) if(((currentStep++) + 1) > steps.size()) return StepResult.COMPLETE;
	}

	@Override
	public void done(final Robot robot) {
		robot.getDriveBase().disableAllPID();
		robot.getDriveBase().drive(0.0, 0.0);
	}
}*/
