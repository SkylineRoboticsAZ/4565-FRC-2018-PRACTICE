package org.usfirst.frc.team4565.robot.subsystems;

import org.usfirst.frc.team4565.robot.commands.TeleopDrive;
import org.usfirst.frc.team4565.robot.RobotMap;
import org.usfirst.frc.team4565.robot.extensions.MotorControllerInterface;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.XboxController;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Controls a drive train consisting of left and right side motors
 * @author Skyline Robotics - Ian McDonough
 *
 */
public class DriveTrain extends Subsystem {
	
	private List<MotorControllerInterface> m_leftSideMotors,
										   m_rightSideMotors;
	/**
	 * Constructs a DriveTrain subsystem object
	 */
	public DriveTrain() {
		m_leftSideMotors = new LinkedList<MotorControllerInterface>();
		m_rightSideMotors = new LinkedList<MotorControllerInterface>();
	}
	
	/**
	 * Add a motor to the left side of this drive train
	 * @param motor The motor to add
	 */
	public void addLeftSideMotor(MotorControllerInterface motor) {
		m_leftSideMotors.add(motor);
	}

	/**
	 * Add a motor to the right side of this drive train
	 * @param motor The motor to add
	 */
	public void addRightSideMotor(MotorControllerInterface motor) {
		m_rightSideMotors.add(motor);
	}

	/**
	 * Initialize the default command for the subsystem
	 */
    public void initDefaultCommand() {
        setDefaultCommand(new TeleopDrive());
    }
	
    /**
     * Drive the robot using an XboxController object for input
     * @param driveController The XboxController to use
     */
	public void driveWithXBoxController(XboxController driveController) {
        //Read values from controller
        double accelerator = driveController.getRawAxis(3);
        double decelerator = driveController.getRawAxis(2);
        double turnFactor = driveController.getRawAxis(4);
        
        //Determine if the values are within margin of error
        boolean useAccelerator, useDecelerator, useTurnFactor;
        useAccelerator = checkDeadband(accelerator, RobotMap.driveDeadband);
        useDecelerator = checkDeadband(decelerator, RobotMap.driveDeadband);
        useTurnFactor = checkDeadband(turnFactor, RobotMap.driverTurnMultiplier);

        //Zero values that are within margin of error &
        // give backing up priority over going forward
        double newMotorValue;

        if (useDecelerator)
            newMotorValue = -decelerator;
        else if (useAccelerator)
            newMotorValue = accelerator;
        else
            newMotorValue = 0;

        if (!useTurnFactor)
            turnFactor = 0;

        //Y = A(1-|X|) if x < 1 (Y,A) if x > 0 (A,Y)
        //Drive if we have any values that are actually reasonable
        if (useAccelerator || useDecelerator || useTurnFactor) {
            //Boost if the boost button is pressed
            double boostMultiplier = (driveController.getRawButton(10) ?
                    RobotMap.boostEnabledMultiplier : RobotMap.boostDisabledMultiplier);
            newMotorValue *= boostMultiplier;

            setLeftDrive(newMotorValue + turnFactor * RobotMap.driverTurnMultiplier);
            setRightDrive(newMotorValue - turnFactor * RobotMap.driverTurnMultiplier);
        } else {
        	setLeftDrive(0);
        	setRightDrive(0);
        }
    }
    
    private void setLeftDrive(double power) {
    	setMotorsPower(m_leftSideMotors, power);
    }
    
    private void setRightDrive(double power) {
    	setMotorsPower(m_rightSideMotors, power);
    }
    
    private void setMotorsPower(List<MotorControllerInterface> list, double power) {
    	//Go through the list of motors and set the power on each one
    	Iterator<MotorControllerInterface> i = list.iterator();
    	while (i.hasNext()) {
    		MotorControllerInterface motor = i.next();
    		motor.setPower(power);
    	}
    }
    
    private boolean checkDeadband(double value, double deadband) {
    	//Check if the value is within the deadband
        return (value > deadband || value < -deadband);
    }
}

