package processor.pipeline;

public class EX_IF_LatchType {
	
	boolean isBranchTaken;
	int branchPC;

	public EX_IF_LatchType()
	{
	}

	public boolean getisBranchTaken() {
		return isBranchTaken;
	}

	public void setisBranchTaken(boolean isbranchtaken) {
		this.isBranchTaken = isbranchtaken;
	}

	public int getbranchPC() {
		return branchPC;
	}

	public void setbranchPC(int branchpc) {
		this.branchPC = branchpc;
	}
	

}
