package frc.robot.Subsystems.Roller;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Constants.ControlMode;
import frc.robot.Constants.RobotMap;
import org.littletonrobotics.junction.Logger;

public class RollerIOSparkMax implements RollerIO {
    private SparkMax roller;
    
    private RollerIOInputsAutoLogged inputs;

    /** Creates a new instance of RollerIOSparkMax. */
    public RollerIOSparkMax() {
        roller = new SparkMax(RobotMap.ARM_RollerId, MotorType.kBrushless);

        inputs = new RollerIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
        inputs.current = getCurrent();
        inputs.percent = getPercent();
        inputs.temperature = getTemperature();
        inputs.voltage = getVoltage();

        Logger.processInputs("Roller_SparkMax", inputs);
    }

    @Override
    public void setSpeed(ControlMode mode, double magnitude) {
        inputs.mode = mode;
        if (mode.equals(ControlMode.Voltage)) magnitude /= RobotController.getInputVoltage();

        roller.set(magnitude);
    }

    @Override
    public Current getCurrent() {
        return Amps.of(roller.getOutputCurrent());
    }

    @Override
    public Dimensionless getPercent() {
        return Percent.of(roller.getAppliedOutput());
    }

    @Override
    public Temperature getTemperature() {
        return Celsius.of(roller.getMotorTemperature());
    }

    @Override
    public Voltage getVoltage() {
        return Volts.of(roller.getBusVoltage()).times(getPercent());
    }

    @Override
    public ControlMode getMode() {
        return inputs.mode;
    }
}