package frc.team5431.robot;

public final class Constants {
	private static enum RobotMappings {
		CATABOT, THRICE
	}

	private static enum CameraSettings {
		LIFECAM
	}

	/*
	 * MAIN CONFIGURATIONS
	 */
	private static final RobotMappings ROBOT_MAPPINGS = RobotMappings.CATABOT;
	private static final CameraSettings CAMERA_SETTINGS = CameraSettings.LIFECAM;

	/*
	 * DEBUGGGING
	 */
	public final static boolean ENABLE_DEBUGGING = true;

	/*
	 * AUTONOMOUS
	 */
	public final static boolean AUTO_LOG_PATHFINDING = false;
	public final static String AUTO_LOG_PATHFINDING_NAME = "right_scale";
	public final static double AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_SPEED = 0.2; // This is the speed to override the
																				// previous step
	public final static double AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_DISTANCE = 3.0; // This is the speed to override the
																					// previous step
	public final static double AUTO_PATHFINDING_OVERRIDE_NEXT_STEP_TURN = 5.0; // Minimum turn offset for the
																				// pathfinding to not skip a step
	public final static double AUTO_ROBOT_DEFAULT_SPEED = 0.45;

	/*
	 * TALONS
	 */
	public final static int TALON_UNUSED = 200;
	public final static int TALON_FRONT_LEFT_ID;
	public final static boolean TALON_FRONT_LEFT_INVERTED;

	public final static int TALON_MIDDLE_LEFT_ID;
	public final static boolean TALON_MIDDLE_LEFT_INVERTED;

	public final static int TALON_BACK_LEFT_ID;
	public final static boolean TALON_BACK_LEFT_INVERTED;

	public final static int TALON_FRONT_RIGHT_ID;
	public final static boolean TALON_FRONT_RIGHT_INVERTED;

	public final static int TALON_MIDDLE_RIGHT_ID;
	public final static boolean TALON_MIDDLE_RIGHT_INVERTED;

	public final static int TALON_BACK_RIGHT_ID;
	public final static boolean TALON_BACK_RIGHT_INVERTED;

	public final static int TALON_INTAKE_LEFT_ID;
	public final static boolean TALON_INTAKE_LEFT_INVERTED;

	public final static int TALON_INTAKE_RIGHT_ID;
	public final static boolean TALON_INTAKE_RIGHT_INVERTED;

	public final static int TALON_INTAKE_PINCHER_ID;
	public final static boolean TALON_INTAKE_PINCHER_INVERTED;

	public final static int TALON_INTAKE_UP_LEFT_ID;
	public final static boolean TALON_INTAKE_UP_LEFT_INVERTED;

	public final static int TALON_INTAKE_UP_RIGHT_ID;
	public final static boolean TALON_INTAKE_UP_RIGHT_INVERTED;

	public final static int TALON_CATAPULT_LEFT_ID;
	public final static boolean TALON_CATAPULT_LEFT_INVERTED;

	public final static int TALON_CATAPULT_RIGHT_ID;
	public final static boolean TALON_CATAPULT_RIGHT_INVERTED;

	public final static int TALON_CLIMBER_RIGHT_ID;
	public final static boolean TALON_CLIMBER_RIGHT_INVERTED;

	public final static int TALON_CLIMBER_LEFT_ID;
	public final static boolean TALON_CLIMBER_LEFT_INVERTED;
	
	public final static int TALON_SCISSOR_ID;
	public final static boolean TALON_SCISSOR_INVERTED;

	/*
	 * ENCODERS
	 */
	// Physical properties
	public final static double ROBOT_WHEEL_DIAMETER = 6.0; // WHEEL DIAMETER IN INCHES

	// Left side
	/*
	 * public final static int ENCODER_LEFT_DRIVE_CHANNEL_1 = 0; public final static
	 * int ENCODER_LEFT_DRIVE_CHANNEL_2 = 1; public final static boolean
	 * ENCODER_LEFT_DRIVE_INVERTED;
	 * 
	 * //Right side public final static int ENCODER_RIGHT_DRIVE_CHANNEL_1 = 2;
	 * public final static int ENCODER_RIGHT_DRIVE_CHANNEL_2 = 3; public final
	 * static boolean ENCODER_RIGHT_DRIVE_INVERTED;
	 */

