package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int loadResult;
	int instruction;
	int aluResult;
	int RD;

	
	public MA_RW_LatchType()
	{
		RW_enable = false;
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

}
