# Simple Data Format `.smp`
smp is a textual data format that is designed to be easily understood by both humans and computers.
The grammar is very similar to JSON, and can be parsed in a single pass. The data is self describing
and fully formed.

## Grammar
Pairs are an identifier separated from a value by `:` and ended with `;`  
Identifiers are any string of characters excluding `:`  
Values are either objects, lists or primitives.  
Objects are groups of pairs, started with `{` and ended with `}`  
Lists are 0 indexed groups of values (each value separated by a `,`) started with `[` and ended with `]`  
Primitives are either booleans, numbers, character literals or strings.  
Booleans are either `true` or `false`  
Numbers are either decimal, hexadecimal or binary numbers.  
Decimal numbers may be prefixed with `-` for negativity.  
Hexadecimal numbers must be prefixed with `0x`  
Binary numbers must be prefixed with `b`  
Character literals are any UTF-8 character or escape sequence surrounded by `'`  
Strings are any amount of UTF-8 characters or escape sequence surrounded by `"`  
An escape sequence is a character prefixed with `\` 
Escape sequence characters with meaning are as follows:  
`n` for newline  
`t` for tab   
`\` for a literal backslash (otherwise it would be impossible for `\` to appear in the output text)  
`"` for a quote (useful within a string)  
`'` for an apostrophe (useful within a character literal)

### Example
```
car: {
    year: 1998;
	model: "Honda Accord";
	engine: {
		cylinders: 6;
		name: "VTEC";
		mpg: 14.834;
	};
	type: 'c';
	driveModes: ['p','r','n','d','l',];
	working: true;
	bumper_sticker: "yes \t we \t can";
};
```
