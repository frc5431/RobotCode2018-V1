package frc.team5431.robot.vision;

import java.util.List;

import frc.team5431.robot.Titan;

public class CubeFinder {
	public static YoloReceiver receiver;
	public static final int NO_CUBE = 25;
	public static final int 
				imWidth = 320,
				imHeight = 240;
	
	public static boolean hasCubes() {
		return receiver.hasCubes();
	}
	
	public static Cube getClosestCube() {
		List<Cube> cubes = receiver.getCubes();
		int lArea = 0;
		int lind = 0;
		for(int ind = 0; ind < cubes.size(); ind++) {
			final int cArea = cubes.get(ind).getArea();
			if(cArea > lArea) {
				lArea = cArea;
				lind = ind;
			}
		}
		
		try {
			return cubes.get(lind);
		} catch(Throwable ignored) {
			return null;
		}
	}
	
	public static double fromCenter(Cube cube) {
		return cube.x - (imWidth / 2);
	}
	
	public static double scaledFromCenter(Cube cube) {
		return fromCenter(cube) / (imWidth / 2);
	}
	
	public static double getAngleFromClosestCube() {
		if(!hasCubes()) return NO_CUBE;
		final Cube closest = getClosestCube();
		if(closest == null) {
			Titan.e("FatalCubeNullError");
			return NO_CUBE;
		}
		final double fCenter = scaledFromCenter(closest);
		return fCenter * 30;
	}
	
	public static YoloReceiver getYoloReceiver() {
		return receiver;
	}
	
	public static void start() {
		receiver = new YoloReceiver();
		receiver.start();
	}
}
