package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int loadResult;
	int instruction;
	int aluResult;
	int RD;

	int Prev_RD;

	int PC;

	
	public MA_RW_LatchType()
	{
		RW_enable = true;
		instruction = 0xf0000000;
		Prev_RD = -1;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

    public void setloadResult(int loadresult) {
        loadResult = loadresult;
    }

	public int getloadResult() {
        return loadResult;
    }

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public int getaluResult() {
		return aluResult;
	}

	public void setaluResult(int aluresult) {
		this.aluResult = aluresult;
	}

	public int getRD() 
	{
		return RD;
	}

	public void setRD(int rd) 
	{
		this.RD = rd;
	}

	public int getPrev_RD() 
	{
		return Prev_RD;
	}

	public void setPrev_RD(int prev_rd) 
	{
		this.Prev_RD = prev_rd;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pc) {
		this.PC = pc;
	}

}
