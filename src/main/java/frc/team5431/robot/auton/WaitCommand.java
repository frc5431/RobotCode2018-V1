package frc.team5431.robot.auton;

import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;

public class WaitCommand extends Titan.Command<Robot> {

	private final long durationMS;
	private long startTime;
	
	public WaitCommand(final long ms) {
		name = "WaitStep";
		properties = String.format("Millis %d", ms);
		durationMS = ms;
	}

	@Override
	public void init(final Robot robot) {
		startTime = System.currentTimeMillis();
	}

	@Override
	public CommandResult periodic(final Robot robot) {
		if (System.currentTimeMillis() >= startTime + durationMS) {
			return CommandResult.COMPLETE;
		}

		return CommandResult.IN_PROGRESS;
	}

	@Override
	public void done(final Robot robot) {
	}

}
