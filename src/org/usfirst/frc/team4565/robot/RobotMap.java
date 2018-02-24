/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4565.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    //User controllers
	public static final int primaryJoystickPort = 0,
							secondaryJoystickPort = 1;
	//Secondary controller variables
	public static final double xboxTriggerDeadband = .5;
	//Robot drive motors
	public static final int leftFrontDriveMotor = 4,
							leftBackDriveMotor = 3,
							rightFrontDriveMotor = 1,
							rightBackDriveMotor = 0;
	//Robot drive parameters
	public static final double driveDeadband = .1,
							   driverTurnDeadband = .2,
                               driverTurnMultiplier = .5,
                               driverClawDeadband = .1,
                               boostDisabledMultiplier = .5,
                               boostEnabledMultiplier = 1;
	//Bottom claw variables
	public static final int bottomClawSolenoidPort0 = 2,
							bottomClawSolenoidPort1 = 3,
							bottomClawPitchControlPort = 2;
	//Top claw variables
	public static final int topClawSolenoidPort0 = 0,
							topClawSolenoidPort1 = 1,
							topClawPitchControlPort = 5;
	//Winch variables
    public static final double driverWinchDeadband = .1,
    						   driverWinchMultiplier = .5,
                               winchPower = .5,
    						   winchArmPower = .3;
    //Winch ports
    public static final int winchPort = 3,
    						winchArmPort = 4;
    //Auto config
    public static final double autoStraightSpeed = .5;
}
