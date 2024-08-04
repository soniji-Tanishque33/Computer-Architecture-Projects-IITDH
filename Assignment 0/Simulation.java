import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Simulation {

    public static void main(String[] args) {

        try {

            // Initializing Border Paramenter

            int min_depth = Integer.parseInt(args[0]);
            int max_depth = Integer.parseInt(args[1]);

            int min_prob = Integer.parseInt(args[2]);
            int max_prob = Integer.parseInt(args[3]);

            int prob_step = Integer.parseInt(args[4]);

            if((max_prob - min_prob) % prob_step != 0){
                System.out.println("prob_step must a divisor of max_prob - min_prob");
                return;
            }

            // initializing Write Buffer for file IO

            BufferedWriter writer = new BufferedWriter(new FileWriter("Simulation_Result.txt")); // Human Readable
            BufferedWriter writer_py = new BufferedWriter(new FileWriter("Simulation_Result_Py.txt")); // To be parshed by the python for ploting


            // Writing Border paramenter to the simulation output files

            writer.write("min_depth = " + min_depth + "\n");
            writer_py.write("min_depth = " + min_depth + "\n");

            writer.write("max_depth = " + max_depth + "\n");
            writer_py.write("max_depth = " + max_depth + "\n");

            writer.write("min_prob = " + min_prob + "\n");
            writer_py.write("min_prob = " + min_prob + "\n");

            writer.write("max_prob = " + max_prob + "\n");
            writer_py.write("max_prob = " + max_prob + "\n");

            writer.write("prob_step = " + prob_step + "\n\n");
            writer_py.write("prob_step = " + prob_step + "");

            writer.write("\n---- ---- ---- ----\n\n");
            writer_py.write("\n---- ---- ---- ----\n");

            // Simulation start

            for (int depth = min_depth; depth <= max_depth; depth++) { // Vary Depth of the Border

                writer.write("Depth = " + depth + "\n\n\n");

                for (int proability = min_prob; proability <= max_prob; proability += prob_step) { // Vary On probability for the Sensors 

                    writer.write("Probability = " + proability + "\n");

                    writer_py.write(depth + " ");
                    writer_py.write(proability + " ");

                    int sum = 0;

                    int runs = Integer.parseInt(args[5]); // Number of time the Simulation will run for each Depth and Probality Combination

                    my_Border border_1 = new my_Border(depth, (double) proability / 100); // Initializing Border

                    my_Infiltrator infiltrator_1 = new my_Infiltrator(border_1); // Initializing Infiltrator

                    for (int i = 0; i < runs; i++) {

                        int time = -1; // time set to -1 so simulation can start at time = 0
                        infiltrator_1.reset(border_1); // Reset the Infiltrator before each run

                        while (infiltrator_1.getX() < border_1.getDepth()) { // Exit condition 

                            time++;

                            if (time % 10 == 0) {
                                border_1.refreshBorder(); // Refresh Border every 10 time steps
                            }

                            if (time % 10 == 0) {
                                infiltrator_1.decide(border_1.sensor_array); // Infiltrator deciding to move or not to move
                            } else if (time % 10 == 9) {
                                infiltrator_1.move(); // Infiltrator moves
                            }
                        }

                        sum += time;
                        writer.write(time + ", ");

                    }

                    double avg = (double) sum / runs; // Average time for each run

                    writer.write("\nAverage infiltration time = " + Double.toString(avg));

                    writer_py.write(Double.toString(avg) + " \n");

                    writer.write("\n\n");

                }

                writer.write("\n---- ---- ---- ----\n\n");

            }

            writer.close();
            writer_py.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}