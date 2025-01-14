// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Drivetrain.Drivetrain;
import frc.robot.Subsystems.Drivetrain.DrivetrainIOSim;
import frc.robot.Subsystems.Drivetrain.DrivetrainIOSparkMax;
import frc.robot.Subsystems.Drivetrain.Commands.ArcadeDrive;
import frc.robot.Subsystems.Roller.Roller;
import frc.robot.Subsystems.Roller.RollerIOSim;
import frc.robot.Subsystems.Roller.RollerIOSparkMax;

public class RobotContainer {
    // Controller
    private CommandXboxController controller = new CommandXboxController(0);

    public RobotContainer() {
        if (RobotBase.isSimulation()) {
            new Drivetrain(new DrivetrainIOSim());
            new Roller(new RollerIOSim());
        } else {
            new Drivetrain(new DrivetrainIOSparkMax());
            new Roller(new RollerIOSparkMax());
        }

        configureBindings();
    }

    private void configureBindings() {
        
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    public Command getTeleopCommand() {
        return new ArcadeDrive(() -> controller.getRawAxis(1), () -> controller.getRawAxis(0));
    }
}