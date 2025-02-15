package frc.robot.Subsystems.Drivetrain;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.RobotMap;
import org.littletonrobotics.junction.Logger;

public class DrivetrainIOTalonSRX implements DrivetrainIO {
    private TalonSRX flMotor;
    private TalonSRX frMotor;
    private TalonSRX blMotor;
    private TalonSRX brMotor;

    private DrivetrainIOInputsAutoLogged inputs;

    public DrivetrainIOTalonSRX() {
        flMotor = new TalonSRX(RobotMap.DT_FrontLeftId);
        frMotor = new TalonSRX(RobotMap.DT_FrontRightId);
        blMotor = new TalonSRX(RobotMap.DT_BackLeftId);
        brMotor = new TalonSRX(RobotMap.DT_BackRightId);

        blMotor.follow(flMotor);
        brMotor.follow(frMotor);

        flMotor.setInverted(true);

        inputs = new DrivetrainIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
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

        Logger.processInputs("Drivetrain_SparkMax", inputs);
    }

    @Override
    public void setLeftSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Voltage) magnitude /= flMotor.getBusVoltage();

        flMotor.set(TalonSRXControlMode.PercentOutput, magnitude);

        inputs.mode = mode;
    }

    @Override
    public void setRightSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Voltage) magnitude /= frMotor.getBusVoltage();

        frMotor.set(TalonSRXControlMode.PercentOutput, magnitude);

        inputs.mode = mode;
    }

    @Override
    public Current getLeftCurrent() {
        return Amps.of(flMotor.getStatorCurrent());
    }

    @Override
    public Distance getLeftDistance() {
        return Meters.of(0);
    }

    @Override
    public Dimensionless getLeftPercent() {
        return Percent.of(flMotor.getMotorOutputPercent());
    }

    @Override
    public Temperature getLeftTemperature() {
        return Celsius.of(flMotor.getTemperature());
    }

    @Override
    public Voltage getLeftVoltage() {
        return Volts.of(flMotor.getMotorOutputVoltage());
    }

    @Override
    public Current getRightCurrent() {
        return Amps.of(frMotor.getStatorCurrent());
    }

    @Override
    public Distance getRightDistance() {
        return Meters.of(0);
    }

    @Override
    public Dimensionless getRightPercent() {
        return Percent.of(frMotor.getMotorOutputPercent());
    }

    @Override
    public Temperature getRightTemperature() {
        return Celsius.of(frMotor.getTemperature());
    }

    @Override
    public Voltage getRightVoltage() {
        return Volts.of(frMotor.getMotorOutputVoltage());
    }

    @Override
    public ControlMode getMode() {
        return inputs.mode;
    }
}