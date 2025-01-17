package frc.robot.Subsystems.Roller;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.RollerConstants;
import org.littletonrobotics.junction.Logger;

public class RollerIOSim implements RollerIO {
    private FlywheelSim roller;

    private RollerIOInputsAutoLogged inputs;

    private double voltage;

    /** Creates a new instance of RollerIOSim. */
    public RollerIOSim() {
        var system = LinearSystemId.createFlywheelSystem(DCMotor.getNEO(1), RollerConstants.momentOfInertia, RollerConstants.gearRatio);
        
        roller = new FlywheelSim(system, DCMotor.getNEO(1));

        inputs = new RollerIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
        roller.setInputVoltage(voltage);

        roller.update(0.02);

        inputs.current = getCurrent();
        inputs.percent = getPercent();
        inputs.temperature = getTemperature();
        inputs.voltage = getVoltage();

        Logger.processInputs("Roller_Sim", inputs);
    }

    @Override
    public void setSpeed(ControlMode mode, double magnitude) {
        if (mode == ControlMode.Percent) magnitude *= RobotController.getInputVoltage();

        voltage = magnitude;

        inputs.mode = mode;
    }

    @Override
    public Current getCurrent() {
        return Amps.of(roller.getCurrentDrawAmps());
    }

    @Override
    public Dimensionless getPercent() {
        return roller.getAngularVelocity().div(RadiansPerSecond.of(DCMotor.getNEO(1).freeSpeedRadPerSec));
    }

    @Override
    public Temperature getTemperature() {
        return Celsius.zero();
    }

    @Override
    public Voltage getVoltage() {
        return Volts.of(roller.getInputVoltage());
    }

    @Override
    public ControlMode getMode() {
        return inputs.mode;
    }
}