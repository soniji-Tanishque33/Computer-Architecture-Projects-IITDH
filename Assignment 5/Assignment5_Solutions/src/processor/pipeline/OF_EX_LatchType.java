package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;

	int instruction;

	int Op_code;
	// int Branch_target;
	int A;
	int B;
	int RD;
	int PC;

	boolean EX_Busy;
	
	
	public OF_EX_LatchType()
	{
		EX_enable = true;
		instruction = 0xf0000000;
		Op_code = 0b11110;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public void setOp_code(int op_code) {
		Op_code = op_code;
	}

	public int getOp_code() {
		return Op_code;
	}

	// public void setBranch_target(int branch_target) {
	// 	Branch_target = branch_target;
	// }

	// public int getBranch_target() {
	// 	return Branch_target;
	// }

	public void setA(int a) {
		A = a;
	}

	public int getA() {
		return A;
	}

	public void setB(int b) {
		B = b;
	}

	public int getB() {
		return B;
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

    public void setEX_Busy(boolean eX_Busy) {
        EX_Busy = eX_Busy;
    }

	
    public boolean isEX_Busy() {
        return EX_Busy;
    }


}
