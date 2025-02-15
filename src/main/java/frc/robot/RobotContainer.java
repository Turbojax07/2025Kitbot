package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Drivetrain.*;
import frc.robot.Subsystems.Drivetrain.Commands.*;
import frc.robot.Subsystems.Gyro.*;
import frc.robot.Subsystems.Roller.*;
import frc.robot.Subsystems.Roller.Commands.*;

public class RobotContainer {
    // Controller(s)
    private CommandXboxController controller = new CommandXboxController(0);

    public RobotContainer() {
        if (RobotBase.isSimulation()) {
            new Drivetrain(new DrivetrainIOSim());
            new Roller(new RollerIOSim());
        } else {
            // new Gyro(new GyroIOPigeon2());
            new Drivetrain(new DrivetrainIOTalonSRX());
            new Roller(new RollerIOSparkMax());
        }

        configureBindings();
    }

    private void configureBindings() {
        controller.a().whileTrue(new RunRoller(1));
        controller.b().whileTrue(new RunRoller(-1));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    public Command getTeleopCommand() {
        return new ArcadeDrive(() -> -controller.getLeftY(), () -> -controller.getRightX());
    }
}