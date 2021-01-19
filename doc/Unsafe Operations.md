# Unsafe Operations

Within `TemplateParser.java` there are a plenitude of unsafe operations. This file
will serve to explain their purpose and quirks.

### Why unsafe?
These unsafe operations are necessary due to privacy and finality. The JVM checks
to ensure code can't access memory which it shouldn't be able to. This is generally great,
but not if you want to shove a bunch of values in to a type without any knowledge
of its purpose or design. The approach used is to allocate the memory for the template and
then push values in to the fields by getting their offset from the newly allocated object
and copying to the object + field addresses.

To put it simply, Java needs some extra coercion to behave like lower level languages.
I'm still writing the parser and encoders in Java, because I am comfortable with it
and find its type checking, security, and portability to be extremely helpful most of the time.

### Rows of `instanceof`
There are rows of `if` statements containing `instanceof` operations against primitive types.
This is required because the unsafe memory copying does not respect the byte layout of the types
within that memory. `instanceof` is an alias for the JVM opcode of `checkcast`.
By using this keyword, we force the JVM to examine the byte layout of the memory,
after which the data is copied without scrambling it. This is only required for primitive types
because all `Object`/reference types have the same byte layout for their references,
and the reference is the only part that is actually copied.

As of 0.8, copying primitive arrays is broken, likely due to the nature of them being both
a reference and primitive type. This is especially frustrating because arrays are much
more difficult to cast than regular objects even without unsafe operations.