	// Calcs
	public final static int ENCODER_STEPS_PER_FULL_ROTATION = 4096;
	// public final static int ENCODER_SAMPLES_TO_AVERAGE = 25; //5 steps to average
	public final static double ENCODER_DISTANCE_PER_PULSE = (ROBOT_WHEEL_DIAMETER * Math.PI)
			/ ENCODER_STEPS_PER_FULL_ROTATION;

	// Pincher
	public final static boolean ENCODER_PINCHER_INVERTED = true;
	public final static int ENCODER_PINCHER_FORWARD_LIMIT = 600; // Encoder count limit
	public final static boolean ENCODER_PINCHER_FORWARD_LIMIT_ENABLED = true;
	public final static int ENCODER_PINCHER_REVERSE_LIMIT = 0; // Encoder count reverse limit
	public final static boolean ENCODER_PINCHER_REVERSE_LIMIT_ENABLED = false;

	// Intake
	public final static int ENCODER_AUTONOMOUS_START_POSITION = 3750;
	public final static int ENCODER_INTAKE_UP_POSITION = 3680;//3550
	public final static int ENCODER_INTAKE_SWITCH_POSITION = 3225; //2800; then 2500;
	public final static int ENCODER_INTAKE_SAFE_SHOOT_POSITION = 2000;

	/*
	 * PID
	 */
	/*
	 * //Driving public final static double DRIVE_HEADING_P = 0.015; public final
	 * static double DRIVE_HEADING_I = 0.0022; public final static double
	 * DRIVE_HEADING_D = 0.00031; public final static double DRIVE_HEADING_MIN_MAX =
	 * 0.1;
	 * 
	 * //Distance public final static double DRIVE_DISTANCE_P = 0.015; public final
	 * static double DRIVE_DISTANCE_I = 0.0022; public final static double
	 * DRIVE_DISTANCE_D = 0.00031;
	 * 
	 * //Turning public final static double TURN_P = 0.003;//0.14 public final
	 * static double TURN_I = 0.000008; public final static double TURN_D =
	 * 0.000003; public final static double TURN_MIN_VALUE = 0.1; public final
	 * static double TURN_PRECISION = 1.0; //Make sure the turn is within the degree
	 * 
	 * //Vision public final static double VISION_P = 0.006; public final static
	 * double VISION_I = 0.000125; //0.00000007; public final static double VISION_D
	 * = 0.0075; //0.0005; public final static double VISION_MIN_MAX = 0.5;
	 * 
	 * public final static double PIVOT_DISTANCE_SCALING = 0.11;
	 */
	// Driving
	/*
	 * public final static double DRIVE_HEADING_P = 0.018; public final static
	 * double DRIVE_HEADING_I = 0.00; public final static double DRIVE_HEADING_D =
	 * 0.08; public final static double DRIVE_HEADING_MIN_MAX = 0.1;
	 */

	// Driving
	public final static double DRIVE_HEADING_P = 0.036;//0.018;
	public final static double DRIVE_HEADING_I = 0.00;
	public final static double DRIVE_HEADING_D = 0.085;
	public final static double DRIVE_HEADING_MIN_MAX = 0.1;

	// Mimic
	public final static double DRIVE_MIMIC_P = 0.025;
	public final static double DRIVE_MIMIC_I = 0.00;
	public final static double DRIVE_MIMIC_D = 0.065;
	public final static double DRIVE_MIMIC_MIN_MAX = 0.4;

	// Distance
	public final static double DRIVE_DISTANCE_P = 0.00632;
	public final static double DRIVE_DISTANCE_I = 0.0000000025; // 0.0003; //0.0022;
	public final static double DRIVE_DISTANCE_D = 0.000915; // 0.00031;
	public final static double DRIVE_DISTANCE_MAX_OFFSET = 20; // Maximum error

	// Turning
	public final static double TURN_P = 0.00325;
	public final static double TURN_I = 0.000185;
	public final static double TURN_D = 0.0000095;
	public final static double TURN_MIN_VALUE = 0.1;
	public final static double TURN_PRECISION = 1.0; // Make sure the turn is within the degree

