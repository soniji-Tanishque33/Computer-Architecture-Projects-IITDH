package generic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;
import processor.pipeline.RegisterFile;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		try (            
			InputStream inputStream = new FileInputStream(assemblyProgramFile);
        ) {
			int BUFFER_SIZE = 4;
			byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

			int i = 0;
			int intValue = 0;
 
			RegisterFile new_RegisterFile = new RegisterFile();
			
			bytesRead = inputStream.read(buffer);
			for (byte b : buffer) {
				intValue = (intValue << 8) + (b & 0xFF);
			}
			new_RegisterFile.setProgramCounter(intValue);

			MainMemory new_Memory = new MainMemory();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
				for (byte b : buffer) {
					intValue = (intValue << 8) + (b & 0xFF);
				}
				new_Memory.setWord(i, intValue);
				i++;
            }
			processor.setMainMemory(new_Memory);
			// System.out.println(".");
			
			new_RegisterFile.setValue(0, 0);
			new_RegisterFile.setValue(1, 65535);
			new_RegisterFile.setValue(2, 65535);

			processor.setRegisterFile(new_RegisterFile);
			System.out.println(".");

		} catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public static void simulate()
	{
		Statistics statistics = new Statistics();
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			processor.getOFUnit().performOF();
			processor.getEXUnit().performEX();
			processor.getMAUnit().performMA();
			processor.getRWUnit().performRW();
			statistics.updateStatictics();
			Clock.incrementClock();
		}
		
		// TODO
		// set statistics
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
