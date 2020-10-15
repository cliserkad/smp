parser grammar Simple;

@header {
  package com.xarql.smp.antlr;
}

options {
    tokenVocab = SimpleLexer;
}

bool: TRUE | FALSE;
integer: (BINARY | HEXMADECIMAL)? DIGIT+;
decimal: DIGIT* DOT DIGIT+;

value: object | list | bool | integer | decimal | STRING_LIT | CHAR_LIT;
list: BRACE_OPEN (value SEPARATOR)* BRACE_CLOSE;
pair: KEYNAME (ASSIGN value) PAIR_END;
object: BODY_OPEN pair* BODY_CLOSE;
