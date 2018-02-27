package frc.team5431.robot.auton;

import java.util.ArrayList;
import java.util.List;

import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;
import frc.team5431.robot.components.DriveBase;

public class TurnCommand extends Titan.Command<Robot> {
	private static final double minimumDegree = 20.0;
	private static final double degreeChunkSize = 90.0;
	private double degrees;
	private boolean tooSmall = false;
	private List<Double> degreeChunks = new ArrayList<Double>();
	private int currentChunk = 0;
	
	public TurnCommand(final double deg) { // TODO MAKE THE TURNING MORE SMOOTH BY OVERRIDING THE NAVX ANGLE WITH THE CURRENT MAXIMUM CHUNK! BECAUSE THE BOT SLOWS DOWN AND STOPS...
		degrees = deg;
		name = "TurnStep";
		properties = String.format("Angle %.2f", degrees);
		
		while(degrees > degreeChunkSize) {
			degreeChunks.add(degreeChunkSize);
			degrees -= degreeChunkSize;
		}
		
		while(degrees < -degreeChunkSize) {
			degreeChunks.add(-degreeChunkSize);
			degrees += degreeChunkSize;
		}
		
		if((degrees < 0.0 && degrees > -5.0) || (degrees > 0.0 && degrees < 5.0)) tooSmall = true;
		
		degreeChunks.add(degrees);
		for(double degs : degreeChunks) {
			Titan.l("mooooved %.2f", degs);
		}
	}
	
	@Override
	public void init(final Robot robot) {
		robot.getDriveBase().reset();
		final double turnAmt;
		if(degreeChunks.size() > 1) turnAmt = degreeChunkSize;
		else if(degreeChunks.size() == 1) turnAmt = degreeChunks.get(0);
		else if(tooSmall) turnAmt = minimumDegree;
		else turnAmt = degrees;
		robot.getDriveBase().turnPID(turnAmt, DriveBase.TitanPIDSource.NAVX);
	}

	@Override
	public CommandResult periodic(final Robot robot) {
		final double turnDeg = (double) degreeChunks.get(currentChunk);
		if(robot.getDriveBase().hasTurned(turnDeg)) {
			currentChunk++;
			if((currentChunk + 1) > degreeChunks.size()) return CommandResult.COMPLETE;
			final double newTurnDeg = degreeChunks.get(currentChunk);
			robot.getDriveBase().disableAllPID();
			robot.getDriveBase().turnPID(newTurnDeg, DriveBase.TitanPIDSource.NAVX);
		}
		return CommandResult.IN_PROGRESS;
	}

	@Override
	public void done(final Robot robot) {
		robot.getDriveBase().disableAllPID();
		robot.getDriveBase().drive(0.0, 0.0);
		//Are you ready for my Chicken AlGhoul?
	}
}
