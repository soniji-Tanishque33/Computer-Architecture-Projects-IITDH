	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $n, %x1
	subi %x1, 2, %x2
	subi %x0, 1, %x3
outerLoop:
	addi %x3, 1, %x3
	bgt %x3, %x2, exit
	add %x0, %x0, %x4
innerLoop:
	bgt %x4, %x2, outerLoop
	load %x4, $a, %x5
	addi %x4, 1, %x7
	load %x7, $a, %x6
	bgt %x6, %x5, swap
	addi %x4, 1, %x4
	jmp innerLoop
swap:
	store %x6, 0, %x4
	store %x5, 0, %x7
	addi %x4, 1, %x4
	jmp innerLoop
exit:
	end