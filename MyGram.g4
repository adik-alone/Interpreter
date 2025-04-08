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
    | '(' expr ')'              # parens
    | expr op=('>'|'<') expr    # MoreLess
    | expr op=('=='|'!=') expr  # EquN
    |  STR      # str
    | ID                        # id
    | INT                       # int
    | FLOAT                     # float
    ;

declare_statment
                : type ID NEWLINE               # DeclareSt
                ;

type
    : TINT                      # TypeInt
    | TFLOAT                    # TypeFloat
    | TSTR                      # TypeStr
    ;

if_statment
            : IF '(' expr ')' '{' statments '}' (NEWLINE)? ELSE '{' statments '}' NEWLINE    # IfElSt
            | IF '(' expr ')' '{' statments '}' NEWLINE                                        # IfSt
            ;

while_statment 
                : WHILE '(' expr ')' '{' statments '}' NEWLINE # WhileSt
                ;



MUL     :       '*';
DIV     :       '/';
ADD     :       '+';
SUB     :       '-';
MORES   :       '>';
LESS    :       '<';
EQU     :       '==';
NEQU    :       '!=';
NOT     :       '~';
AND     :       '&';
OR      :       '|';

PRINT   :       'print';
IF      :       'if';
ELSE    :       'else';
WHILE   :       'while';

TINT    :       'int';
TFLOAT  :       'float';
TSTR    :       'str';

ID      :   [a-zA-Z]+[a-zA-Z0-9_]? ;
FLOAT   :       '-'?[0-9]+'.'[0-9]+;
INT     :       '-'?[0-9]+ ;
STR     :       '"'.*?'"';

NEWLINE :   '\r'? '\n' ;
WS      :   [ \t]+ -> skip;