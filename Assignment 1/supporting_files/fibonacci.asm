	.data
n:
	50
	.text
main:
	addi %x0, 0, %x1
	addi %x0, 1, %x2
	add %x1, %x2, %x5
	addi %x0, 65535, %x3
	load %x0, $n, %x4
one:
	store %x0, 0, %x3
	subi %x4, 1, %x4
	beq %x4, %x0, exit
	subi %x3, 1, %x3
two:
	store %x2, 0, %x3
	subi %x4, 1, %x4
	beq %x4, %x0, exit
	subi %x3, 1, %x3
loop:
	store %x5, 0, %x3
	add %x0, %x2, %x1
	add %x0, %x5, %x2
	add %x1, %x2, %x5
	subi %x4, 1, %x4
	beq %x4, %x0, exit
	subi %x3, 1, %x3
	jmp loop
exit:
	end