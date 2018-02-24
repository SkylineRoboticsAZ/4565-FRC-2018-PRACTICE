package org.usfirst.frc.team4565.robot.commands;

import org.usfirst.frc.team4565.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Allows the drive train to be controlled using an XboxController object
 * @author Skyline Robotics - Ian McDonough
 *
 */
public class TeleopDrive extends Command {

	/**
	 * Constructs a new TeleopDrive command
	 */
    public TeleopDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kDriveTrain);
    }

    /**
     * Does nothing
     */
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    /**
     * Gets the XboxController from the operator interface object and then passes it into
     * the DriveTrain's driveWithXBoxController method
     */
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kDriveTrain.driveWithXBoxController(Robot.kOi.getPrimaryController());
    }

    /**
     * This command is never finished, so return false
     * NOTE: This command can be interrupted, allowing for other commands to run
     */
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    /**
     * Do nothing
     */
    // Called once after isFinished returns true
    protected void end() {
    }

    /**
     * Do nothing
     */
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
