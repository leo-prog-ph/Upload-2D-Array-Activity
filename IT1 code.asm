.model small
.stack 100h

.data
    msgInput db 13,10,'1. Enter Decimal Number: $'
    msgMenu  db 13,10,10,' Choose a Conversion '
             db 13,10,'1. Binary'
             db 13,10,'2. Octal'
             db 13,10,'3. Hexadecimal'
             db 13,10,'Choice: $'
             
    msgRes   db 13,10,'Result: $'
    msgError db 13,10,'ERROR: Invalid choice! Please run again and select 1, 2, or 3.$'
    msgMax   db 13,10,'ERROR: Number too large! Maximum is 65535.$'
    
    num      dw ? ;The Variable stores the final decimal value

.code
main proc
    mov ax, @data
    mov ds, ax
                                  
    
    ;Step 1: it input the number 
    lea dx, msgInput
    mov ah, 09h
    int 21h

    mov bx, 0       ; BX will act as the "Accumulator" for the number    
read_num:
    mov ah, 01h     ; DOS function to read a single character    
    int 21h
    cmp al, 13      ;It check for entry key    
    je menu_step    ; If Enter is pressed, move to the menu
    
    sub al, 30h     ; Convert ASCII character (e.g. '5') to actual digit (5)    
    mov ah, 0
    mov cx, ax      ; Save the current digit into CX temporarily    
    
    mov ax, bx      ; Load our running total into AX    
    mov dx, 10      ; We multiply by 10 to shift digits to the left
    mul dx          ; AX = AX * 10
    
    ; Check if multiplication went over 16-bits (65535)
    jc too_big      

    add ax, cx      ; Add the new digit to the total
    
    ; Check if addition went over 16-bits (65535)
    jc too_big      

    mov bx, ax      ; Move the new total back into BX    
    jmp read_num    ; Repeat for the next digit

too_big:
    lea dx, msgMax
    mov ah, 09h
    int 21h
    jmp exit_prog   ; Terminate if capacity is reached

menu_step:
    mov num, bx         

    ;Step 2: Choos which conversion you want to do
    lea dx, msgMenu
    mov ah, 09h
    int 21h
    
    mov ah, 01h            ;Get the user's input/choices (1, 2, or 3)
    int 21h
    mov bl, al             ;Store the choice character in BL for logic checks
                           ;BL is my variable   
    
    
    
    
    ;Step 3: Checking for error logics 
    ;Check if your choice < 1 and if it does it will put an invalid choice 
    cmp bl, '1'
    jb  invalid_choice
    
    ;Check if your choice > 3 and if it does it will put an invalid choice
    cmp bl, '3'
    ja  invalid_choice

    
    ;Step 4: After choosing the conversion starts here
    ;        and shows the process of the conversion
    lea dx, msgRes      
    mov ah, 09h
    int 21h

    cmp bl, '1'     ;If you choose '1'it will directly jums to Binary 
    je set_bin
    cmp bl, '2'     ;If you choose '2'it will directly jums to Octal
    je set_oct
    cmp bl, '3'     ;If you choose '3'it will directly jums to Hexadecimal
    je set_hex

set_bin:
    mov bx, 2       ;set the divisor base to 2 
    jmp start_conversion
set_oct:
    mov bx, 8       ;set the divisor base to 8
    jmp start_conversion
set_hex:
    mov bx, 16      ;set the divisor base to 16

start_conversion:
    mov ax, num     ; Load the decimal number into AX
    mov cx, 0       ; CX will count how many digits it will generate    

calc_digits:
    mov dx, 0       ; Clear DX before division (essential for 16-bit div)
    div bx          ; AX / BX: Quotient goes to AX, Remainder goes to DX    
    push dx         ; Save the remainder into the Stack    
    inc cx          ; Increment the digit counter
    cmp ax, 0       ; Compares the value if the quotient is 0
    jne calc_digits ; If not, it will divide again

print_loop:
    pop dx          ; Pull the last-saved remainder from the Stack
    cmp dl, 9       ; Check if the digit is 0-9 or 10-15 (for Hex)
    jbe is_digit    ; If 9 or less, skip adjustment
    add dl, 7       ; If 10-15, add 7 to jump to 'A'-'F' in ASCII    
is_digit:
    add dl, 30h     ; Add 30h to turn the number into a printable ASCII char    
    mov ah, 02h     ; DOS function to print a single character    
    int 21h
    loop print_loop ; Repeat until CX is 0
    jmp exit_prog   ; Go to end of program

invalid_choice:
    lea dx, msgError ; Display error if the user put something else
    mov ah, 09h
    int 21h

exit_prog:
    mov ah, 4Ch      ; DOS function to terminate the program
    int 21h
