package frc.robot.Subsystems.Drivetrain.Commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.Subsystems.Drivetrain.Drivetrain;
import java.util.function.Supplier;

public class ArcadeDrive extends Command {
    private Supplier<Double> xSpeedSupplier;
    private Supplier<Double> zRotateSupplier;
    private Drivetrain drivetrain;

    public ArcadeDrive(Supplier<Double> xSpeedSupplier, Supplier<Double> zRotateSupplier) {
        this.xSpeedSupplier = xSpeedSupplier;
        this.zRotateSupplier = zRotateSupplier;
        drivetrain = Drivetrain.getInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double xSpeed = xSpeedSupplier.get();
        double zRotate = zRotateSupplier.get();

        xSpeed = MathUtil.applyDeadband(xSpeed, 0.1);
        zRotate = MathUtil.applyDeadband(zRotate, 0.1);

        // Prevents the jump from 0 to 10% at the beginning of movement, but adds it back at 90%.
        if (xSpeed > 0 && xSpeed < 0.9) xSpeed -= 0.1;
        if (xSpeed < 0 && xSpeed > -0.9) xSpeed += 0.1;
        if (zRotate > 0 && zRotate < 0.9) zRotate -= 0.1;
        if (zRotate < 0 && zRotate > -0.9) zRotate += 0.1;

        xSpeed *= DriveConstants.maxDriveSpeed;
        zRotate *= DriveConstants.maxTurnSpeed;

        double leftSpeed = xSpeed - zRotate;
        double rightSpeed = xSpeed + zRotate;

        drivetrain.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(new ChassisSpeeds());
    }
}
