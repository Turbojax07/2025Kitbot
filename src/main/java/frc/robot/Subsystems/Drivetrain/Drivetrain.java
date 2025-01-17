package frc.robot.Subsystems.Drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelPositions;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    private DrivetrainIO drivetrainIO;

    private DifferentialDriveKinematics kinematics;
    private DifferentialDrivePoseEstimator poseEstimator;

    private static Drivetrain instance;

    /** 
     * Gets a common instance of the {@link Drivetrain} subsystem.
     * 
     * If the Drivetrain subsystem has not been initialized, then it begins a simulation of the drivetrain.
     * 
     * @return An instance of the {@link Drivetrain} class.
     */
    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain(new DrivetrainIOSim());
        }

        return instance;
    }
    
    /**
     * Creates a new instance of the Drivetrain class.
     * 
     * @param drivetrainIO The IO interface to use for this class.  It must implement {@link DrivetrainIO}.
     */
    public Drivetrain(DrivetrainIO io) {
        if (instance != null) {
            return;
        } else {
            instance = this;
        }

        drivetrainIO = io;

        kinematics = new DifferentialDriveKinematics(DriveConstants.robotWidth);
        poseEstimator = new DifferentialDrivePoseEstimator(kinematics, new Rotation2d(), 0, 0, new Pose2d());
    }

    @Override
    public void periodic() {
        poseEstimator.update(getAngle(), getPositions());

        drivetrainIO.updateInputs();
    }

    /**
     * Drives the robot with arcade drive logic.
     * 
     * @param xSpeed The forwards speed of the robot in percent output.
     * @param zRotate The turn speed of the robot in percent output.
     */
    public void arcadeDrive(double xSpeed, double zRotate) {
        xSpeed = MathUtil.applyDeadband(xSpeed, 0.1);
        zRotate = MathUtil.applyDeadband(zRotate, 0.1);

        // Prevents the jump from 0 to 10% at the beginning of movement, but adds it back at 90%.
        if (xSpeed  > 0 && xSpeed  <  0.9) xSpeed  -= 0.1;
        if (xSpeed  < 0 && xSpeed  > -0.9) xSpeed  += 0.1;
        if (zRotate > 0 && zRotate <  0.9) zRotate -= 0.1;
        if (zRotate < 0 && zRotate > -0.9) zRotate += 0.1;

        xSpeed *= DriveConstants.maxDriveSpeed;
        zRotate *= DriveConstants.maxTurnSpeed;

        drivetrainIO.setLeftSpeed(ControlMode.Percent, xSpeed - zRotate);
        drivetrainIO.setRightSpeed(ControlMode.Percent, xSpeed + zRotate);
    }

    /**
     * Drives the robot with tank drive logic.
     * 
     * @param leftSpeed The percent output of the left motor.
     * @param rightSpeed The percent output of the right motor.
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        drivetrainIO.setLeftSpeed(ControlMode.Percent, leftSpeed);
        drivetrainIO.setRightSpeed(ControlMode.Percent, rightSpeed);
    }

    /** Gets the heading of the robot. */
    public Rotation2d getAngle() {
        return drivetrainIO.getAngle();
    }

    /** Gets the positions of each wheel on the robot. */
    public DifferentialDriveWheelPositions getPositions() {
        return new DifferentialDriveWheelPositions(drivetrainIO.getLeftDistance(), drivetrainIO.getRightDistance());
    }
}