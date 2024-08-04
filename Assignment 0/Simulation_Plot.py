import numpy as np
import matplotlib.pyplot as plt

Result_file = open("Simulation_Result_Py.txt", "r") # Reading the file from the Simulation

Lines = Result_file.readlines() # Spliting the file into lines

# Extrating meta data from the file

min_depth = int( Lines[0].split(" ")[-1] )
max_depth = int( Lines[1].split(" ")[-1] )

min_prob = int( Lines[2].split(" ")[-1] )
max_prob = int( Lines[3].split(" ")[-1] )
prob_step = int( Lines[4].split(" ")[-1] )


# Preparing the array as per the meta data

depth_count = max_depth - min_depth + 1
prob_count = ( max_prob - min_prob ) // prob_step + 1


Result_array = np.zeros( ( depth_count,  prob_count ) , dtype=float)

# Reading the data into the array

for line in Lines[6:]:

    x = int(line.split(" ")[0]) - min_depth

    y = (int(line.split(" ")[1]) - min_prob) // prob_step 

    Result_array[x][y] = float(line.split(" ")[2])

print(Result_array)

# Plot the data based on the Depth and Probability 

# Probability on the X axis and Time on the Y axis 
# Depth is ploted seperate line in the graph with label corresponding the each depth

for i in range(len(Result_array)):
    plt.plot(range(min_prob, max_prob + prob_step, prob_step), Result_array[i], label=f"Depth{i + min_depth}")

plt.xlim(0, 100)
plt.legend()
plt.show()
    