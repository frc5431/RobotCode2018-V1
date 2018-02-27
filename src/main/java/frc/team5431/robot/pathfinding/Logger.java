package frc.team5431.robot.pathfinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;

public class Logger {
	private static FileOutputStream log = null;
	private static boolean saved = true;
	
	public static void init(final String fileName) {
		final String fName = "/media/sda1/" + fileName + ".pathfinding";
		try {
			if(Files.deleteIfExists(new File(fName).toPath())) {
				Titan.e("Deleted previous pathfinding data");
			}
			log = new FileOutputStream(fName);
			saved = false;
			Titan.l("Created new pathfinding file");
		} catch (IOException e) {
			Titan.ee("Pathfinding", e);
		}
	}
	
	public static void addData(final Robot robot, final double driveVals[]) {
		try {
			double distance = robot.getDriveBase().getLeftDistance();
			double drivePower = (driveVals[0] + driveVals[1]) / 2.0;
			float angle = robot.getDriveBase().getNavx().getYaw();
			int isDrive = (robot.getTeleop().getXbox().getRawAxis(Titan.Xbox.Axis.LEFT_Y) > 0.5) ? 1 : 0;
			int isTurn = (robot.getTeleop().getXbox().getRawAxis(Titan.Xbox.Axis.RIGHT_Y) > 0.5) ? 1 : 0;
			if(isTurn == 0 && isDrive == 0) {
				robot.getDriveBase().setHome(); //RESET EVERYTHING
			}
			if(!saved) log.write(String.format("%.4f;%.2f;%.2f;%d;%d;\n", drivePower, distance, angle, isDrive, isTurn).getBytes(StandardCharsets.US_ASCII));
		} catch (Exception e) {
			Titan.ee("Pathfinding", e);
		}
	}
	
	public static void saveData() {
		try {
			if(log == null || saved) return;
			Titan.l("Finished pathfinding");
			log.flush();
			log.close();
			saved = true;
			Titan.l("Saved the pathfinding data");
		} catch (IOException e) {
			Titan.ee("Pathfinding", e);
		}
	}
}
