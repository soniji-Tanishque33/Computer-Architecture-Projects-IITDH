public class my_Sensor {

    public boolean State; // hold the state of the sensor

    private double On_Probability; // holds the on probability of the sensor

    public my_Sensor(double On_prob){

        State = false; // sensor is initialized to be off

        On_Probability = On_prob;

    }

    public void change_state(){
        State = Math.random() < On_Probability; // Math.random() return a float uniformly between 0 and 1 so if it less than the On_Probability then the sensor turns on
    }

}
