package frc.team5431.robot;

import frc.team5431.robot.Titan.Xbox.Axis;
import frc.team5431.robot.Titan.Xbox.Button;

public final class Teleop {
	private final Titan.Xbox driver;
	private Titan.Xbox operator;
	private final Titan.Toggle cubeCaptureToggle = new Titan.Toggle();

	public Teleop() {
		driver = new Titan.Xbox(0);
		driver.setDeadzone(0.1);

		operator = new Titan.Xbox(1);
		operator.setDeadzone(0.1);
	}

	public final void periodicDrive(final Robot robot) {
		robot.getDriveBase().drive(-driver.getRawAxis(Titan.Xbox.Axis.LEFT_Y),
				-driver.getRawAxis(Titan.Xbox.Axis.RIGHT_Y));
	}

	public final double[] periodicPathfindingDrive(final Robot robot) {
		final double vals[] = { -(driver.getRawAxis(Titan.Xbox.Axis.LEFT_Y)),
				-(driver.getRawAxis(Titan.Xbox.Axis.RIGHT_Y)) };
		robot.getDriveBase().drive(vals[0], vals[1]);
		return vals;
	}

	public final void periodicClimb(final Robot robot) {
		if (driver.getRawButton(Button.B)) {
			robot.getClimber().climb();
		} else {
			robot.getClimber().stopClimbing();
		}

		if (driver.getRawButton(Button.Y)) {
			robot.getClimber().scissorUpFast();
		} else if (driver.getRawButton(Button.X)) {
			robot.getClimber().scissorUpSlow();
		} else if(driver.getRawButton(Button.A)) {
			robot.getClimber().scissorDown();
		} else {
			robot.getClimber().stopScissor();
		}
	}

	public final void periodicIntake(final Robot robot) {
		if (operator.getRawAxis(Axis.TRIGGER_RIGHT) > 0.5) {
			robot.getIntake().captureCube();
		}

		if (operator.getRawAxis(Axis.TRIGGER_LEFT) > 0.5) {
			robot.getIntake().shootCube();
		}

		if (operator.getRawButton(Button.BUMPER_L)) {
			robot.getIntake().downRelease();
		}
		
		if(operator.getRawButton(Button.A)) {
			robot.getIntake().stayUp();
		}
		
		if(operator.getRawButton(Button.B)) {
			robot.getIntake().stayDown();
		}
		
		//if(operator.getRawButton(button))

		/*
		 * if(operator.getRawAxis(Axis.TRIGGER_RIGHT) > 0.5) {
		 * robot.getCatapult().shoot(); } else if(operator.getRawButton(Button.Y)) {
		 * robot.getCatapult().setLowering(true); } else {
		 * 
		 * robot.getCatapult().setLowering(false); robot.getCatapult().releaseShooter();
		 * }
		 */

		/*
		 * if(operator.getRawButton(Button.X)) { robot.getIntake().goDown(); }else
		 * if(operator.getRawButton(Button.START)) { robot.getIntake().goUp(); }else {
		 * robot.getIntake().stopUp(); }
		 */

		robot.getIntake().update(robot);
	}

	public final void periodicCatapult(final Robot robot) {
		if (operator.getRawButton(Button.X)) {
			robot.getCatapult().lowerCatapult();
		}

		if (operator.getRawButton(Button.BUMPER_R)) {
			robot.getCatapult().shoot();
		}

		robot.getCatapult().update(robot);
	}

	public Titan.Xbox getXbox() {
		return driver;
	}
}
