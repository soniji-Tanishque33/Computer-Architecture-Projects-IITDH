package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			int instruction=MA_RW_Latch.getInstruction();
			int Op_code = instruction >>> 27;

			if(Op_code==0b10110)
			{
				containingProcessor.getRegisterFile().setValue(MA_RW_Latch.getRD(),MA_RW_Latch.getloadResult());
			}
			else if(Op_code < 0b10111){
				containingProcessor.getRegisterFile().setValue(MA_RW_Latch.getRD(),MA_RW_Latch.getaluResult());
			}
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
