package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int aluResult;
	int instruction;
	int RD;

	int PC;

	boolean MA_Busy;
	
	
	public EX_MA_LatchType()
	{
		MA_enable = true;
		instruction = 0xf0000000;
		MA_Busy = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public int getaluResult() {
		return aluResult;
	}

	public void setaluResult(int aluresult) {
		this.aluResult = aluresult;
	}
	
	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public int getRD() 
	{
		return RD;
	}

	public void setRD(int rd) 
	{
		this.RD = rd;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pc) {
		this.PC = pc;
	}

    public boolean isMA_Busy() {
        return MA_Busy;
    }

	public void setMA_Busy(boolean mA_Busy) {
        MA_Busy = mA_Busy;
    }



}
