package org.usfirst.frc.team4565.robot.commands;

import org.usfirst.frc.team4565.robot.RobotMap;
import org.usfirst.frc.team4565.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {

	private DriveTrain m_driveTrain;
	private Encoder m_leftEncoder, m_rightEncoder;
	private double m_goalDistance, m_currentDistance;
	
    public DriveStraight(DriveTrain driveTrain, double meters) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_driveTrain = driveTrain;
    	m_leftEncoder = driveTrain.getLeftEncoder();
    	m_rightEncoder = driveTrain.getRightEncoder();
    	m_goalDistance = meters;
    	m_currentDistance = 0;
    	
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
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_currentDistance >= m_goalDistance;
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
}
