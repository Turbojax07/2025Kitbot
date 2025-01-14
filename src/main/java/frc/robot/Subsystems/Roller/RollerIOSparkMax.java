package frc.robot.Subsystems.Roller;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Dimensionless;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.ControlMode;

public class RollerIOSparkMax implements RollerIO {

    @Override
    public void updateInputs() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInputs'");
    }

    @Override
    public void setSpeed(ControlMode mode, double magnitude) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSpeed'");
    }

    @Override
    public Current getCurrent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrent'");
    }

    @Override
    public Dimensionless getPercent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPercent'");
    }

    @Override
    public Temperature getTemperature() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTemperature'");
    }

    @Override
    public Voltage getVoltage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVoltage'");
    }

    @Override
    public ControlMode getMode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMode'");
    }
    
}
