package frc.robot.Subsystems.Gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
    @AutoLog
    public class GyroIOInputs{
        Angle roll, pitch, yaw;
        LinearAcceleration x_accel, y_accel, z_accel;
    }

    public void updateInputs();

    public Rotation2d getHeading();

    public Angle getRoll();
    public Angle getPitch();
    public Angle getYaw();

    public LinearAcceleration getXAcceleration();
    public LinearAcceleration getYAcceleration();
    public LinearAcceleration getZAcceleration();
}
