package frc.team5431.robot.pathfinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;

public class Collector {
	public static class Stepper {
		public double drivePower, distance, angle;
		public boolean isDrive, isTurn;
	}
	
	private static FileInputStream log = null;
	private static BufferedReader reader = null;
	private static final ArrayList<Stepper> pathData = new ArrayList<Stepper>();
	
	public static void init(final String fileName) {
		final String fName = "/media/sda1/" + fileName + ".pathfinding";
		try {
			Titan.l("Loading the pathfinding file");
			if(!Files.exists(new File(fName).toPath())) {
				Titan.e("The requested pathfinding data was not found");
			}
			
			log = new FileInputStream(fName);
			InputStreamReader iReader = new InputStreamReader(log, StandardCharsets.US_ASCII);
			reader = new BufferedReader(iReader);
			pathData.clear(); //Clear all of the pathData
			
			String line;
			while ((line = reader.readLine()) != null) {
				try {
					final String parts[] = line.split(";");
					final Stepper step = new Stepper();
					step.drivePower = getDouble(parts[0], 0.0);
					step.distance = getDouble(parts[1], 0.0);
					step.angle = getDouble(parts[2], 0.0);
					step.isDrive = getBoolean(parts[3], false);
					step.isTurn = getBoolean(parts[4], false);
					pathData.add(step);
				} catch (Exception e) {
					Titan.ee("PathfindingParser", e);
				}
			}
		    
			try {
				reader.close();
			} catch (Exception e) {
				Titan.ee("Failed to close the pathfinding file", e);
			}
			Titan.l("Loaded the pathfinding file");
		} catch (IOException e) {
			Titan.ee("Pathfinding", e);
		}
	}
	
	private static final double getDouble(final String data) {
		return getDouble(data, 0.0);
	}
	
	private static final double getDouble(final String data, double defaultValue) {
		try {
			return Double.parseDouble(data);
		} catch (Throwable e) {
			return defaultValue;
		}
	}
	
	private static final boolean getBoolean(final String data, boolean defaultValue) {
		try {
			return Integer.parseInt(data) == 1;
		} catch (Throwable e) {
			return defaultValue;
		}
	}
	
	public static ArrayList<Stepper> getData() {
		return pathData;
	}
}
