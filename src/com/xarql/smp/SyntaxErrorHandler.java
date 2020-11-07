package com.xarql.smp;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class SyntaxErrorHandler extends BaseErrorListener {

    public final List<SyntaxException> errors;

    public SyntaxErrorHandler() {
        errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public ExceptionPack pack() {
        return new ExceptionPack((Exception[]) errors.toArray());
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errors.add(new SyntaxException(msg, line, charPositionInLine));
    }

}
