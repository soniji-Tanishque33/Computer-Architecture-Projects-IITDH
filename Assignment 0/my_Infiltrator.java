public class my_Infiltrator {
    private int X; // Current x position of Infiltrator
    private int Y; // Current y position of Infiltrator

    private int new_X; // Nest x position of Infiltrator
    private int new_Y; // Nest y position of Infiltrator

    private boolean move;


    public my_Infiltrator(my_Border border){ // Initialize the Infiltrator
        this.X = -1;
        this.Y = border.getDepth();
        // Infiltrator is placed at the mid-point of the border on attacking side 
    }

    public void reset(my_Border border){ // Reset the Infiltrator before every run
        this.X = -1;
        this.Y = border.getDepth();
    }

    void decide(my_Sensor sensor_array[][]){ // Infiltrator dicide whether to move or not and where

        if(this.X == sensor_array.length - 1){ // Edge case handeling when the Infiltrator at the other end of the border on the defending side
            if(sensor_array[this.X][this.Y].State){
                this.move = true;
                this.new_X = this.X + 1;

            }
            else{
                this.X += 1;
                return;
            }
        }

        if(this.X > -1 && sensor_array[this.X][this.Y].State){ // Check whether the cell which the Infiltrator is currently in is on or not
            this.move = false;
            return;
        }

        for(int i = -1; i < 2; i++){ // Check for the 3 cell in front of the Infiltrator 
            this.move = false;
            if(! sensor_array[this.X + 1][this.Y + i].State){
                this.move = true;
                this.new_X = this.X + 1;
                this.new_Y = this.Y + i;
                return;
            }
        }

    }

    void move(){ // If the Infiltrator decide to move then this function move the Infiltrator from X and Y to new_X and new_Y
        if(this.move){
            this.move = false;
            this.X = this.new_X;
            this.Y = this.new_Y;
        }
    }

    public int getX(){ // Getter for the X position of Infiltrator
        return this.X;
    }


}
