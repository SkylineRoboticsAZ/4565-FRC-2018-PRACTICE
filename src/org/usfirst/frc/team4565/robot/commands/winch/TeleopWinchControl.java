package org.usfirst.frc.team4565.robot.commands.winch;

import org.usfirst.frc.team4565.robot.RobotMap;
import org.usfirst.frc.team4565.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopWinchControl extends Command {

    private Winch m_winch;

    public TeleopWinchControl(Winch winch) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_winch = winch;

        requires(winch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_winch.setWinchPower(RobotMap.winchPower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        m_winch.setWinchPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
