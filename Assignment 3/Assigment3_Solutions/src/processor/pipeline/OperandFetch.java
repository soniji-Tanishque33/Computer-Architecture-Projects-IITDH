/*
// package processor.pipeline;

// import processor.Processor;

// public class OperandFetch {
// 	Processor containingProcessor;
// 	IF_OF_LatchType IF_OF_Latch;
// 	OF_EX_LatchType OF_EX_Latch;
	
// 	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
// 	{
// 		this.containingProcessor = containingProcessor;
// 		this.IF_OF_Latch = iF_OF_Latch;
// 		this.OF_EX_Latch = oF_EX_Latch;
// 	}
	
// 	public void performOF()
// 	{
// 		if(IF_OF_Latch.isOF_enable())
// 		{
// 			//TODO

// 			int instruction = IF_OF_Latch.getInstruction();

// 			// int registerSource1 = -1;
// 			// int registerSource2 = -1;
// 			// int registerDestination = -1;
// 			// int immediate = -1;

// 			int Op_code = instruction >>> 27;
// 			int Imm = instruction & 0x11;
// 			int Branch_target = ((instruction & 0x16)) + containingProcessor.getRegisterFile().getProgramCounter();

// 			// if (Op_code == 0b11000){ // jmp
// 			// 	registerDestination = ( instruction >> 22 ) & 0x1F;
// 			// 	immediate = instruction & 0x16;
// 			// }
// 			// else if (Op_code == 0b11101){ // end
// 			// }
// 			// else if((Op_code <= 20) && (Op_code % 2 == 0)){ // R3-Type
			
// 			int registerSource1 = ( instruction >> 22 ) & 0x1F;
// 			int registerSource2 = ( instruction >> 17 ) & 0x1F;
// 			int registerDestination = ( instruction >> 12 ) & 0x1F;

// 			int Op1 = containingProcessor.getRegisterFile().getValue(registerSource1);
// 			int Op2;
 
// 			if(Op_code == 0b10111){
// 				Op2 = containingProcessor.getRegisterFile().getValue(registerDestination);
// 			}
// 			else{
// 				Op2 = containingProcessor.getRegisterFile().getValue(registerSource2);
// 			}



// 
// 				// rs1.setValue(registerSource1);
// 				// current_Instruction.setSourceOperand1(rs1);

// 				// rs2.setValue(registerSource2);
// 				// current_Instruction.setSourceOperand2(rs2);
				
// 				// rd.setValue(registerDestination);
// 				// current_Instruction.setDestinationOperand(rd);
// 			// }

// 			// else { // R2I-Type
// 			// 	registerSource1 = ( instruction >> 22 ) & 0x1F;
// 			// 	registerDestination = ( instruction >> 17 ) & 0x1F;
// 			// 	immediate = instruction & 0x11;

// 			// 	// rs1.setValue(registerSource1);
// 			// 	// current_Instruction.setSourceOperand1(rs1);

// 			// 	// rd.setValue(registerDestination);
// 			// 	// current_Instruction.setDestinationOperand(rd);
// 			// }
//  
			
//  			this.OF_EX_Latch.setInstruction(instruction);

// 			this.OF_EX_Latch.setImm(Imm);
// 			this.OF_EX_Latch.setBranch_target(Branch_target);
// 			this.OF_EX_Latch.setA(Op1);
// 			this.OF_EX_Latch.setOp2(Op2);

			
// 			IF_OF_Latch.setOF_enable(false);
// 			OF_EX_Latch.setEX_enable(true);
// 		}
// 	}

// }

*/

package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO

			int instruction = IF_OF_Latch.getInstruction();
			int PC = IF_OF_Latch.getPC();

			int registerSource1 = -1;
			int registerSource2 = -1;
			int registerDestination = -1;
			int immediate = -1;

			int A = 0;
			int B = 0;
			int rd = 0;

			int Op_code = instruction >>> 27;

			if (Op_code == 0b11000){ // jmp
				registerDestination = ( instruction >> 22 ) & 0x1F;
				immediate = instruction & 0x3FFFFF;
				immediate = immediate << 10;
				immediate = immediate >> 10;

				A = containingProcessor.getRegisterFile().getValue(registerDestination);
				B = immediate;
				rd = containingProcessor.getRegisterFile().getValue(registerDestination);
			}

			else if (Op_code == 0b11101){ // end

			}

			else if((Op_code <= 20) && (Op_code % 2 == 0)){ // R3-Type
				registerSource1 = ( instruction >> 22 ) & 0x1F;
				registerSource2 = ( instruction >> 17 ) & 0x1F;
				registerDestination = ( instruction >> 12 ) & 0x1F;

				A = containingProcessor.getRegisterFile().getValue(registerSource1);
				B = containingProcessor.getRegisterFile().getValue(registerSource2);
				rd = registerDestination;

			}

			else { // R2I-Type
				registerSource1 = ( instruction >> 22 ) & 0x1F;
				registerDestination = ( instruction >> 17 ) & 0x1F;
				immediate = instruction & 0x1FFFF;

				if(Op_code == 0b10111){
					A = containingProcessor.getRegisterFile().getValue(registerDestination);
					rd = registerSource1;
				}
				else{
					A = containingProcessor.getRegisterFile().getValue(registerSource1);
					rd = registerDestination;
				}
				if (Op_code >= 0b11001 && Op_code <= 0b11100){
					rd = containingProcessor.getRegisterFile().getValue(registerDestination);
					immediate = immediate << 15;
					immediate = immediate >> 15;
				}
				B = immediate;
			}

			OF_EX_Latch.setPC(PC);
			OF_EX_Latch.setA(A);
			OF_EX_Latch.setB(B);
			OF_EX_Latch.setRD(rd);
			OF_EX_Latch.setInstruction(instruction);
			OF_EX_Latch.setOp_code(Op_code);
			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
