package frc.robot.Subsystems.Roller;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.ControlMode;
import org.littletonrobotics.junction.AutoLog;

public interface RollerIO  {
    /** The values to log through AdvantageKit. */
    @AutoLog
    public class RollerIOInputs {
        Current current;
        Dimensionless percent;
        Temperature temperature;
        Voltage voltage;

        ControlMode mode;
    }

    /** Updates the values of the inputs defined in the RollerIO interface. */
    public void updateInputs();

    /**
     * Sets the speed of the motor.
     * 
     * @param mode The control mode to set the speed with.
     * @param magnitude The magnitude with respect to the control mode.
     */
    public void setSpeed(ControlMode mode, double magnitude);
    
    /** Gets the output current of the roller motor as a {@link Current}. */
    public Current getCurrent();
    /** Gets the percent output of the roller motor as a {@link Dimensionless}. */
    public Dimensionless getPercent();
    /** Gets the temperature of the roller motor as a {@link Temperature}. */
    public Temperature getTemperature();
    /** Gets the output voltage of the roller motor as a {@link Voltage}. */
    public Voltage getVoltage();

    /** Gets the current mode used to control the roller motor. */
    public ControlMode getMode();
}