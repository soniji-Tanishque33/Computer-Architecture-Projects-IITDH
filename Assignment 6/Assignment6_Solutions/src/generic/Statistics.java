package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;

	static int no_of_nops;
	static int no_branch_taken;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Total number of nops = " + no_of_nops);
			writer.println("Total number of branch taken(Wrongly predicted) = " + no_branch_taken);
			float ICP = (float)numberOfInstructions / (float)numberOfCycles;
			writer.println("ISC (Instruction per Cycle) = " + ICP);
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics

	public void updateStatictics(){
		Statistics.numberOfCycles += 1;
		Statistics.numberOfInstructions += 1;
	}

	public void updateCycles(){
		Statistics.numberOfCycles += 1;
	}

	public void updateInstructions(){
		Statistics.numberOfInstructions += 1;
	}

	public void updateNo_of_nops(){
		Statistics.no_of_nops += 1;
	}

	public void updateNo_branch_taken(){
		Statistics.no_branch_taken += 1;
	}

	public void remove_4_nops(){
		Statistics.no_of_nops -= 4;
	}

	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
}
