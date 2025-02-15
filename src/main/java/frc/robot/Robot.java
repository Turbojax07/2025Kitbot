package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

public class Robot extends LoggedRobot {
    private Command autonomousCommand;
    private Command teleopCommand;

    public Robot() {
        RobotContainer robotContainer = new RobotContainer();

        autonomousCommand = robotContainer.getAutonomousCommand();
        teleopCommand = robotContainer.getTeleopCommand();

        if (autonomousCommand == null) {
            autonomousCommand = Commands.print("No Autonomous command configured.");
        }

        if (teleopCommand == null) {
            teleopCommand = Commands.print("No Teleop command configured.");
        }

        Logger.addDataReceiver(new NT4Publisher());

        if (isReal()) {
            Logger.addDataReceiver(new WPILOGWriter("Log"));
        }

        if (isSimulation() && Constants.isReplay) {
            Logger.setReplaySource(new WPILOGReader("replay.wpilog"));
        }

        Logger.start();
    }

    /** Runs every tick that the robot is on. */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /** Runs once when the robot enters Disabled mode. */
    @Override
    public void disabledInit() {}

    /** Runs every tick that the robot is in Disabled mode. */
    @Override
    public void disabledPeriodic() {}

    /** Runs once when the robot exits Disabled mode. */
    @Override
    public void disabledExit() {}

    /** Runs once when the robot enters Autonomous mode. */
    @Override
    public void autonomousInit() {
        autonomousCommand.schedule();
    }

    /** Runs every tick that the robot is in Autonomous mode. */
    @Override
    public void autonomousPeriodic() {}

    /** Runs once when the robot exits Autonomous mode. */
    @Override
    public void autonomousExit() {
        autonomousCommand.cancel();
    }

    /** Runs once when the robot enters Teleop mode. */
    @Override
    public void teleopInit() {
        teleopCommand.schedule();
    }

    /** Runs every tick that the robot is in Teleop mode. */
    @Override
    public void teleopPeriodic() {}

    /** Runs once when the robot exits Teleop mode. */
    @Override
    public void teleopExit() {
        teleopCommand.cancel();
    }

    /** Runs once when the robot enters Test mode. */
    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    /** Runs every tick that the robot is in Test mode. */
    @Override
    public void testPeriodic() {}

    /** Runs once when the robot exits Test mode. */
    @Override
    public void testExit() {}
}