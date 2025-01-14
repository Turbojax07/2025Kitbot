package frc.robot.Subsystems.Roller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlMode;

public class Roller extends SubsystemBase {
    private RollerIO rollerIO;

    private static Roller instance;

    public static Roller getInstance() {
        if (instance == null) instance = new Roller(new RollerIOSim());

        return instance;
    }

    public Roller(RollerIO rollerIO) {
        this.rollerIO = rollerIO;

        instance = this;
    }

    @Override
    public void periodic() {
        rollerIO.updateInputs();
    }

    public void setVelocity(double velocity) {
        rollerIO.setSpeed(ControlMode.Percent, velocity);
    }

    public void stop() {
        rollerIO.setSpeed(ControlMode.Percent, 0);
    }
}