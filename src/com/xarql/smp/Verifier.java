package com.xarql.smp;

import com.xarql.util.ExceptionPack;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import com.xarql.smp.antlr.SimpleLexer;
import com.xarql.smp.antlr.SimpleParser;

public class Verifier {

	public static ParseTree makeParseTree(final String smp) throws ExceptionPack {
		final var syntaxErrorHandler = new SyntaxErrorHandler();

		final var lex = new SimpleLexer(CharStreams.fromString(smp));
		lex.removeErrorListeners();
		lex.addErrorListener(syntaxErrorHandler);

		final var tokens = new CommonTokenStream(lex, 0);

		final var parser = new SimpleParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(syntaxErrorHandler);

		final ParseTree tree = parser.root();

		if(syntaxErrorHandler.hasErrors()) {
			throw syntaxErrorHandler.pack();
		}

		return tree;
	}

	public static boolean verify(final String smp) {
		try {
			makeParseTree(smp);
			return true;
		} catch(final ExceptionPack pack) {
			return false;
		}
	}

	public static boolean verifyOrPrint(final String smp) {
		try {
			makeParseTree(smp);
			return true;
		} catch(final ExceptionPack pack) {
			System.err.println(pack.getMessage());
			return false;
		}
	}

}
