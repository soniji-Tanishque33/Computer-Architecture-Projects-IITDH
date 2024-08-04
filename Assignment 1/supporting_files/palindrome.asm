	.data
a:
	102
	.text
main:
	load %x0, $a, %x4
	load %x0, $a, %x5
	addi %x0, 0, %x6
	addi %x0, 0, %x7
loop:
	divi %x5, 10, %x8
	muli %x8, 10, %x8
	sub %x5, %x8, %x7
	muli %x6, 10, %x6
	add %x6, %x7, %x6
	divi %x5, 10, %x5
	bgt %x5, %x0, loop 
	beq %x6, %x4, palindrome
notPalindrome:
	subi %x0, 1, %x10
	end
palindrome:
	addi %x0, 1, %x10
	end