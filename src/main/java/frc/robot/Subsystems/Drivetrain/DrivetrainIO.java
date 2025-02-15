package frc.robot.Subsystems.Drivetrain;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.ControlMode;
import org.littletonrobotics.junction.AutoLog;

public interface DrivetrainIO {
    @AutoLog
    public class DrivetrainIOInputs {
        Current leftCurrent;
        Distance leftDistance;
        Dimensionless leftPercent;
        Temperature leftTemperature;
        Voltage leftVoltage;

        Current rightCurrent;
        Distance rightDistance;
        Dimensionless rightPercent;
        Temperature rightTemperature;
        Voltage rightVoltage;

        ControlMode mode = ControlMode.Percent;
    }

    /** Updates the inputs and pushes them through the logger. */
    public void updateInputs();

    /**
     * Sets the speed of the left side of the robot.
     * 
     * @param mode The mode to drive with.
     * @param magnitude The magnitude with respect to the mode.
     */
    public void setLeftSpeed(ControlMode mode, double magnitude);

    /**
     * Sets the speed of the right side of the robot.
     * 
     * @param mode The mode to drive with.
     * @param magnitude The magnitude with respect to the mode.
     */
    public void setRightSpeed(ControlMode mode, double magnitude);

    /** Returns the output current of the left motor(s) as a {@link Current}. */
    public Current getLeftCurrent();
    /** Returns the distance traveled of the left motor(s) as a {@link Distance}. */
    public Distance getLeftDistance();
    /** Returns the percent output of the left motor(s) as a {@link Dimensionless}. */
    public Dimensionless getLeftPercent();
    /** Returns the temperature of the left motor(s) as a {@link Temperature} */
    public Temperature getLeftTemperature();
    /** Returns the output voltage of the left motor(s) as a {@link Voltage} */
    public Voltage getLeftVoltage();

    /** Returns the output current of the right motor(s) as a {@link Current}. */
    public Current getRightCurrent();
    /** Returns the distance traveled of the right motor(s) as a {@link Distance}. */
    public Distance getRightDistance();
    /** Returns the percent output of the right motor(s) as a {@link Dimensionless}. */
    public Dimensionless getRightPercent();
    /** Returns the temperature of the right motor(s) as a {@link Temperature} */
    public Temperature getRightTemperature();
    /** Returns the output voltage of the right motor(s) as a {@link Voltage} */
    public Voltage getRightVoltage();

    /** Returns the  */
    public ControlMode getMode();
}