	.data
a:
	5
	.text
main:
	load %x0, $a, %x4
	addi %x0, 2, %x2
	beq %x4, %x2, prime
loop:
	div %x4, %x2, %x3
	mul %x3, %x2, %x3
	beq %x3, %x4, notPrime
	addi %x2, 1, %x2
	bgt %x4, %x2, loop
prime:
	addi %x0, 1, %x10
	end
notPrime:
	subi %x0, 1, %x10
	end