	.data
a:
	9
	.text
main:
	load %x0, $a, %x4
	andi %x4, 1, %x10
	beq %x10, %x0, even
	end
even:
	subi %x10, 1, %x10
	end