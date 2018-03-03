package org.usfirst.frc.team4565.robot.commands;

import org.usfirst.frc.team4565.robot.RobotMap;
import org.usfirst.frc.team4565.robot.AutoCalc;
import org.usfirst.frc.team4565.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {

	private DriveTrain m_driveTrain;
	private Encoder m_leftEncoder, m_rightEncoder;
	private double m_goalRotations, m_leftRotations, m_rightRotations;
	
    public DriveStraight(DriveTrain driveTrain, double meters) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_driveTrain = driveTrain;
    	m_leftEncoder = driveTrain.getLeftEncoder();
    	m_rightEncoder = driveTrain.getRightEncoder();
    	m_goalRotations = AutoCalc.calculateRobotDrive(meters);
    	m_leftRotations = 0;
    	m_rightRotations = 0;
    	
    	requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Initialized, goal distance: " + m_goalRotations);
    	m_leftEncoder.reset();
    	m_rightEncoder.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftValue = m_leftEncoder.getDistance();
    	double rightValue = m_rightEncoder.getDistance();
    	
    	System.out.println("Left Distance: " + leftValue);
    	System.out.println("Right Distance: " + rightValue);
    	
    	double leftMotorSpeed = RobotMap.autoStraightSpeed, 
    		   rightMotorSpeed = RobotMap.autoStraightSpeed;
    	
    	if (leftValue > rightValue) {
    		System.out.println("Correcting right");
    		leftMotorSpeed -= (leftValue - rightValue);
    	} else if (rightValue > leftValue) {
    		System.out.println("Correcting left");
    		rightMotorSpeed -= (rightValue - leftValue);
    	}
    	
    	System.out.println("Left Drive Speed: " + leftMotorSpeed);
    	System.out.println("Right Drive Speed: " + rightMotorSpeed);
    	m_driveTrain.setLeftDrive(leftMotorSpeed);
    	m_driveTrain.setRightDrive(rightMotorSpeed);
    	
    	m_leftRotations = leftValue;
    	m_rightRotations = rightValue;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (m_leftRotations >= m_goalRotations && m_rightRotations >= m_goalRotations) {
    		System.out.println("Finished!");
    		return true;
    	}
    	
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Ended");
    	m_driveTrain.setLeftDrive(0);
    	m_driveTrain.setRightDrive(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Interrupted!");
    	end();
    }
}
