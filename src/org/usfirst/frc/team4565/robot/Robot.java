/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4565.robot;

import org.usfirst.frc.team4565.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4565.robot.subsystems.Claw;
import org.usfirst.frc.team4565.robot.subsystems.Winch;
import org.usfirst.frc.team4565.robot.subsystems.WinchArm;
import org.usfirst.frc.team4565.robot.extensions.TalonSRXWrapper;
import org.usfirst.frc.team4565.robot.extensions.TalonWrapper;
import org.usfirst.frc.team4565.robot.extensions.VictorSPWrapper;
import org.usfirst.frc.team4565.robot.commands.DriveStraight;
import org.usfirst.frc.team4565.robot.commands.TurnDegrees;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static DriveTrain kDriveTrain;
	public static Claw kBottomClaw;
	public static Winch kWinch;
	public static WinchArm kWinchArm;
	public static OI kOi;
	public static Command m_autoCommand;
	
	//private static Compressor m_compressor;

	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Initialize subsystems
		initDriveTrain();
		initBottomClaw();
		initWinch();
		initWinchArm();
		kOi = new OI();
		kOi.init();
		
		m_chooser.addDefault("No Auto", null);
		m_chooser.addObject("Drive Straight", new DriveStraight(kDriveTrain, 1));
		m_chooser.addObject("Turn 90 Degrees Right", new TurnDegrees(kDriveTrain, 90));
		SmartDashboard.putData("Auto Selection", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autoCommand = m_chooser.getSelected();
		
		if (m_autoCommand != null)
			m_autoCommand.start();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		/*if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		m_autoCommand.start();*/
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autoCommand != null) {
			m_autoCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		/*System.out.println("Current: " + m_compressor.getCompressorCurrent());
		System.out.println("Current Too High Falut: " + m_compressor.getCompressorCurrentTooHighFault());
		System.out.println("Compressor current too high sticky falut: " + m_compressor.getCompressorCurrentTooHighStickyFault());
		System.out.println("Not connected falut: " + m_compressor.getCompressorNotConnectedFault());
		System.out.println("Not connected sticky falut: " + m_compressor.getCompressorNotConnectedStickyFault());
		System.out.println("Compressor shorted fault: " + m_compressor.getCompressorShortedFault());
		System.out.println("Compressor shorted sticky fault: " + m_compressor.getCompressorShortedStickyFault());*/
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	private void initDriveTrain() {		
		//Create all the motor controller objects
		TalonSRXWrapper leftFrontMotor = new TalonSRXWrapper(RobotMap.leftFrontDriveMotor);
		TalonSRXWrapper leftBackMotor = new TalonSRXWrapper(RobotMap.leftBackDriveMotor);
		VictorSPWrapper rightFrontMotor = new VictorSPWrapper(RobotMap.rightFrontDriveMotor);
		VictorSPWrapper rightBackMotor = new VictorSPWrapper(RobotMap.rightBackDriveMotor);
		
		//Invert the right side
		rightFrontMotor.setInverted(true);
		rightBackMotor.setInverted(true);
		
		//Create encoder objects
		Encoder leftEncoder = new Encoder(RobotMap.leftEncoderPort0, RobotMap.leftEncoderPort1, true, EncodingType.k4X);
		Encoder rightEncoder = new Encoder(RobotMap.rightEncoderPort0, RobotMap.rightEncoderPort1, false, EncodingType.k4X);
		
		//Create the DriveTrain subsystem
		kDriveTrain = new DriveTrain();
		kDriveTrain.addLeftSideMotor(leftFrontMotor);
		kDriveTrain.addLeftSideMotor(leftBackMotor);
		kDriveTrain.addRightSideMotor(rightFrontMotor);
		kDriveTrain.addRightSideMotor(rightBackMotor);
		kDriveTrain.setLeftSideEncoder(leftEncoder);
		kDriveTrain.setRightSideEncoder(rightEncoder);
	}
	
	private void initBottomClaw() {
		//Create all the motor controller objects
		VictorSPWrapper pitchMotor = new VictorSPWrapper(RobotMap.bottomClawPitchControlPort);
		DoubleSolenoid clawCylinder = new DoubleSolenoid(RobotMap.bottomClawSolenoidPort0, 
														 RobotMap.bottomClawSolenoidPort1);
		
		//Create the new Claw subsystem
		kBottomClaw = new Claw(pitchMotor, clawCylinder, 1);
	}

	private void initWinch() {
		TalonWrapper winchMotor = new TalonWrapper(RobotMap.winchPort);

		kWinch = new Winch(winchMotor);
	}
	
	private void initWinchArm() {
		TalonWrapper winchArmMotor = new TalonWrapper(RobotMap.winchArmPort);
		
		kWinchArm = new WinchArm(winchArmMotor);
	}
}
