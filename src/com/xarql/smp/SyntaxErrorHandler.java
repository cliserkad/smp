package com.xarql.smp;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import com.xarql.util.ExceptionPack;

public class SyntaxErrorHandler extends BaseErrorListener {

	public final List<SyntaxException> errors;

	public SyntaxErrorHandler() {
		errors = new ArrayList<>();
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public ExceptionPack pack() {
		return new ExceptionPack(errors.toArray(new Exception[0]));
	}

	@Override
	public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final String msg,
			final RecognitionException e) {
		errors.add(new SyntaxException(msg, line, charPositionInLine));
	}

}
