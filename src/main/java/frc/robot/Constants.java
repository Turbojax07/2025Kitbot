package frc.robot;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Distance;
import frc.robot.Subsystems.Drivetrain.DrivetrainIO;
import frc.robot.Subsystems.Drivetrain.DrivetrainIOSparkMax;
import frc.robot.Subsystems.Gyro.GyroIO;
import frc.robot.Subsystems.Gyro.GyroIOBNO085;

public class Constants {
    public enum ControlMode {
        Percent,
        Voltage;
    }

    public static final boolean isReplay = false;

    public class RealIOClasses {
        public static final DrivetrainIO drivetrainIO = new DrivetrainIOSparkMax();
        public static final GyroIO gyroIO = new GyroIOBNO085();
    }

    public class DriveConstants {
        public static final int flId = 1;
        public static final int frId = 2;
        public static final int blId = 3;
        public static final int brId = 4;

        public static final double maxDriveSpeed = 0.5;
        public static final double maxTurnSpeed = 0.3;
        
        public static final double gearRatio = 8.45;
        public static final Distance wheelDiameter = Units.Inches.of(8);
        public static final Distance trackWidth = Units.Inches.of(0);
        public static final double rotToMeters = gearRatio * Math.PI * wheelDiameter.in(Meters);
    }

    public class GyroConstants {
        public static final double MILLI_G_TO_MS2 = 0.0098067; // < Scalar to convert milli-gs to m/s^2
        public static final double DEGREE_SCALE = 0.01;        // < To convert the degree values
        public static final int baudrate = 115200;              // < Baud rate of the serial connection
    }

    public class RollerConstants {
        public static final double gearRatio = 5.4167;
        public static final double momentOfInertia = 0.000224;
    }
}
