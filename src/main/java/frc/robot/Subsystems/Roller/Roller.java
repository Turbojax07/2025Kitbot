package frc.robot.Subsystems.Roller;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlMode;

public class Roller extends SubsystemBase {
    private RollerIO rollerIO;

    private static Roller instance;

    /** 
     * Gets a common instance of the {@link Roller} subsystem.
     * 
     * If the Roller subsystem has not been initialized, then it begins a simulation of the roller.
     * 
     * @return An instance of the {@link Roller} class.
     */
    public static Roller getInstance() {
        if (instance == null) instance = new Roller(new RollerIOSim());

        return instance;
    }

    /**
     * Creates a new instance of the Roller class.
     * 
     * @param rollerIO The IO interface to use for this class.  It must implement {@link RollerIO}.
     */
    public Roller(RollerIO rollerIO) {
        if (instance != null) {
            return;
        }
        
        instance = this;

        this.rollerIO = rollerIO;
    }

    /** Runs once every tick that the subsystem has been initialized. */
    @Override
    public void periodic() {
        rollerIO.updateInputs();
    }

    /**
     * Sets the velocity of the roller in percent output.
     * 
     * @param velocity The percent speed to run the roller at.
     */
    public void setVelocity(double velocity) {
        rollerIO.setSpeed(ControlMode.Percent, velocity);
    }

    /** Stops the roller from moving. */
    public void stop() {
        rollerIO.setSpeed(ControlMode.Percent, 0);
    }
}