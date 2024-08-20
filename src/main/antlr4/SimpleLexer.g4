lexer grammar SimpleLexer;

@header {
  package xyz.cliserkad.smp.antlr;
}

// skip over comments in lexer
COMMENT: '//' .*? '\n' -> channel(HIDDEN);
BLOCK_COMMENT: '/*' .*? '*/' -> channel(HIDDEN);

// skip over whitespace
WS : [ \t\r\n]+ -> skip;

CHAR_QUOTE: '\'';
QUOTE: '"';
STRING_LIT: QUOTE (~["\\] | '\\' .)* QUOTE;
// keywords
TRUE: 'true';
FALSE: 'false';

// syntax
BINARY: 'b';
HEXMADECIMAL: '0x';
BODY_OPEN: '{'; // opening bracket
BODY_CLOSE: '}'; // closing bracket
BRACE_OPEN: '[';
BRACE_CLOSE: ']';
DOT: '.';
SEPARATOR: ',';
PAIR_END: ';';
ASSIGN: ':';
NEGATIVE: '-';

DIGIT               : '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9';
fragment UPLETTER   : [A-Z];
fragment DNLETTER   : [a-z];
fragment LETTER     : UPLETTER | DNLETTER;
fragment ALPHANUM   : LETTER | DIGIT;
fragment UNDERSCORE : '_';
CHAR_LIT: '\'' . '\'';

// the specification says you can use any character that is not : but this is much more restrictive
// this might be changed later to be more permissive, but eliminating more characters makes parsing less ambiguous
KEY_FRAGMENT: ~[-+_:; \n\t\r{}[\],.\\/*'"];
