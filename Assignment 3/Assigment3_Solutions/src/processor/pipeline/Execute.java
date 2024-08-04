package processor.pipeline;

import processor.Processor;
import generic.Simulator;


public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		//TODO
		if(OF_EX_Latch.isEX_enable())
		{

			int Op_code = OF_EX_Latch.getOp_code();
			int instruction = OF_EX_Latch.getInstruction();

			int current_PC = OF_EX_Latch.getPC();
			int branchPC = 0;

			int aluResult = 0;

			int A = OF_EX_Latch.getA();
			int B = OF_EX_Latch.getB();
			int RD = OF_EX_Latch.getRD();

			switch (Op_code) {
				case 0:
				case 1:
				case 22:
				case 23:
					aluResult = A + B;
					break;
				case 2:
				case 3:
					aluResult = A - B;
					break;
				case 4:
				case 5:
					aluResult = A * B;
					break;
				case 6:
				case 7:
					aluResult = A / B;
					int remainder = A % B;
					containingProcessor.getRegisterFile().setValue(31,remainder);
					break;		
				case 8:
				case 9:
					aluResult = A & B;
					break;		
				case 10:
				case 11:
					aluResult = A | B;
					break;
				case 12:
				case 13:
					aluResult = A ^ B;
					break;
				case 14:
				case 15:
					aluResult = (A < B) ? 1 : 0;
					break;
				case 16:
				case 17:
					aluResult = A << B;
					break;	
				case 18:
				case 19:
					aluResult = A >>> B;
					break;		
				case 20:
				case 21:
					aluResult = A >> B;
					break;
				case 24:
					branchPC = current_PC + A + B;
					// EX_IF_Latch.setisBranchTaken(true);
					// EX_IF_Latch.setbranchPC(branchPC);
					containingProcessor.getRegisterFile().setProgramCounter(branchPC);
					break;
				case 25:
					if (A == RD){
						branchPC = current_PC + B;
						// EX_IF_Latch.setisBranchTaken(true);
						// EX_IF_Latch.setbranchPC(branchPC);
						containingProcessor.getRegisterFile().setProgramCounter(branchPC);
					}
					break;
				case 26:
					if (A != RD){
						branchPC = current_PC + B;
						// EX_IF_Latch.setisBranchTaken(true);
						// EX_IF_Latch.setbranchPC(branchPC);
						containingProcessor.getRegisterFile().setProgramCounter(branchPC);
					}
					break;
				case 27:
					if (A < RD){
						branchPC = current_PC + B;
						// EX_IF_Latch.setisBranchTaken(true);
						// EX_IF_Latch.setbranchPC(branchPC);
						containingProcessor.getRegisterFile().setProgramCounter(branchPC);
					}
					break;					
				case 28:
					if (A > RD){
						branchPC = current_PC + B;
						// EX_IF_Latch.setisBranchTaken(true);
						// EX_IF_Latch.setbranchPC(branchPC);
						containingProcessor.getRegisterFile().setProgramCounter(branchPC);
					}
					break;
				case 29:
					Simulator.setSimulationComplete(true);
					break;
				default:
					break;
			}

			
			EX_MA_Latch.setaluResult(aluResult);
			EX_MA_Latch.setInstruction(instruction);
			EX_MA_Latch.setRD(RD);
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);

		}
	}

}
