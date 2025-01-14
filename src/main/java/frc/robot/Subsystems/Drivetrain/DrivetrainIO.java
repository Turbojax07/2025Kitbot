package frc.robot.Subsystems.Drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
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

        Rotation2d angle;

        ControlMode mode = ControlMode.Percent;
    }

    public void updateInputs();

    public void setLeftSpeed(ControlMode mode, double magnitude);
    public void setRightSpeed(ControlMode mode, double magnitude);

    public Current getLeftCurrent();
    public Distance getLeftDistance();
    public Dimensionless getLeftPercent();
    public Temperature getLeftTemperature();
    public Voltage getLeftVoltage();

    public Current getRightCurrent();
    public Distance getRightDistance();
    public Dimensionless getRightPercent();
    public Temperature getRightTemperature();
    public Voltage getRightVoltage();

    public Rotation2d getAngle();
    public ControlMode getMode();
}
