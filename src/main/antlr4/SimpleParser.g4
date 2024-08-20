parser grammar SimpleParser;

@header {
  package xyz.cliserkad.smp.antlr;
}

options {
    tokenVocab = SimpleLexer;
}

bool: TRUE | FALSE;
integer: (NEGATIVE | BINARY | HEXMADECIMAL)? DIGIT+;
decimal: NEGATIVE? DIGIT+ DOT DIGIT+;

value: object | list | bool | integer | decimal | STRING_LIT | CHAR_LIT;
list: BRACE_OPEN (value SEPARATOR)* BRACE_CLOSE;
key: KEY_FRAGMENT+?;
pair: key ASSIGN value PAIR_END;
object: BODY_OPEN pair* BODY_CLOSE;

root: object*;
