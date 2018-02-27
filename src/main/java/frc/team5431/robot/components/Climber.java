package frc.team5431.robot.components;

import frc.team5431.robot.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber {
	private final WPI_TalonSRX climberLeft, climberRight, scissor;

	public Climber() {
		climberLeft = new WPI_TalonSRX(Constants.TALON_CLIMBER_LEFT_ID);
		climberRight = new WPI_TalonSRX(Constants.TALON_CLIMBER_RIGHT_ID);
		scissor = new WPI_TalonSRX(Constants.TALON_SCISSOR_ID);
		
		climberLeft.setInverted(Constants.TALON_CLIMBER_LEFT_INVERTED);
		climberRight.setInverted(Constants.TALON_CLIMBER_RIGHT_INVERTED);
		scissor.setInverted(Constants.TALON_SCISSOR_INVERTED);
		
		climberLeft.setNeutralMode(NeutralMode.Brake);
		climberRight.setNeutralMode(NeutralMode.Brake);
		scissor.setNeutralMode(NeutralMode.Brake);
	}
	
	public void climb() {
		climberLeft.set(Constants.CLIMBER_SPEED);
		climberRight.set(Constants.CLIMBER_SPEED);
	}
	
	public void stopClimbing() {
		climberLeft.set(0.0);
		climberRight.set(0.0);
	}
	
	public void scissorUpFast() {
		scissor.set(Constants.SCISSOR_UPPER_SPEED);
	}
	
	public void scissorUpSlow() {
		scissor.set(Constants.SCISSOR_LOWER_SPEED);
	}
	
	public void scissorDown() {
		scissor.set(Constants.SCISSOR_REVERSE_SPEED);
	}
	
	public void stopScissor() {
		scissor.set(0.0);
	}
}
