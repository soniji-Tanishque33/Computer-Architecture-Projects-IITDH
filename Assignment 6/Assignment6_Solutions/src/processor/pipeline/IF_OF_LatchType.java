package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	int PC;

	boolean OF_busy;
	boolean OF_wait;
	
	public IF_OF_LatchType()
	{
		OF_enable = true;
		instruction = 0xf0000000;
		OF_busy = false;
		OF_wait = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pc) {
		this.PC = pc;
	}

    public boolean isOFbusy() {
        return OF_busy;
    }

	public void setOF_Busy(boolean oFbusy) {
        OF_busy = oFbusy;
    }

    public void setOF_wait(boolean oFwait) {
        OF_wait = oFwait;
    }

	public boolean getOF_wait() {
		return OF_wait;
    }

}
