public class my_Border {
    
    private int Depth ; // Depth of the border
    private int Width ; // Width of the border since in optimal play the infiltrator cannot do beyoud a certain a limit on width
    public my_Sensor sensor_array[][];

    public my_Border(int depth, double on_prob){ // Constructor
        this.Depth = depth; // Initliatization of Depth
        this.Width = this.Depth * 2 + 3; // Initliatization of Width 
        this.sensor_array = new my_Sensor[this.Depth][this.Width]; // Initialization of sensor array
        for(int i = 0; i < this.Depth; i++){
            for(int j = 0; j < this.Width; j++){
                sensor_array[i][j] = new my_Sensor(on_prob);
            }
        }
    }

    public void refreshBorder(){ // Refereshes the border with new random values 
        for(int i = 0; i < this.Depth; i++){
            for(int j = 0; j < this.Width; j++){
                this.sensor_array[i][j].change_state();
            }
        }
    }

    public int getDepth(){ // Getter for te Depth of border

        return this.Depth;

    }


}
