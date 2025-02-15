package frc.robot.Subsystems.Gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
    @AutoLog
    public class GyroIOInputs {
        Angle roll = Rotations.zero();
        Angle pitch = Rotations.zero();
        Angle yaw = Rotations.zero();
        
        LinearAcceleration x_accel = MetersPerSecondPerSecond.zero();
        LinearAcceleration y_accel = MetersPerSecondPerSecond.zero();
        LinearAcceleration z_accel = MetersPerSecondPerSecond.zero();
    }

    /** Updates the values of the inputs defined in {@link GyroIOInputs}. */
    public void updateInputs();

    /** Gets the heading (yaw) of the robot as a {@link Rotation2d}. */
    public Rotation2d getHeading();

    
    /** Gets the roll of the robot as an {@link Angle}. */
    public Angle getRoll();

    /** Gets the pitch of the robot as an {@link Angle}. */
    public Angle getPitch();
    
    /** Gets the yaw of the robot as an {@link Angle}. */
    public Angle getYaw();


    /** Gets the acceleration of the robot along the x axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getXAcceleration();
    
    /** Gets the acceleration of the robot along the y axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getYAcceleration();
    
    /** Gets the acceleration of the robot along the z axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getZAcceleration();
}