package frc.robot.Subsystems.Drivetrain;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.networktables.StructSubscriber;
import edu.wpi.first.networktables.StructTopic;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.RobotMap;
import frc.robot.Subsystems.Gyro.Gyro;
import org.littletonrobotics.junction.Logger;


public class DrivetrainSparkMax extends SubsystemBase implements DrivetrainIO {
    private SparkMax flMotor;
    private SparkMax frMotor;
    private SparkMax blMotor;
    private SparkMax brMotor;

    private RelativeEncoder flEncoder;
    private RelativeEncoder frEncoder;
    private RelativeEncoder blEncoder;
    private RelativeEncoder brEncoder;

    private Gyro gyro;

    private double leftMagnitude;
    private double rightMagnitude;

    private DrivetrainIOInputsAutoLogged inputs;

    public DrivetrainSparkMax() {
        flMotor = new SparkMax(RobotMap.DT_FrontLeftId, MotorType.kBrushless);
        frMotor = new SparkMax(RobotMap.DT_FrontRightId, MotorType.kBrushless);
        blMotor = new SparkMax(RobotMap.DT_BackLeftId, MotorType.kBrushless);
        brMotor = new SparkMax(RobotMap.DT_BackRightId, MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(IdleMode.kBrake);
        
        config.inverted(true);
        flMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        config.follow(flMotor);
        blMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        config.disableFollowerMode();
        config.inverted(false);
        frMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        config.follow(frMotor);
        brMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        flEncoder = flMotor.getEncoder();
        frEncoder = frMotor.getEncoder();
        blEncoder = blMotor.getEncoder();
        brEncoder = brMotor.getEncoder();

        gyro = Gyro.getInstance();

        inputs = new DrivetrainIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
        switch(inputs.mode) {
            case Percent:
                flMotor.set(leftMagnitude);
                frMotor.set(rightMagnitude);
                break;
            case Voltage:
                flMotor.setVoltage(leftMagnitude);
                frMotor.setVoltage(rightMagnitude);
        }

        NetworkTable table = NetworkTableInstance.getDefault().getTable("LoggedStuff");
        StructTopic<Pose2d> topic = table.getStructTopic("/Drivetrain/Pose", Pose2d.struct);
        
        StructPublisher<Pose2d> publisher = topic.publish();
        StructSubscriber<?> sub = topic.subscribe(null);

        Logger.recordOutput("", new Pose2d());
        publisher.set(new Pose2d());

        inputs.leftCurrent = getLeftCurrent();
        inputs.leftDistance = getLeftDistance();
        inputs.leftPercent = getLeftPercent();
        inputs.leftTemperature = getLeftTemperature();
        inputs.leftVoltage = getLeftVoltage();

        inputs.rightCurrent = getRightCurrent();
        inputs.rightDistance = getRightDistance();
        inputs.rightPercent = getRightPercent();
        inputs.rightTemperature = getRightTemperature();
        inputs.rightVoltage = getRightVoltage();

        inputs.angle = getAngle();

        Logger.processInputs("Drivetrain_SparkMax", inputs);
    }

    @Override
    public void setLeftSpeed(ControlMode mode, double magnitude) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLeftSpeed'");
    }

    @Override
    public void setRightSpeed(ControlMode mode, double magnitude) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRightSpeed'");
    }

    @Override
    public Current getLeftCurrent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeftCurrent'");
    }

    @Override
    public Distance getLeftDistance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeftDistance'");
    }

    @Override
    public Dimensionless getLeftPercent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeftPercent'");
    }

    @Override
    public Temperature getLeftTemperature() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeftTemperature'");
    }

    @Override
    public Voltage getLeftVoltage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLeftVoltage'");
    }

    @Override
    public Current getRightCurrent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRightCurrent'");
    }

    @Override
    public Distance getRightDistance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRightDistance'");
    }

    @Override
    public Dimensionless getRightPercent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRightPercent'");
    }

    @Override
    public Temperature getRightTemperature() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRightTemperature'");
    }

    @Override
    public Voltage getRightVoltage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRightVoltage'");
    }

    @Override
    public Rotation2d getAngle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAngle'");
    }

    @Override
    public ControlMode getMode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMode'");
    }
    
}
