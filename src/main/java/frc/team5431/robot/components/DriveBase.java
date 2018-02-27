package frc.team5431.robot.components;

import frc.team5431.robot.Constants;
import frc.team5431.robot.Titan;
import frc.team5431.robot.TitanNavx;
import frc.team5431.robot.vision.Vision;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
	public enum TitanPIDSource {
		NAVX, VISION, NAVX_MIMIC,  __DO_NOT_CHANGE
	}
	
	public enum TitanPIDValues {
		NORMAL, VISION, MIMIC
	}
	
	public enum TitanPIDType {
		DRIVE_FORWARD, TURN, PIVOT, NONE;
	}

	private final WPI_TalonSRX frontLeft, frontRight, middleLeft, middleRight, backLeft, backRight;
	//private final Encoder leftEncoder, rightEncoder;
	private final TitanNavx navx = new TitanNavx();

	private TitanPIDSource pidSourceType = TitanPIDSource.NAVX;
	private TitanPIDType pidType = TitanPIDType.NONE;
	private TitanPIDValues pidValues = TitanPIDValues.NORMAL;
	private double wantedAngle = 0.0;
	private double wantedDistance = 0.0;
	private double currentPower = 0.0;
	private boolean pidPower = false;
	private final DriveBasePIDSource pidSource = new DriveBasePIDSource();
	private final DriveBasePIDOutput pidOutput = new DriveBasePIDOutput();
	private final DriveBasePIDDistanceSource pidDistanceSource = new DriveBasePIDDistanceSource();
	private final DriveBasePIDDistanceOutput pidDistanceOutput = new DriveBasePIDDistanceOutput();
	public PIDController drivePID = new PIDController(0, 0, 0, 0, pidSource, pidOutput);
	public final PIDController distancePID = new PIDController(Constants.DRIVE_DISTANCE_P, Constants.DRIVE_DISTANCE_I,
			Constants.DRIVE_DISTANCE_D, 0, pidDistanceSource, pidDistanceOutput);
	
	public class DriveBasePIDSource implements PIDSource {
		PIDSourceType filler = PIDSourceType.kDisplacement;

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			filler = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return filler;
		}

		@Override
		public double pidGet() { 
			if(pidType == TitanPIDType.NONE) return wantedAngle; //This fixes the I going out of control problem (DO NOT TOUCH)
			
			switch(pidSourceType) {
			case NAVX_MIMIC:
			case NAVX:
				SmartDashboard.putNumber("yaw", navx.getYaw());
				return navx.getYaw();
			case VISION:
				//IF WE WANT TO USE YOLO (USE THE BELOW FINDER)
				//angle = CubeFinder.getAngleFromClosestCube();
				return Vision.getAngle();
				/*if(angle < -14) {
					angle = -14;
				} else if(angle > 14) {
					angle = 14;
				}*/
			case __DO_NOT_CHANGE:
				SmartDashboard.putString("FatalPIDError", "The PID source was not set!");
				break;
			default:
				break;
			}
			return 0.0;
		}
	}
	
	public class DriveBasePIDOutput implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			
			
			switch(pidType) {
			case DRIVE_FORWARD:
				/*if(output > 0) {
					drive(currentPower, currentPower - output);
				} else {
					drive(currentPower + output, currentPower);
				}*/
				drive(currentPower + output, currentPower - output);
				//SmartDashboard.putNumber("want", currentPower);
				SmartDashboard.putNumber("output", output);
				/*if (output > 0) {
					drive(currentPower + output, currentPower);
				} else {
					drive(currentPower, currentPower);
				}*/ //OLD CODE
				//drive(currentPower - output, currentPower + output); 
				/*boolean drive = true;
				if(pidSourceType == TitanPIDSource.VISION) {
					//drive = Vision.foundTarget();
					if(!drive) drive(0, 0); //Don't drive if there's no target
				} 
				
				if (drive && output > 0) drive(currentPower, currentPower - output); //, currentPower - output);
				else if (drive && output <= 0) drive(currentPower + output, currentPower);*/
				/*if(currentPower < 0) {
					drive(currentPower - output, currentPower + output);
				} else {
					drive(currentPower + output, currentPower - output);
				}*/
				break;
			case TURN:
				SmartDashboard.putNumber("output", output);
				/*if(output < 0 && output > -Constants.TURN_MIN_VALUE) {
					output = -Constants.TURN_MIN_VALUE;
				} else if(output > 0 && output < Constants.TURN_MIN_VALUE) {
					output = Constants.TURN_MIN_VALUE;
				} */
				drive(output, -output);
				break;
			case PIVOT:
				SmartDashboard.putNumber("output", output);
				if (wantedAngle < 0) { 
					//double encDist = leftEncoder.getDistance(); 
					//currentPower = encDist / 5;
					//double cPower = within(encDist, 1.5) ? 0.0 : currentPower;
					drive(-Constants.PIVOT_DISTANCE_SCALING, output); //-currentPower * Constants.PIVOT_DISTANCE_SCALING, output);
				} else {
					//double encDist = rightEncoder.getDistance(); //@TODO CHANGE TO RIGHT ENCODER WHEN FIXED
					//currentPower = encDist / 5;
					//double cPower = within(encDist, 1.5) ? 0.0 : currentPower;
					drive(-output, -Constants.PIVOT_DISTANCE_SCALING); //currentPower * Constants.PIVOT_DISTANCE_SCALING);
				}
				break;
			case NONE:
				return;
			}
		}
	}

	public class DriveBasePIDDistanceSource implements PIDSource {
		PIDSourceType filler = PIDSourceType.kDisplacement;

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			filler = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return filler;
		}

		@Override
		public double pidGet() {
			/*if(pidType == TitanPIDType.PIVOT) {
				if(wantedAngle < 0) {
					return leftEncoder.getDistance();
				} else {
					return rightEncoder.getDistance();
				}
			} else */
			double distance = getLeftDistance();
			SmartDashboard.putNumber("encoderPosition", distance);
			if(wantedDistance > 0 && (wantedDistance - distance) > Constants.DRIVE_DISTANCE_MAX_OFFSET) {
				return Constants.DRIVE_DISTANCE_MAX_OFFSET;
			} else if(wantedDistance < 0 && (Math.abs(wantedDistance) - Math.abs(distance)) > Constants.DRIVE_DISTANCE_MAX_OFFSET) {
				return -Constants.DRIVE_DISTANCE_MAX_OFFSET;
			}
			return distance;
			//return leftEncoder.getDistance(); //@TODO REMOVE THIS
		}
	}

	public class DriveBasePIDDistanceOutput implements PIDOutput {

		@Override
		public void pidWrite(final double output) {
			if(pidPower) currentPower = output;
		}

	}
	
	public DriveBase() {
		/*
		 * INITIALIZE THE TALONS
		 */
		frontLeft = new WPI_TalonSRX(Constants.TALON_FRONT_LEFT_ID);
		middleLeft = new WPI_TalonSRX(Constants.TALON_MIDDLE_LEFT_ID);
		backLeft = new WPI_TalonSRX(Constants.TALON_BACK_LEFT_ID);
		frontRight = new WPI_TalonSRX(Constants.TALON_FRONT_RIGHT_ID);
		middleRight = new WPI_TalonSRX(Constants.TALON_MIDDLE_RIGHT_ID);
		backRight = new WPI_TalonSRX(Constants.TALON_BACK_RIGHT_ID);

		/*
		 * SET THE DIRECTION OF THE TALON
		 */
		frontLeft.setInverted(Constants.TALON_FRONT_LEFT_INVERTED);
		middleLeft.setInverted(Constants.TALON_MIDDLE_LEFT_INVERTED);
		backLeft.setInverted(Constants.TALON_BACK_LEFT_INVERTED);
		frontRight.setInverted(Constants.TALON_FRONT_RIGHT_INVERTED);
		middleRight.setInverted(Constants.TALON_MIDDLE_RIGHT_INVERTED);
		backRight.setInverted(Constants.TALON_BACK_RIGHT_INVERTED);

		/*
		 * MASTER-SLAVE RELATIONSHIPS
		 */
		//Left side
		//backLeft.follow(frontLeft);
		//middleLeft.follow(frontLeft);
		//backLeft.set(ControlMode.Follower, Constants.TALON_FRONT_LEFT_ID);
		//middleLeft.set(ControlMode.Follower, Constants.TALON_FRONT_LEFT_ID);

		//Right side
		//backRight.set(ControlMode.Follower, frontRight.getDeviceID());
		//middleRight.set(ControlMode.Follower, frontRight.getDeviceID());

		/*
		 * ENCODER DEFINITIONS
		 */
		//Left side
		middleLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		middleLeft.setSensorPhase(false);
		middleLeft.setSelectedSensorPosition(0, 0, 0);
		
		//Right side
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		frontRight.setSensorPhase(false);
		frontRight.setSelectedSensorPosition(0, 0, 0);
		
		/*//Left side
		leftEncoder = new Encoder(Constants.ENCODER_LEFT_DRIVE_CHANNEL_1, Constants.ENCODER_LEFT_DRIVE_CHANNEL_2, false, EncodingType.k4X);
		leftEncoder.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
		leftEncoder.setSamplesToAverage(Constants.ENCODER_SAMPLES_TO_AVERAGE);
		leftEncoder.setReverseDirection(Constants.ENCODER_LEFT_DRIVE_INVERTED);
		
		//Right side
		rightEncoder = new Encoder(Constants.ENCODER_RIGHT_DRIVE_CHANNEL_1, Constants.ENCODER_RIGHT_DRIVE_CHANNEL_2, false, EncodingType.k4X);
		rightEncoder.setDistancePerPulse(Constants.ENCODER_DISTANCE_PER_PULSE);
		rightEncoder.setSamplesToAverage(Constants.ENCODER_SAMPLES_TO_AVERAGE);
		rightEncoder.setReverseDirection(Constants.ENCODER_RIGHT_DRIVE_INVERTED);
		*/
		
		/*
		 * PID DEFINITIONS
		 */
		//Turning PID
		drivePID.setInputRange(-90, 90);
		drivePID.setOutputRange(-1, 1);
		drivePID.setAbsoluteTolerance(0.5);
		drivePID.setContinuous(true);

		
		//Distance PID
		distancePID.setInputRange(-90, 90);
		distancePID.setOutputRange(-0.5, 0.5);
		distancePID.setAbsoluteTolerance(0.5);
		distancePID.setContinuous(true);
		
		disableAllPID();
	}
	
	public void setBrakeMode(final boolean brake) {
		final NeutralMode mode;
		if (brake) {
			mode = NeutralMode.Brake;
		} else {
			mode = NeutralMode.Coast;
		}
		frontLeft.setNeutralMode(mode);
		middleLeft.setNeutralMode(mode);
		backLeft.setNeutralMode(mode);
		frontRight.setNeutralMode(mode);
		middleRight.setNeutralMode(mode);
		backRight.setNeutralMode(mode);
	}

	public final void disableAllPID() {
		pidType = TitanPIDType.NONE;
		//drivePID.reset();
		//distancePID.reset();
		drivePID.disable();
		//distancePID.disable();
	}
	
	public final void setNavxSource() {
		pidSourceType = TitanPIDSource.NAVX;
	}

	
	public final void setVisionSource() {
		pidSourceType = TitanPIDSource.VISION;
	}
	
	public final void setPIDValues(TitanPIDValues values) {
		pidValues = values;
	}

	public final void drive(final double left, final double right) {
		frontLeft.set(left);
		middleLeft.set(left);
		backLeft.set(left);
		frontRight.set(right);
		middleRight.set(right);
		backRight.set(right);
	}
	
	public final void setFullSource(final TitanPIDSource source, boolean disableCurrent, Vision.TargetMode mode) {
		if(disableCurrent) {
			disableAllPID();
			reset();
		}
		
		switch(source) {
		case NAVX:
			Vision.setTargetMode(mode);
			setNavxSource();
			setPIDValues(TitanPIDValues.NORMAL);
			break;
		case VISION:
			Vision.setTargetMode(mode);
			setVisionSource();
			setPIDValues(TitanPIDValues.VISION);
			break;
		case NAVX_MIMIC:
			Vision.setTargetMode(mode);
			setNavxSource();
			setPIDValues(TitanPIDValues.MIMIC);
		case __DO_NOT_CHANGE:
			break;
		}
	}
	
	public final void setDrivePIDValues() {
		switch(pidValues) {
		case NORMAL:
			drivePID.setPID(Constants.DRIVE_HEADING_P, Constants.DRIVE_HEADING_I, Constants.DRIVE_HEADING_D, 0.0);
			drivePID.setOutputRange(-Constants.DRIVE_HEADING_MIN_MAX, Constants.DRIVE_HEADING_MIN_MAX);
			SmartDashboard.putString("PIDValues", "Normal");
			break;
		case VISION:
			drivePID.setPID(Constants.VISION_P, Constants.VISION_I, Constants.VISION_D, 0.0);
			drivePID.setOutputRange(-1, 1);
			SmartDashboard.putString("PIDValues", "Vision");
			break;
		case MIMIC:
			drivePID.setPID(Constants.DRIVE_MIMIC_P, Constants.DRIVE_MIMIC_I, Constants.DRIVE_MIMIC_D, 0.0);
			drivePID.setOutputRange(-Constants.DRIVE_MIMIC_MIN_MAX, Constants.DRIVE_MIMIC_MIN_MAX);
			SmartDashboard.putString("PIDValues", "Mimic");
			break;
		}
	}
	
	public final void setTurnPIDValues() {
		drivePID.setPID(Constants.TURN_P, Constants.TURN_I, Constants.TURN_D, 0.0);
		drivePID.setOutputRange(-1, 1);
	}
	
	public final void driveAtAnglePID(final double speed, final double angle) {
		driveAtAnglePID(speed, angle, TitanPIDSource.__DO_NOT_CHANGE, Vision.TargetMode.Normal);
	}
	
	public final void driveAtAnglePID(final double speed, final double angle, final TitanPIDSource source, final Vision.TargetMode mode) {
		setFullSource(source, true, mode);
		pidType = TitanPIDType.DRIVE_FORWARD;
		pidPower = false;
		currentPower = speed;
		wantedAngle = angle;
		
		drivePID.reset();
		drivePID.setSetpoint(0);
		setDrivePIDValues();
		//drivePID.setOutputRange(-Constants.DRIVE_HEADING_MIN_MAX, Constants.DRIVE_HEADING_MIN_MAX);
		drivePID.setSetpoint(angle);
		drivePID.enable();
	}
	
	public final void updateStepResults(final double speed, final double angle) {
		currentPower = speed;
		wantedAngle = angle;
		drivePID.setSetpoint(angle);
	}
	
	public final void drivePID(final double distance) {
		drivePID(distance, 0.0);
	}
	
	public final void drivePID(final double distance, final double angle) {
		drivePID(distance, angle, TitanPIDSource.__DO_NOT_CHANGE);
	}
	
	public final void drivePID(final double distance, final double angle, final TitanPIDSource source) {
		drivePID(distance, angle, Constants.AUTO_ROBOT_DEFAULT_SPEED, source, Vision.TargetMode.Normal);
	}
	
	
	public final void drivePID(final double distance, final double angle, final double spd, final TitanPIDSource source, final Vision.TargetMode mode) {
		setFullSource(source, true, mode);
		pidType = TitanPIDType.DRIVE_FORWARD;
		pidPower = true;
		wantedAngle = angle;
		wantedDistance = distance;
		currentPower = spd;
		
		drivePID.reset();
		drivePID.setSetpoint(0);
		setDrivePIDValues();
		drivePID.setSetpoint(angle);
		
		//distancePID.reset();
		//distancePID.setSetpoint(distance);
		
		drivePID.enable();
		//distancePID.enable();
	}

	public final void turnPID(final double angle) {
		turnPID(angle, TitanPIDSource.__DO_NOT_CHANGE);
	}
	
	public final void turnPID(final double angle, final TitanPIDSource source) {
		turnPID(angle, source, Vision.TargetMode.Normal);
	}
	
	public final void turnPID(final double angle, final TitanPIDSource source, final Vision.TargetMode mode) {
		setFullSource(source, true, mode);
		pidType = TitanPIDType.TURN;
		pidPower = false;
		wantedAngle = angle;
		wantedDistance = 0;
		
		drivePID.reset();
		drivePID.setSetpoint(0);
		setTurnPIDValues();
		drivePID.setSetpoint(angle);
		drivePID.enable();
	}
	
	public final void pivotPID(final double angle) {
		pidType = TitanPIDType.PIVOT;
		wantedAngle = angle;
		currentPower = 0.0;
		
		drivePID.reset();
		drivePID.setSetpoint(0);
		setTurnPIDValues();
		drivePID.setSetpoint(angle);
		
		distancePID.reset();
		distancePID.setSetpoint(0);
		
		drivePID.enable();
		distancePID.enable();
	}
	
	public TitanNavx getNavx() {
		return navx;
	}
	
	public final double getLeftDistance() {
		return middleLeft.getSelectedSensorPosition(0) * Constants.ENCODER_DISTANCE_PER_PULSE;

	}
	
	public final double getRightDistance() {
		return frontRight.getSelectedSensorPosition(0) * Constants.ENCODER_DISTANCE_PER_PULSE;

	}	
	
	public final boolean hasTravelled(final double wantedDistance) {
		return hasTravelled(wantedDistance, true);
	}
	
	public final boolean hasTravelled(final double wantedDistance, final boolean isLeft) {
		if (wantedDistance < 0) {
			return ((isLeft) ? getLeftDistance() : getRightDistance()) <= wantedDistance;
		} else {
			return ((isLeft) ? getLeftDistance() : getRightDistance()) >= wantedDistance;
		}
		/*if(wantedDistance < 0) {
			return leftEncoder.getDistance() < wantedDistance || rightEncoder.getDistance() < wantedDistance;
		} else {
			return leftEncoder.getDistance() > wantedDistance || rightEncoder.getDistance() > wantedDistance;
		}*/
	}
	
	public final boolean hasTurned(final double wantedAngle) {
		return Titan.approxEquals(wantedAngle, navx.getYaw(), Constants.TURN_PRECISION);
	}

	public final void resetNavx() {
		navx.reset();
		navx.resetDisplacement();
		navx.resetYaw();
	}

	public final void resetEncoders() {
		middleLeft.setSelectedSensorPosition(0, 0, 0);
		frontRight.setSelectedSensorPosition(0, 0, 0);
		//leftEncoder.reset();
		//rightEncoder.reset();
	}

	public final void reset() {
		resetNavx();
		resetEncoders();
	}
	
	public final void setHome() {
		reset();
		disableAllPID();
	}
}
