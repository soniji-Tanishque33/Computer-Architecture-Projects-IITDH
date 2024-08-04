package processor.memorysystem;

import generic.*;
import processor.Clock;
import processor.pipeline.MemoryAccess;

public class MainMemory implements Element {
	int[] memory;

	public MainMemory() {
		memory = new int[65536];
	}

	public int getWord(int address) {
		return memory[address];
	}

	public void setWord(int address, int value) {
		memory[address] = value;
	}

	public String getContentsAsString(int startingAddress, int endingAddress) {
		if (startingAddress == endingAddress)
			return "";

		StringBuilder sb = new StringBuilder();
		sb.append("\nMain Memory Contents:\n\n");
		for (int i = startingAddress; i <= endingAddress; i++) {
			sb.append(i + "\t\t: " + memory[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void handleEvent(Event e) {
		if (e.getEventType() == Event.EventType.MemoryRead) {
			MemoryReadEvent event = (MemoryReadEvent) e;
			Simulator.getEventQueue().addEvent(
					new MemoryResponseEvent(
							Clock.getCurrentTime(),
							this,
							event.getReqestingForElement(),
							event.getRequestingElement(),
							getWord(event.getAddressToReadFrom()),
							event.getAddressToReadFrom()));
		}
		
		else if(e.getEventType() == Event.EventType.MemoryWrite) {
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			this.setWord(event.getAddressToWriteTo(), event.getValue());
			MemoryAccess memory_access = (MemoryAccess) event.getRequestingElement();
			memory_access.EX_MA_Latch.setMA_Busy(false);
			// memory_access.EX_MA_Latch.setMA_enable(false);
			memory_access.EX_MA_Latch.setMA_enable(true);
			memory_access.OF_EX_Latch.setEX_Busy(false);
			memory_access.MA_RW_Latch.setRW_enable(true);
			memory_access.getMa_RW_Latch().setInstruction(memory_access.getInstruction());
		}

	}

	@Override
	public String indentify() {
		return "MainMemory";
	}
}
