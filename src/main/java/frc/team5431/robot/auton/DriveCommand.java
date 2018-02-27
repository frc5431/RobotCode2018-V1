package frc.team5431.robot.auton;

import frc.team5431.robot.Constants;
import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;
import frc.team5431.robot.Titan.Command.CommandResult;
import frc.team5431.robot.components.DriveBase;
import frc.team5431.robot.vision.Vision;

public class DriveCommand extends Titan.Command<Robot> {
	private final double distance, angle, speed;

	public DriveCommand(final double dis) {
		this(dis, 0.0);
	}

	public DriveCommand(final double dis, final double ang) {
		this(dis, ang, Constants.AUTO_ROBOT_DEFAULT_SPEED);
	}

	public DriveCommand(final double dis, final double ang, final double spd) {
		distance = dis;
		angle = ang;

		if (distance < 0)
			speed = -spd;
		else
			speed = spd;

		name = "DriveStep";
		properties = String.format("Distance %.2f : Heading %.2f", distance, angle);
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public CommandResult periodic(final Robot robot) {
		if (robot.getDriveBase().hasTravelled(distance)) {
			return CommandResult.COMPLETE;
		}
		return CommandResult.IN_PROGRESS;
	}

	@Override
	public void init(final Robot robot) {
		robot.getDriveBase().reset();
		robot.getDriveBase().drivePID(distance, angle, speed, DriveBase.TitanPIDSource.NAVX, Vision.TargetMode.Normal);
	}

	@Override
	public void done(final Robot robot) {
		robot.getDriveBase().disableAllPID();
		robot.getDriveBase().drive(0.0, 0.0);
	}
}