	// Vision
	public final static double VISION_P = 0.006;
	public final static double VISION_I = 0.000125; // 0.00000007;
	public final static double VISION_D = 0.0075; // 0.0005;
	public final static double VISION_MIN_MAX = 0.5;

	public final static double PIVOT_DISTANCE_SCALING = 0.11;

	/*
	 * CURRENT LIMITING
	 */
	// Intake pincher
	public final static int INTAKE_PINCHER_CONTINUOUS_LIMIT = 15;
	public final static int INTAKE_PINCHER_PEAK_LIMIT = 20;
	public final static int INTAKE_PINCHER_PEAK_DURATION = 500;
	public final static boolean INTAKE_PINCHER_ENABLE_CURRENT_LIMITING = true;

	// Intake up
	public final static int INTAKE_UP_CONTINUOUS_LIMIT = 10;
	public final static int INTAKE_UP_PEAK_LIMIT = 18;
	public final static int INTAKE_UP_PEAK_DURATION = 800;
	public final static boolean INTAKE_UP_ENABLE_CURRENT_LIMITING = true;

	/*
	 * RAMP RATES
	 */
	// Intake
	public final static int INTAKE_PINCHER_RAMP_RATE = 0;

	public static class Vision {
		public final static int IMAGE_WIDTH = 320;
		public final static int IMAGE_HEIGHT = 240;
		public final static int FPS = 10;
		public final static double CAMERA_HORZ_FOV;
		public final static double CAMERA_VERT_FOV;
		public final static double CAMERA_DIAG_DIST;
		public final static double DEGREES_PER_HORZ_PIXEL;
		public final static double DEGREES_PER_VERT_PIXEL;
		public final static double CAMERA_HORZ_OFFSET;
		public final static double CAMERA_VERT_OFFSET;

		static {
			// CAMERA SPECIFIC SETTINGS
			switch (CAMERA_SETTINGS) {
			case LIFECAM:
			default:
				CAMERA_DIAG_DIST = Math.sqrt((IMAGE_WIDTH * IMAGE_WIDTH) + (IMAGE_HEIGHT * IMAGE_HEIGHT));
				CAMERA_HORZ_FOV = (360 * Math.atan((IMAGE_WIDTH / 2) / CAMERA_DIAG_DIST)) / Math.PI; // 58.5
				CAMERA_VERT_FOV = (360 * Math.atan((IMAGE_HEIGHT / 2) / CAMERA_DIAG_DIST)) / Math.PI; // 45.6
				DEGREES_PER_HORZ_PIXEL = CAMERA_HORZ_FOV / (double) IMAGE_WIDTH;
				DEGREES_PER_VERT_PIXEL = CAMERA_VERT_FOV / (double) IMAGE_HEIGHT;
				CAMERA_HORZ_OFFSET = 0.0;
				CAMERA_VERT_OFFSET = 0.0;
			}
		}

		public final static double fromCenter(final double pixel, final double dims) {
			return pixel - (dims / 2.0);
		}
		// The Chicken AlGoul Is Ready, Are You?

		public final static double angleFromCenter(final double center, final double degreesPerPixel) {
			return center * degreesPerPixel;
		}

		public final static double getHorzAngle(final double pixel) {
			return angleFromCenter(fromCenter(pixel, IMAGE_WIDTH), DEGREES_PER_HORZ_PIXEL) + CAMERA_HORZ_OFFSET;
		}

		public final static double getVertAngle(final double pixel) {
			return angleFromCenter(fromCenter(pixel, IMAGE_HEIGHT), DEGREES_PER_VERT_PIXEL) + CAMERA_VERT_OFFSET;
		}
	}

