package frc.robot.Subsystems.Roller;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.ControlMode;
import org.littletonrobotics.junction.AutoLog;

public interface RollerIO  {
    @AutoLog
    public class RollerIOInputs {
        Current current;
        Dimensionless percent;
        Temperature temperature;
        Voltage voltage;

        ControlMode mode;
    }

    public void updateInputs();

    public void setSpeed(ControlMode mode, double magnitude);
    
    public Current getCurrent();
    public Dimensionless getPercent();
    public Temperature getTemperature();
    public Voltage getVoltage();

    public ControlMode getMode();
}
