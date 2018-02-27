package org.usfirst.frc.team4565.robot.commands;

import org.usfirst.frc.team4565.robot.RobotMap;
import org.usfirst.frc.team4565.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {

	private DriveTrain m_driveTrain;
	private Encoder m_leftEncoder, m_rightEncoder;
	private double m_goalDegrees, m_leftDegrees, m_rightDegrees;
	private static final double distancePerRotation = RobotMap.wheelDiameter * Math.PI;
	
    public DriveStraight(DriveTrain driveTrain, double meters) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_driveTrain = driveTrain;
    	m_leftEncoder = driveTrain.getLeftEncoder();
    	m_rightEncoder = driveTrain.getRightEncoder();
    	m_goalDegrees = convertToDegrees(meters);
    	m_leftDegrees = 0;
    	m_rightDegrees = 0;
    	
    	requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_leftEncoder.reset();
    	m_rightEncoder.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftValue = m_leftEncoder.getDistance();
    	double rightValue = m_rightEncoder.getDistance();
    	
    	if (leftValue > rightValue) {
    		m_driveTrain.setLeftDrive(RobotMap.autoStraightSpeed - (leftValue - rightValue));
    		m_driveTrain.setRightDrive(RobotMap.autoStraightSpeed);
    	} else if (rightValue > leftValue) {
    		m_driveTrain.setLeftDrive(RobotMap.autoStraightSpeed);
    		m_driveTrain.setRightDrive(RobotMap.autoStraightSpeed - (rightValue - leftValue));
    	} else {
    		m_driveTrain.setLeftDrive(0);
    		m_driveTrain.setRightDrive(0);
    	}
    	
    	m_leftDegrees = leftValue;
    	m_rightDegrees = rightValue;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (m_leftDegrees >= m_goalDegrees && m_rightDegrees >= m_goalDegrees);
    }

    // Called once after isFinished returns true
    protected void end() {
    	m_driveTrain.setLeftDrive(0);
    	m_driveTrain.setRightDrive(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    private double convertToDegrees(double meters) {
    	return (360 * meters) / distancePerRotation;
    }
}
