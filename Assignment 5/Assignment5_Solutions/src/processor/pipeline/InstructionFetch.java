package processor.pipeline;

import configuration.Configuration;
import processor.Clock;
import processor.Processor;

import generic.*;

public class InstructionFetch implements Element {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;


	int i;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch,
			IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;

		i = 0;
	}
	
	public void performIF() {


		if(IF_OF_Latch.getOF_wait()){
			IF_OF_Latch.setOF_wait(false); // new edit
			return; // edit
		}


		if (containingProcessor.getInsert_Data_nop()){
			this.IF_EnableLatch.setIF_enable(false);
		}
		else{
			this.IF_EnableLatch.setIF_enable(true);
		}
		if (IF_EnableLatch.isIF_enable()) {
			if (IF_EnableLatch.isIF_busy()) {
				return;
			}

			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();

			Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							containingProcessor.getRegisterFile().getProgramCounter())
			);

			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_OF_Latch.setPC(currentPC);

			System.out.println(currentPC);

			// IF_OF_Latch.setOF_enable(false);
			IF_OF_Latch.setInstruction(0xf0000000);
			containingProcessor.setisBranchTaken(false);
			IF_EnableLatch.setIF_busy(true);

			IF_OF_Latch.setOF_wait(false); // new edit
			IF_OF_Latch.setOF_enable(false);

		}
	}

	@Override
	public void handleEvent(Event e) {
		if (IF_OF_Latch.isOFbusy()) {
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		} else {
			MemoryResponseEvent event = (MemoryResponseEvent) e;

				// if(containingProcessor.getisBranchTaken()){
				// // containingProcessor.setInsert_Data_nop(true);
				// // Op_code = 0b11110;
				// newInstruction = 0xf0000000;
				// IF_OF_Latch.setInstruction(newInstruction);
				// containingProcessor.setisBranchTaken(false);
				// return;
				// }

			if (containingProcessor.getisBranchTaken()){
				Simulator.setis_branch_taken(true);
				IF_OF_Latch.setInstruction(0xf0000000);
				containingProcessor.setisBranchTaken(false);
				// return;
			}

			else{
				// Simulator.setis_branch_taken(false);
				IF_OF_Latch.setInstruction(event.getValue());
	
			}
			IF_EnableLatch.setIF_busy(false);
			
			IF_OF_Latch.setOF_wait(true); // new edit
			
			IF_OF_Latch.setOF_enable(true);

		}
	}
}
