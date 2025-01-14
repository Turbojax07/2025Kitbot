package frc.robot.Subsystems.Drivetrain;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelPositions;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    private DrivetrainIO drivetrainIO;

    private DifferentialDriveKinematics kinematics;
    private DifferentialDrivePoseEstimator poseEstimator;

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain(new DrivetrainIOSim());
        }

        return instance;
    }
    
    public Drivetrain(DrivetrainIO io) {
        drivetrainIO = io;

        kinematics = new DifferentialDriveKinematics(DriveConstants.trackWidth);
        poseEstimator = new DifferentialDrivePoseEstimator(kinematics, new Rotation2d(), 0, 0, new Pose2d());

        instance = this;
    }

    @Override
    public void periodic() {
        poseEstimator.update(getAngle(), getPositions());

        drivetrainIO.updateInputs();
    }

    public void arcadeDrive(ChassisSpeeds speeds) {
        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);

        drivetrainIO.setLeftSpeed(ControlMode.Percent, wheelSpeeds.leftMetersPerSecond);
        drivetrainIO.setLeftSpeed(ControlMode.Percent, wheelSpeeds.leftMetersPerSecond);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        drivetrainIO.setLeftSpeed(ControlMode.Percent, leftSpeed);
        drivetrainIO.setRightSpeed(ControlMode.Percent, rightSpeed);
    }

    public Rotation2d getAngle() {
        return drivetrainIO.getAngle();
    }

    public DifferentialDriveWheelPositions getPositions() {
        return new DifferentialDriveWheelPositions(drivetrainIO.getLeftDistance(), drivetrainIO.getRightDistance());
    }
}