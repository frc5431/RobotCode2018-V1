package frc.team5431.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team5431.titan.core.Titan;
import frc.team5431.titan.core.TitanRobot;

public class Robot extends IterativeRobot {
    @Override
    public void robotInit() {
        WPI_TalonSRX a = new WPI_TalonSRX(0);
		Titan.Joystick joy = new Titan.Joystick(0);
		TitanRobot b = new TitanRobot();
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }

    @Override
    public void disabledPeriodic() { }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() { }

    @Override
    public void testPeriodic() { }
}
