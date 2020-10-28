/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  XboxController StickB; // 底盤控制
  XboxController StickS; // 投射器

  WPI_VictorSPX LFMotor; // 左前
  WPI_VictorSPX RFMotor; // 右前
  WPI_VictorSPX LBMotor; // 左後
  WPI_VictorSPX RBMotor; // 右後

  WPI_VictorSPX LSMotor; // 左投
  WPI_VictorSPX RSMotor; // 右投
  WPI_VictorSPX XMotor; // 左右
  WPI_VictorSPX YMotor; // 上下

  // SpeedControllerGroup FMotors; //前排馬達群組
  SpeedControllerGroup RMotors; // 右側馬達群組
  SpeedControllerGroup LMotors; // 左側馬達群組
  SpeedControllerGroup SMotors; // 投射馬達群組

  double LSpeed; // 左
  double RSpeed; // 右

  double SSpeed; // 投射
  double XMSpeed; // 左右
  double YMSpeed; // 上下

  int auto;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    StickB = new XboxController(0);
    // StickS=new XboxController(1);

    RFMotor = new WPI_VictorSPX(4);
    RBMotor = new WPI_VictorSPX(3);
    LFMotor = new WPI_VictorSPX(2);
    LBMotor = new WPI_VictorSPX(1);

    // LSMotor=new WPI_VictorSPX();
    // RSMotor=new WPI_VictorSPX();
    // XMotor=new WPI_VictorSPX();
    // YMotor=new WPI_VictorSPX();

    RMotors = new SpeedControllerGroup(RFMotor, RBMotor);
    LMotors = new SpeedControllerGroup(LFMotor, LBMotor);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() 
  {
  m_autonomousCommand = m_robotContainer.getAutonomousCommand();
  
  auto = 0;
  
  timer.reset();
  }

  Timer timer = new Timer();

  @Override
  public void autonomousPeriodic() 
  {
    switch (auto)
    {
    case 0:
      if(timer.get()==0)
      {
        timer.start();
      }
      RMotors.set(0.2);
      LMotors.set(-0.1);
      if(timer.get()>=5)
      {
        RMotors.set(0);
        LMotors.set(0);
        auto++;
      }
    case 1:
      break;
    }
  }
  @Override
  public void teleopInit() 
  {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) 
    {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() 
  {
    RSpeed = -StickB.getY(Hand.kRight);
    LSpeed = StickB.getY(Hand.kLeft);

    RSpeed = RSpeed*0.7;
    LSpeed = LSpeed*0.7;

    RMotors.set(RSpeed);
    LMotors.set(LSpeed);
  }

  @Override
  public void testInit() 
  {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() 
  {
  }
}
