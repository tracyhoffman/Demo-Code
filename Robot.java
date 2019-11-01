package org.usfirst.frc.team2221.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	/*--------------------------------------------------------------------*/
	/*----------------------Setup Talons---------------------*/
	   
		Spark m_frontLeft = new Spark(1);
	    Spark m_rearLeft = new Spark(2);
	    SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
																								
	    Spark m_frontRight = new Spark(3);
	    Spark m_rearRight = new Spark(4);
	    SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
		
	    DifferentialDrive bubb = new DifferentialDrive(m_left, m_right);
	   
		Victor Elevator   = new Victor(7);
	    Spark Intake = new Spark(1);
	    Victor Drawbridge  = new Victor(2);
	    Talon Lever = new Talon(0);	   

		Compressor sug = new Compressor(0);
		DoubleSolenoid climber = new DoubleSolenoid(0,1);
		

	Alliance alliance = DriverStation.getInstance().getAlliance();
	
	Timer timer;	
	/*---------------------Controller Setup-----------------------------*/
	XboxController xbox1 = new XboxController(0); // Driver xbox (xboxController)
	XboxController xbox2 = new XboxController(1); 
	
	Joystick JS1 = new Joystick(2);
	double JS_x;
	double JS_y;
	double JS_z;
	double rotato;
	double xbox1_rotato;
	double tlb;
	double xbox2_elevatorstick;
	double xbox1_rightstick;

	/*--------------------------------------------------------------------*/
	/*----------------------Controller Settings----------------------------------*/
	double xbox1_value_x; // Xbox1 x-values
	double xbox1_value_y; // Xbox1 y-values 
	double xbox2_value_y;
	
	double xbox1RightTrigger = xbox1.getTriggerAxis(Hand.kRight);
	double xbox1LeftTrigger = xbox1.getTriggerAxis(Hand.kLeft);		
	/*--------------------------------------------------------------------*/
	
	@Override
	public void robotInit() {
		UsbCamera cam0 = edu.wpi.first.cameraserver.CameraServer.getInstance().startAutomaticCapture();
		cam0.setResolution(128, 240);
		cam0.setFPS(15);
		cam0.setBrightness(30);

		climber.set(Value.kForward);

		//m_frontRight.setInverted(true);
		//m_rearRight.setInverted(true);
	        }
	       
	@Override	
	public void autonomousInit(){
			}
	
	@Override
	
	public void autonomousPeriodic() {

		if(xbox1.getAButton()){

				xbox1_value_x = xbox1.getX(Hand.kLeft)*.5;
				xbox1_value_y = xbox1.getY(Hand.kLeft)*.5;
				xbox1_rotato = rotato*.5;
				xbox2_elevatorstick = (xbox2.getY(Hand.kLeft)*.75);
				
				JS_x = JS1.getX()*.50;
				JS_y = JS1.getY()*.50;
				JS_z = JS1.getZ()*.25;
			
				}
			else{
				
				xbox1_value_x = (xbox1.getX(Hand.kLeft)*0.8);	
				xbox1_value_y = (xbox1.getY(Hand.kLeft)*0.8);
				xbox1_rotato = rotato;
				xbox2_elevatorstick = (xbox2.getY(Hand.kLeft)*0.65);
				
				JS_x = JS1.getX();
				JS_y = JS1.getY();
				JS_z = JS1.getZ();
				
			}	
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////// DRIVETRAIN SETTINGS ////////////////////////////////////////////
				//bubb.driveCartesian(JS_x, JS_y, JS_z);								//UNCOMMENT FOR MECANUMJOYSTICK
				//bubb.driveCartesian(xbox1_value_x, xbox1_value_y, xbox1_rotato);      //UNCOMMENT FOR MECANUMXBOX
			    bubb.arcadeDrive(-xbox1_value_y, xbox1_value_x);						//UNCOMMENT FOR 6WHEELXBOX  
				//bubb.arcadeDrive(JS_x, -JS_y);									    //UNCOMMENT FOR 6WHEELJOYSTICK
				
				Intake.set(xbox2_elevatorstick);
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// ELEVATOR /////////////////////////////////////////////////
				if(xbox2.getYButton())
				{
					Elevator.set(-0.75);
				}
				else if(xbox2.getAButton())
				{
					Elevator.set(0.75);
				}
				else
				{
					Elevator.set(0);
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// DRAWBRIDGE //////////////////////////////////////////////
				if(xbox2.getStartButton())
				{
					Drawbridge.set(1);
				}
				else if(xbox2.getBackButton())
				{
					Drawbridge.set(-1);
				}
				else
				{
					Drawbridge.set(0);
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// PNEUMATIC SETUP ////////////////////////////////////////
				if(xbox1.getXButton()){
					climber.set(Value.kForward);
				}
				else if(xbox1.getBButton()){
					climber.set(Value.kReverse);
				}
				else{
					climber.set(Value.kOff);
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// climber SETUP ////////////////////////////////////////
				xbox1_rightstick = (xbox1.getY(Hand.kRight));
				
				Lever.set(-xbox1_rightstick);


			}
		
	@Override
	public void teleopInit(){
		bubb.setSafetyEnabled(true);
		
			}

	@Override
	public void teleopPeriodic() {
//-----------------------Driver Controller---------------------------------//

		if(xbox1RightTrigger>.2)
				{
				rotato = xbox1RightTrigger;
				}
		
		else    {
			rotato = 0;
				}
		
		if(xbox1LeftTrigger>.2)
				{
				rotato = -xbox1LeftTrigger;
				}
		
		else 	{
			rotato = 0;
				}
				
			if(xbox1.getAButton()){

				xbox1_value_x = xbox1.getX(Hand.kLeft)*.5;
				xbox1_value_y = xbox1.getY(Hand.kLeft)*.5;
				xbox1_rotato = rotato*.5;
				xbox2_elevatorstick = (xbox2.getY(Hand.kLeft)*.75);
				
				JS_x = JS1.getX()*.50;
				JS_y = JS1.getY()*.50;
				JS_z = JS1.getZ()*.25;
			
				}
			else{
				
				xbox1_value_x = (xbox1.getX(Hand.kLeft)*0.8);	
				xbox1_value_y = (xbox1.getY(Hand.kLeft)*0.8);
				xbox1_rotato = rotato;
				xbox2_elevatorstick = (xbox2.getY(Hand.kLeft)*0.65);
				
				JS_x = JS1.getX();
				JS_y = JS1.getY();
				JS_z = JS1.getZ();
				
			}	
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////// DRIVETRAIN SETTINGS ////////////////////////////////////////////
				//bubb.driveCartesian(JS_x, JS_y, JS_z);								//UNCOMMENT FOR MECANUMJOYSTICK
				//bubb.driveCartesian(xbox1_value_x, xbox1_value_y, xbox1_rotato);      //UNCOMMENT FOR MECANUMXBOX
			    bubb.arcadeDrive(-xbox1_value_y, xbox1_value_x);						//UNCOMMENT FOR 6WHEELXBOX  
				//bubb.arcadeDrive(JS_x, -JS_y);									    //UNCOMMENT FOR 6WHEELJOYSTICK
				
				Intake.set(xbox2_elevatorstick);
				/////////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// ELEVATOR /////////////////////////////////////////////////
				if(xbox2.getYButton())
				{
					Elevator.set(-0.75);
				}
				else if(xbox2.getAButton())
				{
					Elevator.set(0.75);
				}
				else
				{
					Elevator.set(0);
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// DRAWBRIDGE //////////////////////////////////////////////
				if(xbox2.getStartButton())
				{
					Drawbridge.set(1);
				}
				else if(xbox2.getBackButton())
				{
					Drawbridge.set(-1);
				}
				else
				{
					Drawbridge.set(0);
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// PNEUMATIC SETUP ////////////////////////////////////////
				if(xbox1.getXButton()){
					climber.set(Value.kForward);
				}
				else if(xbox1.getBButton()){
					climber.set(Value.kReverse);
				}
				else{
					climber.set(Value.kOff);
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////// climber SETUP ////////////////////////////////////////
				xbox1_rightstick = (xbox1.getY(Hand.kRight));
				
				Lever.set(-xbox1_rightstick);
				
		
				//////////////////////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////// ARCHAIC MATCH TIMER (NOT SURE IF FUNCTIONAL) /////////////////////////////
			tlb = Timer.getMatchTime();
			SmartDashboard.putNumber("TIME LEFT BOYO", tlb);
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}
	}
