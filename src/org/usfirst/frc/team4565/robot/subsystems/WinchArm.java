package org.usfirst.frc.team4565.robot.subsystems;


import org.usfirst.frc.team4565.robot.RobotMap;
import org.usfirst.frc.team4565.robot.commands.winch.TeleopWinchArmControl;
import org.usfirst.frc.team4565.robot.extensions.MotorControllerInterface;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.XboxController;

public class WinchArm extends Subsystem {

    private MotorControllerInterface m_winchArmMotor;

    public WinchArm(MotorControllerInterface winchArmMotor) {
        m_winchArmMotor = winchArmMotor;
    }

    public void teleopControlArmWithController(XboxController controller) {
		double value = controller.getPOV();
		
		if (value == 0) {
			m_winchArmMotor.setPower(RobotMap.winchArmPower);
		} else if (value == 180) {
			m_winchArmMotor.setPower(-RobotMap.winchArmPower);
		} else {
			m_winchArmMotor.setPower(0);
		}
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TeleopWinchArmControl(this));
    }
}

