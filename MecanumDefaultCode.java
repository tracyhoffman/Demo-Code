package org.usfirst.frc.team2221.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Watchdog;


/*
 * Simplest program to drive a robot with mecanum drive using a single Logitech
 * Extreme 3D Pro joystick and 4 drive motors connected as follows:
 *     - PWM 0 - Connected to front left drive motor
 *     - PWM 1 - Connected to rear left drive motor
 *     - PWM 2 - Connected to front right drive motor
 *     - PWM 3 - Connected to rear right drive motor
 */

public class MecanumDefaultCode extends TimedRobot {
     //Create a robot drive object using PWMs 0, 1, 2 and 3
     Spark m_frontLeft = new Spark(1);	
	Spark m_rearLeft = new Spark(2);
	Spark m_frontRight = new Spark(3);	
     Spark m_rearRight = new Spark(4);
     RobotDrive m_robotDrive = new RobotDrive(m_frontLeft, m_rearLeft, m_frontRight, m_rearRight);
     //Define joystick being used at USB port 1 on the Driver Station
     Joystick m_driveStick = new Joystick(1);
@Override
public void teleopPeriodic() {
     //m_robotDrive.setSafetyEnabled(true);
     while(true && isOperatorControl() && isEnabled()) 
     {
          m_robotDrive.mecanumDrive_Cartesian(m_driveStick.getX(), m_driveStick.getY(), m_driveStick.getZ(), 0);
          Timer.delay(0.005);
              
     }
}
}