package frc.team5431.robot.vision;

import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import frc.team5431.robot.Constants;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	public static CubeDetector cubeDetector;
	public static UsbCamera camera;
	public static CvSink cv;
	//public static CvSource proc;
	public static Mat image;
	//public static MjpegServer server;
	//public static VideoSource source;
	public static boolean 
			visionTargetFound = false, 
			initialized = false;
	public static double 
			visionAngle = 0, 
			visionDistance = 0;
	
	public static enum TargetMode {
		Normal, Cube
	}
	
	public static TargetMode targetting = TargetMode.Normal;
	
	public final static void init() {
		if(initialized) return;
		cubeDetector = new CubeDetector();
		image = new Mat();
		
		camera.setResolution(Constants.Vision.IMAGE_WIDTH, Constants.Vision.IMAGE_HEIGHT);
		camera.setFPS(Constants.Vision.FPS);
		cv = CameraServer.getInstance().getVideo();
		CameraServer.getInstance().addServer("source", 1180).setSource(camera);
		/*
		proc = CameraServer.getInstance().putVideo("processed", Constants.Vision.IMAGE_WIDTH, Constants.Vision.IMAGE_HEIGHT);
		proc.putFrame(Mat.zeros(Constants.Vision.IMAGE_HEIGHT, Constants.Vision.IMAGE_WIDTH, CvType.CV_8UC3)); //Put a blank frame
		proc.setFPS(10);
		proc.setConnected(true);
		proc.setDescription("test");
		*/
		
		initialized = true;
	}
	
	public final static void setCamera(UsbCamera c) {
		camera = c;
	}
	
	public final static void setCameraNormal() {
		camera.setBrightness(50);
		camera.setExposureManual(30);
	}
	
	public final static void setCameraPeg() {
		camera.setBrightness(0);
		camera.setExposureManual(0);
	}
	
	public final static void setCameraGear() {
		camera.setBrightness(10);
		camera.setExposureManual(25);
	}
	
    public final static Rect getRectangle(final MatOfPoint contour) {
    	return Imgproc.boundingRect(contour);
    }
    
    public final static double getCenterX(final Rect boundingBox) {
    	return boundingBox.x + (boundingBox.width / 2);
    }
    
    public final static double getCenterY(final Rect boundingBox) {
    	return boundingBox.y + (boundingBox.height / 2);
    }
    
    public final static boolean inSameSpot(final Rect parent, final Rect child) {
    	return (getCenterX(parent) == getCenterX(child)) && (getCenterY(parent) == getCenterY(child));
    }
    
    public final static double getArea(final MatOfPoint contour) {
    	return Imgproc.contourArea(contour);
    }
	
    private final static void processCubeFrame() {
    	cv.grabFrame(image);
    	if(image != null) {
    		if(!image.empty()) {
    			cubeDetector.process(image);
    			image = cubeDetector.cvDilateOutput(); //Set the output
    			final List<MatOfPoint> cPoints = cubeDetector.filterContoursOutput();
    			if(cPoints.size() == 0) {
    				visionTargetFound = false;
    				return;
    			}
    			
    			MatOfPoint largest = cPoints.get(0);
    			for(final MatOfPoint ps : cPoints) {
    				if(getArea(ps) > getArea(largest)) {
    					largest = ps;
    				}
    			}
    			
    			final Rect cubeRect = getRectangle(largest);
    			final double centerX = getCenterX(cubeRect);
    			visionAngle = Constants.Vision.getHorzAngle(centerX);
    			SmartDashboard.putNumber("CubeAngle", visionAngle);
    			visionDistance = getCenterY(cubeRect);
    			visionTargetFound = true;
    		} else {
    			visionTargetFound = false;
    		}
    	} else {
    		visionTargetFound = false;
    	}
    	
    	SmartDashboard.putBoolean("FoundCube", visionTargetFound);
    }
    
    /*
    public static void processGearFrame() {
    	cv.grabFrame(image);
    	if(image != null)
    	{
    		if(!image.empty()) {
    			gripGear.process(image);
    			List<MatOfPoint> contourPoints = gripGear.filterContoursOutput();
    			
    			if(contourPoints.size() == 0) {
    				visionTargetFound = false;
    				return;
    			}
    			
    			MatOfPoint largestMat = contourPoints.get(0);
    			for(MatOfPoint object : contourPoints) {
    				if(getArea(object) > getArea(largestMat)) {
    					largestMat = object;
    				}
    			}
    			
    			Rect gearRect = getRectangle(largestMat);
    			double centerX = getCenterX(gearRect);
    			double fromCenter = Constants.Vision.fromCenter(centerX);
    			visionAngle = Constants.Vision.angleFromCenter(fromCenter);
    			visionDistance = getCenterY(gearRect);
    			visionTargetFound = true;
    		} else {
    			visionTargetFound = false;
    		}
    	} else {
    		visionTargetFound = false;
    	}
    	
		SmartDashboard.putBoolean("FoundGear", visionTargetFound);
    }
    
    public static void processPegFrame() {
    	cv.grabFrame(image);
    	if(image != null)
    	{
    		if(!image.empty()) {
    			grip.process(image);
    			List<MatOfPoint> contourPoints = grip.filterContoursOutput();
    			
    			Rect leftPeg = new Rect(), rightPeg = new Rect();
    			double centerX = 666;
    			
    			if(contourPoints.size() < 2) {
    				SmartDashboard.putBoolean("ContoursFound", false);
    				visionTargetFound = false;
    				return;
    			}
    			
    			SmartDashboard.putBoolean("ContoursFound", true);
    			
    			for(MatOfPoint parent : contourPoints) {
    				Rect parentBox = getRectangle(parent);
    				double parentX = getCenterX(parentBox);
    				double parentY = getCenterY(parentBox);
    				
    				for(MatOfPoint child : contourPoints) {
    					Rect childBox = getRectangle(child);
    					if(inSameSpot(parentBox, childBox)) continue;
    					double childX = getCenterX(childBox);
    					double childY = getCenterY(childBox);
    					
    					if(Math.abs(parentY - childY) < 10) {
    						SmartDashboard.putBoolean("PegFound", true);
    						centerX = (parentX + childX) / 2;
    						if(parentX < childX) {
    							leftPeg = parentBox;
    							rightPeg = childBox;
    						} else {
    							leftPeg = childBox;
    							rightPeg = parentBox;
    						}
    						visionTargetFound = true;
    						break;
    					} else {
    						SmartDashboard.putBoolean("PegFound", false);
    					}
    				}
    			}
    			
    			double fromCenter = Constants.Vision.fromCenter(centerX);
    			visionAngle = Constants.Vision.angleFromCenter(fromCenter);
    			visionDistance = Math.abs(getCenterX(leftPeg) - getCenterX(rightPeg));
    			
    	    	SmartDashboard.putNumber("PegPairCenterX", fromCenter);
    	    	SmartDashboard.putNumber("PegHorzAngle", visionAngle);
    	    	SmartDashboard.putNumber("PegDisplacement", visionDistance);
    		} else {
    	    	visionTargetFound = false;
    	    }
    	} else {
    		visionTargetFound = false;
    	}
    }
    
    public static void useAngleFromCamera() {
    	DriveBasePIDSource.inputType = DriveBasePIDSource.InputType.Vision;
    }
    
    public static void useAngleFromNavx() {
    	DriveBasePIDSource.inputType = DriveBasePIDSource.InputType.Navx;
    }*/
    
    public final static boolean isOnTarget() {
    	return visionDistance > 40; //48
    }
    
    public final static boolean foundTarget() {
    	return visionTargetFound;
    }
    
    public final static double getAngle() {
    	return visionAngle;
    }
    
    public final static void setTargetMode(TargetMode mode) {
    	targetting = mode;
    }
    
    public final static void setCubeTargetMode() {
    	targetting = TargetMode.Cube;
    }
    
    public final static void setNormalTargetMode() {
    	targetting = TargetMode.Normal;
    }
    
    /*
    public static void stopAllVision() {
    	DriveBase.disablePID();
    	DriveBase.setPIDNormal();
    	Vision.useAngleFromNavx();
    	Vision.setNormalTargetMode();
    }*/
    
    public final static void periodic() {
    	switch(targetting) {
    	case Normal:
    		SmartDashboard.putBoolean("ProcessingCube", false);
    		break;
    	case Cube:
    		SmartDashboard.putBoolean("ProcessingCube", true);
    		processCubeFrame();
    	}
    	
    	/*if(image != null) {
    		if(!image.empty()) proc.putFrame(image);
    	}*/
    	SmartDashboard.putString("TargettingMode", targetting.toString());
    }
	
}

