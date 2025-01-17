package frc.robot.Subsystems.Drivetrain;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotMap;
import frc.robot.Subsystems.Gyro.GyroIO;
import frc.robot.Subsystems.Gyro.GyroIOBNO085;

import org.littletonrobotics.junction.Logger;

public class DrivetrainIOSparkMax implements DrivetrainIO {
    private SparkMax flMotor;
    private SparkMax frMotor;
    private SparkMax blMotor;
    private SparkMax brMotor;

    private RelativeEncoder flEncoder;
    private RelativeEncoder frEncoder;
    private RelativeEncoder blEncoder;
    private RelativeEncoder brEncoder;

    private GyroIO gyroIO;

    private double leftMagnitude;
    private double rightMagnitude;

    private DrivetrainIOInputsAutoLogged inputs;

    public DrivetrainIOSparkMax() {
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

        gyroIO = new GyroIOBNO085();

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

        gyroIO.updateInputs();

        Logger.processInputs("Drivetrain_SparkMax", inputs);
    }

    @Override
    public void setLeftSpeed(ControlMode mode, double magnitude) {
        if (mode != inputs.mode) {
            switch (mode) {
                case Percent:
                    leftMagnitude /= flMotor.getBusVoltage();
                    rightMagnitude /= frMotor.getBusVoltage();
                    break;
                case Voltage:
                    leftMagnitude *= flMotor.getBusVoltage();
                    rightMagnitude *= frMotor.getBusVoltage();
            }

            inputs.mode = mode;
        }

        leftMagnitude = magnitude;
    }

    @Override
    public void setRightSpeed(ControlMode mode, double magnitude) {
        if (mode != inputs.mode) {
            switch (mode) {
                case Percent:
                    leftMagnitude /= flMotor.getBusVoltage();
                    rightMagnitude /= frMotor.getBusVoltage();
                    break;
                case Voltage:
                    leftMagnitude *= flMotor.getBusVoltage();
                    rightMagnitude *= frMotor.getBusVoltage();
            }

            inputs.mode = mode;
        }

        rightMagnitude = magnitude;
    }

    @Override
    public Current getLeftCurrent() {
        return Amps.of(flMotor.getOutputCurrent());
    }

    @Override
    public Distance getLeftDistance() {
        return Meters.of((flEncoder.getPosition() % 1) * DriveConstants.rotToMeters);
    }

    @Override
    public Dimensionless getLeftPercent() {
        switch(inputs.mode) {
            case Percent:
                return Percent.of(leftMagnitude);
            case Voltage:
                return Volts.of(leftMagnitude).div(Volts.of(flMotor.getBusVoltage()));
        }

        return null;
    }

    @Override
    public Temperature getLeftTemperature() {
        return Celsius.of(flMotor.getMotorTemperature());
    }

    @Override
    public Voltage getLeftVoltage() {
        switch(inputs.mode) {
            case Percent:
                return Volts.of(leftMagnitude * flMotor.getBusVoltage());
            case Voltage:
                return Volts.of(leftMagnitude);
        }

        return null;
    }

    @Override
    public Current getRightCurrent() {
        return Amps.of(frMotor.getOutputCurrent());
    }

    @Override
    public Distance getRightDistance() {
        return Meters.of((frEncoder.getPosition() % 1) * DriveConstants.rotToMeters);
    }

    @Override
    public Dimensionless getRightPercent() {
        switch(inputs.mode) {
            case Percent:
                return Percent.of(rightMagnitude);
            case Voltage:
                return Volts.of(rightMagnitude).div(Volts.of(frMotor.getBusVoltage()));
        }

        return null;
    }

    @Override
    public Temperature getRightTemperature() {
        return Celsius.of(frMotor.getMotorTemperature());
    }

    @Override
    public Voltage getRightVoltage() {
        switch(inputs.mode) {
            case Percent:
                return Volts.of(rightMagnitude * frMotor.getBusVoltage());
            case Voltage:
                return Volts.of(rightMagnitude);
        }

        return null;
    }

    @Override
    public Rotation2d getAngle() {
        return gyroIO.getHeading();
    }

    @Override
    public ControlMode getMode() {
        return inputs.mode;
    }
}