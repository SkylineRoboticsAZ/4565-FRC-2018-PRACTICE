/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4565.robot;

import org.usfirst.frc.team4565.robot.triggers.TriggerTrigger;
import org.usfirst.frc.team4565.robot.commands.claw.ToggleClaw;
import org.usfirst.frc.team4565.robot.commands.winch.TeleopWinchControl;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private XboxController m_primaryController, m_secondaryController;
	private Button m_winchButton, m_winchReverseButton;
	private TriggerTrigger m_bottomClaw,
				           m_topClaw;

	public OI() {
		m_primaryController = null;
		m_secondaryController = null;
	}
	
	public void init() {
		m_primaryController = new XboxController(RobotMap.primaryJoystickPort);
		m_secondaryController = new XboxController(RobotMap.secondaryJoystickPort);

		m_bottomClaw = new TriggerTrigger(m_secondaryController, TriggerTrigger.Trigger.RightTrigger);
		m_topClaw = new TriggerTrigger(m_secondaryController, TriggerTrigger.Trigger.LeftTrigger);

		m_winchButton = new JoystickButton(m_secondaryController, 1);
		m_winchReverseButton = new JoystickButton(m_secondaryController, 4);
		
		m_bottomClaw.whenActive(new ToggleClaw(Robot.kBottomClaw));
		m_topClaw.whenActive(new ToggleClaw(Robot.kTopClaw));
		m_winchButton.whileHeld(new TeleopWinchControl(Robot.kWinch));
		m_winchReverseButton.whileHeld(new TeleopWinchControl(Robot.kWinch, true));
	}
	
	public XboxController getPrimaryController() {
		return m_primaryController;
	}

	public XboxController getSecondaryController() {
		return m_secondaryController;
	}
}
