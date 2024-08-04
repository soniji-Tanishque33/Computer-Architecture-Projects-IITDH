package processor.pipeline;

import configuration.Configuration;
import generic.*;
import processor.Clock;
import processor.Processor;

// import processor.pipeline.*;;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	public EX_MA_LatchType EX_MA_Latch;
	public MA_RW_LatchType MA_RW_Latch;
	public OF_EX_LatchType OF_EX_Latch;

	int instruction;
	int RD;
	int current_PC;

	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	public MA_RW_LatchType getMa_RW_Latch(){
		return MA_RW_Latch;
	}

	public int getInstruction(){
		return instruction;
	}
	
	public void performMA()
	{
		//TODO

		if(EX_MA_Latch.isMA_Busy()){
			OF_EX_Latch.setEX_Busy(true);
			return;
		}

		if(EX_MA_Latch.isMA_enable()){
			instruction=EX_MA_Latch.getInstruction();
			int Op_code = instruction >>> 27;
			current_PC = EX_MA_Latch.getPC();

			if(Op_code==0b10110)
			{
				// int loadresult=containingProcessor.getMainMemory().getWord(EX_MA_Latch.getaluResult());
				Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							EX_MA_Latch.getaluResult())
				);
				// MA_RW_Latch.setloadResult(loadresult);
				MA_RW_Latch.setInstruction(0xf0000000);
				EX_MA_Latch.setMA_Busy(true);
				RD = EX_MA_Latch.getRD();
				return;
			}

			
			else if(Op_code==0b10111)
			{
				int x=containingProcessor.getRegisterFile().getValue(EX_MA_Latch.getRD());
				// containingProcessor.getMainMemory().setWord(EX_MA_Latch.getaluResult(), x);
				Simulator.getEventQueue().addEvent(
					new MemoryWriteEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							EX_MA_Latch.getaluResult(),
							x)
				);

				MA_RW_Latch.setInstruction(0xf0000000);
				EX_MA_Latch.setMA_Busy(true);
				return;
			}


			MA_RW_Latch.setaluResult(EX_MA_Latch.getaluResult());
			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setRD(EX_MA_Latch.getRD());
			MA_RW_Latch.setPC(current_PC);
		}
	}

	@Override
	public void handleEvent(Event e) {
		if(e.getEventType() == Event.EventType.MemoryResponse) {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			MA_RW_Latch.setloadResult(event.getValue());
			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setRD(RD);
			MA_RW_Latch.setPC(current_PC);
			EX_MA_Latch.setMA_Busy(false);
			OF_EX_Latch.setEX_Busy(false);
		}

	}

}
