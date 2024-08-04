package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		if(EX_MA_Latch.isMA_enable()){
			int instruction=EX_MA_Latch.getInstruction();
			int Op_code = instruction >>> 27;
			int current_PC = EX_MA_Latch.getPC();

			if(Op_code==0b10110)
			{
				int loadresult=containingProcessor.getMainMemory().getWord(EX_MA_Latch.getaluResult());
				MA_RW_Latch.setloadResult(loadresult);
			}
			
			else if(Op_code==0b10111)
			{
				int x=containingProcessor.getRegisterFile().getValue(EX_MA_Latch.getRD());
				containingProcessor.getMainMemory().setWord(EX_MA_Latch.getaluResult(), x);
			}
			MA_RW_Latch.setaluResult(EX_MA_Latch.getaluResult());
			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setRD(EX_MA_Latch.getRD());
			MA_RW_Latch.setPC(current_PC);

			// EX_MA_Latch.setMA_enable(false);
			// MA_RW_Latch.setRW_enable(true);

		}
	}

}
