package frc.team5431.robot.auton;

import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;
import frc.team5431.robot.components.Intake.IntakeState;

public class SwitchCubeCommand extends Titan.Command<Robot>{
	@Override
	public void init(final Robot robot) {
		name = "SwitchCubeStep";
		properties = "Shooting cube into switch";
		
		//Start the intake shoot state machine
		robot.getIntake().shootCube();
		robot.getDriveBase().drive(-0.15, -0.15);
	}

	@Override
	public CommandResult periodic(final Robot robot) {
		//Check to see if the state machine is done
		robot.getDriveBase().drive(-0.15, -0.15);
		if(robot.getIntake().getState() == IntakeState.STAY_UP) {
			return CommandResult.COMPLETE;
		}
		return CommandResult.IN_PROGRESS;
	}

	@Override
	public void done(final Robot robot) {
		robot.getIntake().intakeStop();
		robot.getIntake().pinchSoft();
	}

}
