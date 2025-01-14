package frc.robot.Subsystems.Drivetrain;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import frc.robot.Constants.ControlMode;
import org.littletonrobotics.junction.Logger;

public class DrivetrainIOSim implements DrivetrainIO {
    private DifferentialDrivetrainSim driveSim = DifferentialDrivetrainSim.createKitbotSim(KitbotMotor.kDoubleNEOPerSide, KitbotGearing.k8p45, KitbotWheelSize.kEightInch, null);

    private DrivetrainIOInputsAutoLogged inputs;

    private double leftVoltage;
    private double rightVoltage;

    public DrivetrainIOSim() {
        inputs = new DrivetrainIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
        driveSim.setInputs(leftVoltage, rightVoltage);

        driveSim.update(0.02);

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

        Logger.processInputs("Drivetrain_Sim", inputs);
    }

    @Override
    public void setLeftSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Percent) magnitude *= RobotController.getInputVoltage();

        leftVoltage = magnitude;

        inputs.mode = mode;
    }

    @Override
    public void setRightSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Percent) magnitude *= RobotController.getInputVoltage();    

        rightVoltage = magnitude;

        inputs.mode = mode;
    }

    @Override
    public Current getLeftCurrent() {
        return Amps.of(driveSim.getLeftCurrentDrawAmps());
    }

    @Override
    public Distance getLeftDistance() {
        return Meters.of(driveSim.getLeftPositionMeters());
    }

    @Override
    public Dimensionless getLeftPercent() {
        switch(inputs.mode) {
            case Percent:
                return Percent.of(leftVoltage);
            case Voltage:
                return Volts.of(leftVoltage).div(Volts.of(RobotController.getInputVoltage()));
        }

        return null;
    }

    @Override
    public Temperature getLeftTemperature() {
        return Celsius.zero();
    }

    @Override
    public Voltage getLeftVoltage() {
        switch(inputs.mode) {
            case Percent:
                return Volts.of(RobotController.getInputVoltage()).times(Percent.of(leftVoltage));
            case Voltage:
                return Volts.of(leftVoltage);
        }

        return null;
    }

    @Override
    public Current getRightCurrent() {
        return Amps.of(driveSim.getCurrentDrawAmps());
    }

    @Override
    public Distance getRightDistance() {
        return Meters.of(driveSim.getRightPositionMeters());
    }

    @Override
    public Dimensionless getRightPercent() {
        switch(inputs.mode) {
            case Percent:
                return Percent.of(rightVoltage);
            case Voltage:
                return Volts.of(rightVoltage).div(Volts.of(RobotController.getInputVoltage()));
        }

        return null;
    }

    @Override
    public Temperature getRightTemperature() {
        return Celsius.zero();
    }

    @Override
    public Voltage getRightVoltage() {
        switch(inputs.mode) {
            case Percent:
                return Volts.of(RobotController.getInputVoltage()).times(Percent.of(rightVoltage));
            case Voltage:
                return Volts.of(rightVoltage);
        }

        return null;
    }
    
    @Override
    public Rotation2d getAngle() {
        return driveSim.getHeading();
    }

    @Override
    public ControlMode getMode() {
        return inputs.mode;
    }
}
