package frc.robot;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Distance;

public class Constants {
    public enum ControlMode {
        Percent,
        Voltage;
    }

    public static final boolean isReplay = false;

    public class RobotMap {
        public static final int DT_FrontLeftId = 1;
        public static final int DT_FrontRightId = 2;
        public static final int DT_BackLeftId = 3;
        public static final int DT_BackRightId = 4;

        public static final int GYRO_Pigeon2Id = 9;

        public static final int ARM_RollerId = 11;
    }

    public class DriveConstants {
        public static final double maxDriveSpeed = 0.5;
        public static final double maxTurnSpeed = 0.3;
        
        public static final double gearRatio = 8.45;
        public static final Distance wheelDiameter = Inches.of(8);
        public static final Distance robotWidth = Inches.of(26.5);
        public static final double rotToMeters = gearRatio * Math.PI * wheelDiameter.in(Meters);
    }

    public class RollerConstants {
        public static final double gearRatio = 5.4167;
        public static final double momentOfInertia = 0.000224;
    }
}