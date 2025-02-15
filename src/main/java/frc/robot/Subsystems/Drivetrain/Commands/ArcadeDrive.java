package frc.robot.Subsystems.Drivetrain.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Drivetrain.Drivetrain;
import java.util.function.Supplier;

public class ArcadeDrive extends Command {
    private Drivetrain drivetrain;
    private Supplier<Double> xSpeedSupplier;
    private Supplier<Double> zRotateSupplier;

    /**
     * Drives the robot with arcade drive logic.
     * 
     * @param xSpeedSupplier The supplier for movement along the x axis.
     * @param zRotateSupplier The supplier for rotation along the z axis.
     */
    public ArcadeDrive(Supplier<Double> xSpeedSupplier, Supplier<Double> zRotateSupplier) {
        this.xSpeedSupplier = xSpeedSupplier;
        this.zRotateSupplier = zRotateSupplier;
        
        drivetrain = Drivetrain.getInstance();
    }

    /** Runs once when the command is initially scheduled. */
    @Override
    public void initialize() {}

    /** Runs once every tick that the command is scheduled. */
    @Override
    public void execute() {
        drivetrain.arcadeDrive(xSpeedSupplier.get(), zRotateSupplier.get());
    }

    /**
     * Runs once every tick that the command is scheduled.
     * 
     * @return Whether or not the command should end early.
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /** Runs once when the command is cancelled. */
    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }
}