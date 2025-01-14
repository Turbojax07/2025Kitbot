package frc.robot.Subsystems.Roller.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Roller.Roller;

public class RunRoller extends Command {
    private double velocity;
    private Roller roller;

    public RunRoller(double velocity) {
        this.velocity = velocity;

        roller = Roller.getInstance();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        roller.setVelocity(velocity);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        roller.stop();
    }
}
