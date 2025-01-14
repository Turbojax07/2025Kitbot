package frc.robot.Subsystems.Roller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
}