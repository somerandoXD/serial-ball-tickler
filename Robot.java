// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */  
 
  private final WPI_TalonSRX leftmotor1 = new WPI_TalonSRX(1);
  private final WPI_TalonSRX leftmotor2 = new WPI_TalonSRX(2);
  private final WPI_TalonSRX rightmotor1 = new WPI_TalonSRX(3);
  private final WPI_TalonSRX rightmotor2 = new WPI_TalonSRX(4);
  private final WPI_TalonSRX conveyor1 = new WPI_TalonSRX(5);
  private final MotorControllerGroup leftMotorGroup = new MotorControllerGroup(leftmotor1, leftmotor2);
  private final MotorControllerGroup rightMotorGroup = new MotorControllerGroup(rightmotor1, rightmotor2);
  DifferentialDrive drivetrain = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
  XboxController player1 = new XboxController(0);
  Timer timer = new Timer();

  private final Compressor compressor2 = new Compressor(PneumaticsModuleType.REVPH);
  private final DoubleSolenoid dbsolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
  @Override
  public void robotInit() {

  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.getNumber("current", compressor2.getCurrent());
    SmartDashboard.getNumber("preasue", compressor2.getPressure());
    SmartDashboard.getBoolean("on/off?", compressor2.isEnabled());
  }
  @Override
  public void autonomousInit() {
    timer.reset();
    timer.start();
  }
  @Override
  public void autonomousPeriodic() {
    /*
     * while (timer.get()<=15)
     * {
     *    while (time.get()<=4)
     *    drivetrain.tankDrive(0.7, 0.7);
     *    drivetrain.tankDrive(1,0.5);
     *    while (time.get()>4 && time.get()<=15)
     *    drivetrain.tankDrive(0.7, 0.7);  
     * }
     */
    
    
    
    if (timer.get() < 4){
      drivetrain.tankDrive(0.7, 0.7);
    } else if (timer.get() > 8 ){
      drivetrain.tankDrive(1, 0.5);
    }
  }

  @Override
  public void teleopInit() {
    
      }
  
  @Override
  public void teleopPeriodic() {
    if (player1.getLeftBumper()){
      dbsolenoid.set(DoubleSolenoid.Value.kForward);
    } else if (player1.getRightBumper()){
      dbsolenoid.set(DoubleSolenoid.Value.kReverse);
    } else{
      dbsolenoid.set(DoubleSolenoid.Value.kOff);
    }
    if (player1.getLeftTriggerAxis() == 1) {
      rightMotorGroup.set(0.2);
      leftMotorGroup.set(0.2);
    } else if (player1.getStartButtonPressed()){
      drivetrain.tankDrive(player1.getRightY(), player1.getLeftY());
    } 
    else{
      drivetrain.tankDrive(-player1.getRightY(), -player1.getLeftY());
    }
    if (player1.getAButton()){
      conveyor1.set(0.5);
    } 
    if (player1.getRightBumper() && player1.getRightStickButton() && player1.getBButton()){
      drivetrain.tankDrive(1, -1);
    }
  }
  
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
