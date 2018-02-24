package org.usfirst.frc.team4565.robot.commands.claw;

import org.usfirst.frc.team4565.robot.Robot;
import org.usfirst.frc.team4565.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Allows pitch control of a Claw object using a Joystick object
 * @author Skyline Robotics - Ian McDonough
 *
 */
public class TeleopClawPitchControl extends Command {

	private Claw m_claw;
	
	/**
	 * Construct a new TeleopClawPitchControl object
	 * @param claw The Claw object that will be modified
	 */
    public TeleopClawPitchControl(Claw claw) {
    	m_claw = claw;
    	
    	requires(claw);
    }

    /**
     * Does nothing
     */
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    /**
     * Gets the joystick from the operator interface object and then passes it into
     * the Claw's controlPitchWithJoystick method
     */
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        m_claw.controlPitchWithController(Robot.kOi.getSecondaryController());
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
     * Does nothing
     */
    // Called once after isFinished returns true
    protected void end() {
    }

    /**
     * Does nothing
     */
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
