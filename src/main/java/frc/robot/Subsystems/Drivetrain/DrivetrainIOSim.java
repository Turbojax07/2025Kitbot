package frc.robot.Subsystems.Drivetrain;

import static edu.wpi.first.units.Units.*;

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

    public DrivetrainIOSim() {
        inputs = new DrivetrainIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
        driveSim.setInputs(inputs.leftVoltage.in(Volts), inputs.rightVoltage.in(Volts));

        driveSim.update(0.02);

        inputs.leftCurrent = getLeftCurrent();
        inputs.leftDistance = getLeftDistance();
        inputs.leftPercent = getLeftPercent();
        inputs.leftTemperature = getLeftTemperature();

        inputs.rightCurrent = getRightCurrent();
        inputs.rightDistance = getRightDistance();
        inputs.rightPercent = getRightPercent();
        inputs.rightTemperature = getRightTemperature();

        Logger.processInputs("Drivetrain_Sim", inputs);
    }

    @Override
    public void setLeftSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Percent) magnitude *= RobotController.getInputVoltage();

        inputs.leftVoltage = Volts.of(magnitude);

        inputs.mode = mode;
    }

    @Override
    public void setRightSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Percent) magnitude *= RobotController.getInputVoltage();    

        inputs.rightVoltage = Volts.of(magnitude);

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
        return inputs.leftVoltage.div(Volts.of(RobotController.getInputVoltage()));
    }

    @Override
    public Temperature getLeftTemperature() {
        return Celsius.zero();
    }

    @Override
    public Voltage getLeftVoltage() {
        return inputs.leftVoltage;
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
        return inputs.rightVoltage.div(Volts.of(RobotController.getInputVoltage()));
    }

    @Override
    public Temperature getRightTemperature() {
        return Celsius.zero();
    }

    @Override
    public Voltage getRightVoltage() {
        return inputs.rightVoltage;
    }
    
    @Override
    public ControlMode getMode() {
        return inputs.mode;
    }

    public Rotation2d getHeading() {
        return driveSim.getHeading();
    }
}