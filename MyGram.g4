grammar MyGram;

prog: statments;

statments: statment+;

statment
        : ID '=' expr NEWLINE       # assign
        | declare_statment          # declare
        | if_statment               # if
        | while_statment            # while
        | print_statment            # print
        | NEWLINE                   # blank
        ;

print_statment: PRINT '(' expr ')' NEWLINE;

expr
    : expr op=('*'|'/') expr    # MulDiv
    | expr op=('+'|'-') expr    # AddSub
    | '-' expr                  # Neg
    | ID                        # id
    | INT                       # int
    | FLOAT                     # float
    | '(' expr ')'              # parens
    ;

declare_statment
                : type ID NEWLINE               # DeclareSt
                ;

type
    : TINT                      # TypeInt
    | TFLOAT                    # TypeFloat
    ;

if_statment
            : IF '(' expr ')' '{' statments '}' NEWLINE                                        # IfSt
            | IF '(' expr ')' '{' statments '}' (NEWLINE)? ELSE '{' statments '}' NEWLINE    # IfElSt
            ;

while_statment 
                : WHILE '(' expr ')' '{' statments '}' NEWLINE # WhileSt
                ;


INT     :       '-'?[0-9]+ ;
FLOAT   :       '-'?[0-9]+'.'[0-9]+;
ID      :       [a-zA-Z]+[a-zA-Z0-9_]? ;
MUL     :       '*';
DIV     :       '/';
ADD     :       '+';
SUB     :       '-';

PRINT   :     'print';
IF      :        'if';
ELSE    :      'else';
WHILE   :     'while';

TINT    :      'int';
TFLOAT  :    'float';

NEWLINE :   '\r'? '\n' ;
WS      :   [ \t]+ -> skip;