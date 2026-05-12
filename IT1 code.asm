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
    
    num      dw ?

.code
main proc
    mov ax, @data
    mov ds, ax

    lea dx, msgInput
    mov ah, 09h
    int 21h

    mov bx, 0           
read_num:
    mov ah, 01h         
    int 21h
    cmp al, 13         
    je menu_step
    
    sub al, 30h         
    mov ah, 0
    mov cx, ax          
    
    mov ax, bx          
    mov dx, 10
    mul dx
    add ax, cx          
    mov bx, ax          
    jmp read_num

menu_step:
    mov num, bx         


    lea dx, msgMenu
    mov ah, 09h
    int 21h
    
    mov ah, 01h         
    int 21h
    mov bl, al          
    cmp bl, '1'
    jb  invalid_choice
    
    
    cmp bl, '3'
    ja  invalid_choice

    
    lea dx, msgRes      
    mov ah, 09h
    int 21h

    cmp bl, '1'
    je set_bin
    cmp bl, '2'
    je set_oct
    cmp bl, '3'
    je set_hex

set_bin:
    mov bx, 2
    jmp start_conversion
set_oct:
    mov bx, 8
    jmp start_conversion
set_hex:
    mov bx, 16

start_conversion:
    mov ax, num
    mov cx, 0           

calc_digits:
    mov dx, 0
    div bx              
    push dx             
    inc cx
    cmp ax, 0
    jne calc_digits

print_loop:
    pop dx
    cmp dl, 9
    jbe is_digit
    add dl, 7           
is_digit:
    add dl, 30h         
    mov ah, 02h         
    int 21h
    loop print_loop
    jmp exit_prog

invalid_choice:
    lea dx, msgError
    mov ah, 09h
    int 21h

exit_prog:
    mov ah, 4Ch
    int 21h

main endp
end main