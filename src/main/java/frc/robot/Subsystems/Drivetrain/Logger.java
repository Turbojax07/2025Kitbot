package frc.robot.Subsystems.Drivetrain;

import java.util.HashMap;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.GenericPublisher;
import edu.wpi.first.networktables.GenericSubscriber;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.networktables.StructSubscriber;
import edu.wpi.first.networktables.StructTopic;
import edu.wpi.first.networktables.Topic;
import edu.wpi.first.util.WPISerializable;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.struct.Struct;
import edu.wpi.first.util.struct.StructSerializable;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Logger {
    private NetworkTable table;

    public Logger() {
        this("Logger");
    }

    public Logger(String tableName) {
        table = NetworkTableInstance.getDefault().getTable(tableName);
    }

    public void putString(String key, String value) {
        StringTopic topic = table.getStringTopic(key);
        StringPublisher pub = topic.publish();
        
        pub.set(value);

        pub.close();
    }

    public void putSerializable(String key, WPISerializable value, Struct<WPISerializable> struct) {
        StructTopic<WPISerializable> topic = table.getStructTopic(key, struct);
        StructPublisher<WPISerializable> pub = topic.publish();

        pub.set(value);

        pub.close();

        SmartDashboard.putData(new Mechanism2d(0, 0));
    }

    public void putSendable(String key, Sendable value) {
        
    }

    public String getString(String key) {
        StringTopic topic = table.getStringTopic(key);
        StringSubscriber subscriber = topic.subscribe("");

        String toReturn = subscriber.get();

        subscriber.close();

        return toReturn;
    }

    public Double getDouble(String key) {
        DoubleTopic topic = table.getDoubleTopic(key);
        DoubleSubscriber subscriber = topic.subscribe(0);

        Double toReturn = subscriber.get();

        subscriber.close();

        return toReturn;
    }

    public Boolean getBoolean(String key) {
        BooleanTopic topic = table.getBooleanTopic(key);
        BooleanSubscriber subscriber = topic.subscribe(false);

        Boolean toReturn = subscriber.get();

        subscriber.close();

        return toReturn;
    }


    /**
     * Due to limitations in Java (or my lack of knowledge), this returns an instance of the {@link WPISerializable} interface.
     * 
     * Make sure to cast the output of this function to whatever variable you need.
     * 
     * @param key The place the value is stored in NetworkTables.
     * @param struct The struct the value inherits from.
     * 
     * @return The object on NetworkTables.
     */
    public WPISerializable getStruct(String key, Struct<WPISerializable> struct) {
        StructTopic<WPISerializable> topic = table.getStructTopic(key, struct);
        StructSubscriber<WPISerializable> subscriber = topic.subscribe(null);

        WPISerializable toReturn = subscriber.get();

        subscriber.close();

        return toReturn;
    }
}