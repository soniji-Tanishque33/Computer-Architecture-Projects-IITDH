package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.INPUT_STREAM;

import java.io.IOException;

import generic.Operand.OperandType;


public class Simulator {
		
	static FileInputStream inputcodeStream = null;

	public static int get_value_or_label(Operand ins){

		int int_ins;

		if(ins.getOperandType() == OperandType.Label){
			int_ins = ParsedProgram.symtab.get(ins.getLabelValue());
		}
		else{
			int_ins = ins.getValue();
		}

		return int_ins;
	}

	public static Map<Instruction.OperationType, Integer> mapping = new HashMap() {{
		put(Instruction.OperationType.add,  0b1100000); // R3-Type
		put(Instruction.OperationType.addi, 0b1000001); // R2I-Type
		put(Instruction.OperationType.sub,  0b1100010); // R3-Type
		put(Instruction.OperationType.subi, 0b1000011); // R2I-Type
		put(Instruction.OperationType.mul,  0b1100100); // R3-Type
		put(Instruction.OperationType.muli, 0b1000101); // R2I-Type
		put(Instruction.OperationType.div,  0b1100110); // R3-Type
		put(Instruction.OperationType.divi, 0b1000111); // R2I-Type
		put(Instruction.OperationType.and,  0b1101000); // R3-Type
		put(Instruction.OperationType.andi, 0b1001001); // R2I-Type
		put(Instruction.OperationType.or,   0b1101010); // R3-Type
		put(Instruction.OperationType.ori,  0b1001011); // R2I-Type
		put(Instruction.OperationType.xor,  0b1101100); // R3-Type
		put(Instruction.OperationType.xori, 0b1001101); // R2I-Type
		put(Instruction.OperationType.slt,  0b1101110); // R3-Type
		put(Instruction.OperationType.slti, 0b1001111); // R2I-Type
		put(Instruction.OperationType.sll,  0b1110000); // R3-Type
		put(Instruction.OperationType.slli, 0b1010001); // R2I-Type
		put(Instruction.OperationType.srl,  0b1110010); // R3-Type
		put(Instruction.OperationType.srli, 0b1010011); // R2I-Type
		put(Instruction.OperationType.sra,  0b1110100); // R3-Type
		put(Instruction.OperationType.srai, 0b1010101); // R2I-Type
		put(Instruction.OperationType.load, 0b1010110); // R2I-Type
		put(Instruction.OperationType.store,0b1010111); // R2I-Type
		put(Instruction.OperationType.jmp,  0b0111000); // R3-Type
		put(Instruction.OperationType.beq,  0b1011001); // R2I-Type
		put(Instruction.OperationType.bne,  0b1011010); // R2I-Type
		put(Instruction.OperationType.blt,  0b1011011); // R2I-Type
		put(Instruction.OperationType.bgt,  0b1011100); // R2I-Type
		put(Instruction.OperationType.end,  0b0111101); // R2I-Type
	}};
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode
        String outputFile = objectProgramFile;
 
        try (            
			// InputStream inputStream = new FileInputStream(inputFile);
            OutputStream outputStream = new FileOutputStream(outputFile);
        ) {

			//2. write the firstCodeAddress to the file
			byte[] byte_addressCode = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			outputStream.write(byte_addressCode);

			
			//3. write the data to the file
			for (Integer temp_data : ParsedProgram.data) {
				byte[] byte_data = ByteBuffer.allocate(4).putInt(temp_data).array();
				outputStream.write(byte_data);	
			}

			//4. assemble one instruction at a time, and write to the file
			for (Instruction current_ins : ParsedProgram.code) {
				int opcode = (mapping.get(current_ins.operationType) & 0b11111);
				int operation_type = (mapping.get(current_ins.operationType) >>> 5);
				int binary_ins = 0;

				if (operation_type == 3) {
					binary_ins = 0;
					int rs1 = current_ins.getSourceOperand1().value;
					int rs2 = current_ins.getSourceOperand2().value;
					int rd = current_ins.getDestinationOperand().value;
					// binary_ins = (opcode + rs1_value + rs2_value + rd_value + unused_bits);
					binary_ins = (opcode << (32 - 5)) + (rs1 << (32 - 10)) + (rs2 << (32 - 15)) + (rd << (32 - 20));

				}
				else if (operation_type == 2) {
					int pc = current_ins.getProgramCounter();
					int rs1 = current_ins.getSourceOperand1().value;
					int rs2 = current_ins.getSourceOperand2().value;
					int rd = current_ins.getDestinationOperand().value;	
					// //11001-beq; 11010-bne; 11011-bne; 11100-bgt
					if (opcode == 25 || opcode == 26 || opcode == 27 || opcode == 28) {
						rd = ParsedProgram.symtab.get(current_ins.destinationOperand.labelValue);
						int offset = rd - pc;
						binary_ins = (opcode << (32 - 5)) + (rs1 << (32 - 10)) + (rs2 << (32 - 15)) + (offset & ((1 << 17) - 1));
					}
					//if instructions in load or store
					else{
						// rd = current_ins.destinationOperand.value;
						// rs2 = ParsedProgram.symtab.get(current_ins.sourceOperand2.labelValue);
						rd = get_value_or_label(current_ins.getDestinationOperand());
						rs2 = get_value_or_label(current_ins.getSourceOperand2());
						binary_ins = (opcode << (32 - 5)) + (rs1 << (32 - 10)) + (rd << (32 - 15)) + (rs2);
					}
				}
				else if (operation_type == 1) {
					
					// //if instruction is jmp in ToyRISC (branch in SimpleRISC)
					if (opcode == 24) {
						int rd = ParsedProgram.symtab.get(current_ins.destinationOperand.labelValue);
						int pc = current_ins.getProgramCounter();
						int offset = rd - pc;
						binary_ins = (opcode << (32 - 5)) + (offset & ((1 << 22) - 1));
					}

					// //if Instruction is end
					else if (opcode == 29) {
						binary_ins = opcode << (32 - 5);
					}
				}
				else {
					continue;
				}

				byte[] instBinary = ByteBuffer.allocate(4).putInt(binary_ins).array();
				outputStream.write(instBinary);	

			}


        } catch (IOException ex) {
            ex.printStackTrace();
        }

		//5. close the file
	}
	
}
