package org.usfirst.frc.team4565.robot.commands.claw;

import org.usfirst.frc.team4565.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Toggles the opened/closed state of a Claw object
 * @author Skyline Robotics - Ian McDonough
 *
 */
public class ToggleClaw extends Command {

	private Claw m_claw;
	private boolean m_finished = false;
	
	/**
	 * Constructs a new ToggleClaw command object
	 * @param claw The Claw object that will be modified
	 */
    public ToggleClaw(Claw claw) {
    	m_claw = claw;
    	
    	requires(claw);
    }


    /**
     * Toggles the state of the claw
     */
    // Called just before this Command runs the first time
    protected void initialize() {
    	if (m_claw.isClawOpen())
    		m_claw.closeClaw();
    	else
    		m_claw.openClaw();
    	m_finished = true;
    }

    /**
     * Does nothing
     */
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    /**
     * Return true when the command is done being executed
     */
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Check if we are done toggling or not
        return m_finished;
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
