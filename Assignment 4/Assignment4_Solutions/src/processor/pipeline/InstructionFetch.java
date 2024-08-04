package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		if (containingProcessor.getInsert_Data_nop()){
			// this.IF_EnableLatch.setIF_enable(false);
		}	

		else if(IF_EnableLatch.isIF_enable())
		{
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			if(containingProcessor.getisBranchTaken()){
				// containingProcessor.setInsert_Data_nop(true);
				// Op_code = 0b11110;
				newInstruction = 0xf0000000;
				IF_OF_Latch.setInstruction(newInstruction);
				containingProcessor.setisBranchTaken(false);
				return;
			}
			IF_OF_Latch.setInstruction(newInstruction);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
			IF_OF_Latch.setPC(currentPC);

			// IF_EnableLatch.setIF_enable(false);
			// IF_OF_Latch.setOF_enable(true);

		}
	}

}