	static {
		// Catabot
		switch (ROBOT_MAPPINGS) {
		case CATABOT:
		default:
			TALON_FRONT_LEFT_ID = 1;
			TALON_FRONT_LEFT_INVERTED = true;

			TALON_MIDDLE_LEFT_ID = 2;
			TALON_MIDDLE_LEFT_INVERTED = true;

			TALON_BACK_LEFT_ID = 3;
			TALON_BACK_LEFT_INVERTED = true;

			TALON_FRONT_RIGHT_ID = 4;
			TALON_FRONT_RIGHT_INVERTED = false;

			TALON_MIDDLE_RIGHT_ID = 5;
			TALON_MIDDLE_RIGHT_INVERTED = false;

			TALON_BACK_RIGHT_ID = 6;
			TALON_BACK_RIGHT_INVERTED = false;

			TALON_INTAKE_LEFT_ID = 7;
			TALON_INTAKE_LEFT_INVERTED = false;

			TALON_INTAKE_RIGHT_ID = 8;
			TALON_INTAKE_RIGHT_INVERTED = true;

			TALON_INTAKE_PINCHER_ID = 9;
			TALON_INTAKE_PINCHER_INVERTED = false;

			TALON_INTAKE_UP_LEFT_ID = 10;
			TALON_INTAKE_UP_LEFT_INVERTED = true;

			TALON_INTAKE_UP_RIGHT_ID = 11;
			TALON_INTAKE_UP_RIGHT_INVERTED = false;

			TALON_CATAPULT_LEFT_ID = 12;
			TALON_CATAPULT_LEFT_INVERTED = true;

			TALON_CATAPULT_RIGHT_ID = 13;
			TALON_CATAPULT_RIGHT_INVERTED = false;

			TALON_CLIMBER_RIGHT_ID = 14;
			TALON_CLIMBER_RIGHT_INVERTED = false;

			TALON_CLIMBER_LEFT_ID = 15;
			TALON_CLIMBER_LEFT_INVERTED = true;
			
			TALON_SCISSOR_ID = 16;
			TALON_SCISSOR_INVERTED = false;
			break;

		case THRICE:
			TALON_FRONT_LEFT_ID = 5;
			TALON_FRONT_LEFT_INVERTED = false;

			TALON_MIDDLE_LEFT_ID = 4;
			TALON_MIDDLE_LEFT_INVERTED = false;

			TALON_BACK_LEFT_ID = 4;
			TALON_BACK_LEFT_INVERTED = false;

			TALON_FRONT_RIGHT_ID = 2;
			TALON_FRONT_RIGHT_INVERTED = true;

			TALON_MIDDLE_RIGHT_ID = 3;
			TALON_MIDDLE_RIGHT_INVERTED = true;

			TALON_BACK_RIGHT_ID = 3;
			TALON_BACK_RIGHT_INVERTED = true;

			TALON_INTAKE_LEFT_ID = TALON_UNUSED;
			TALON_INTAKE_LEFT_INVERTED = false;

			TALON_INTAKE_RIGHT_ID = TALON_UNUSED;
			TALON_INTAKE_RIGHT_INVERTED = false;

			TALON_INTAKE_PINCHER_ID = TALON_UNUSED;
			TALON_INTAKE_PINCHER_INVERTED = false;

			TALON_INTAKE_UP_LEFT_ID = TALON_UNUSED;
			TALON_INTAKE_UP_LEFT_INVERTED = false;

			TALON_INTAKE_UP_RIGHT_ID = TALON_UNUSED;
			TALON_INTAKE_UP_RIGHT_INVERTED = false;

			TALON_CATAPULT_LEFT_ID = TALON_UNUSED;
			TALON_CATAPULT_LEFT_INVERTED = false;

			TALON_CATAPULT_RIGHT_ID = TALON_UNUSED;
			TALON_CATAPULT_RIGHT_INVERTED = false;

			TALON_CLIMBER_RIGHT_ID = 6;
			TALON_CLIMBER_RIGHT_INVERTED = true;

			TALON_CLIMBER_LEFT_ID = 8;
			TALON_CLIMBER_LEFT_INVERTED = false;
			
			TALON_SCISSOR_ID = TALON_UNUSED;
			TALON_SCISSOR_INVERTED = false;

			// ENCODER_LEFT_DRIVE_INVERTED = true;
			// ENCODER_RIGHT_DRIVE_INVERTED = false;
			break;
		}
	}

	// SPEEDS
	public final static double CLIMBER_SPEED = 1.0;
	public final static double SCISSOR_UPPER_SPEED = 0.5;
	public final static double SCISSOR_LOWER_SPEED = 0.1;
	public final static double SCISSOR_REVERSE_SPEED = -0.1;
	public final static double CATAPULT_LOWER_SPEED = 0.5;
}
