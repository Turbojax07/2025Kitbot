package frc.robot.Subsystems.Roller.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Roller.Roller;

public class RunRoller extends Command {
    private double velocity;
    private Roller roller;

    /**
     * Drives the roller motor at a set velocity.
     * 
     * @param velocity The percent output to run the roller at.
     */
    public RunRoller(double velocity) {
        this.velocity = velocity;

        roller = Roller.getInstance();
    }

    /** Runs once when the command is initially scheduled. */
    @Override
    public void initialize() {}

    /** Runs once every tick that the command is scheduled. */
    @Override
    public void execute() {
        roller.setVelocity(velocity);
    }

    /**
     * Runs once every tick that the command is scheduled.
     * 
     * @return Whether or not the command should end early.
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /** Runs once when the command is cancelled. */
    @Override
    public void end(boolean interrupted) {
        roller.stop();
    }
}