package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.*;

public @Service class TokenFormatter {
    private static volatile @Service TokenFormatter instance;

    public static @Service TokenFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TokenFormatter();
                }
            }
        }
        return instance;
    }

    private TokenFormatter() {
    }

    public @Mandatory String format(@Mandatory Token token) {
        if (token instanceof WordToken) {
            return token.getText();
        }

        if (token instanceof NumberToken) {
            return token.getText();
        }

        if (token instanceof BracketToken) {
            return token.getText();
        }

        if (token instanceof OperatorToken) {
            return token.getText();
        }

        if (token instanceof SeparatorToken) {
            return token.getText();
        }

        if (token instanceof SingleQuoteToken) {
            return "'" + token.getText() + "'";
        }

        if (token instanceof DoubleQuoteToken) {
            return '"' + token.getText() + '"';
        }

        if (token instanceof SingleLineCommentToken) {
            if (token.getText().contains("\n")) {
                throw new IllegalArgumentException("Single line comment token cannot contain newline.");
            }
            return "//" + token.getText();
        }

        if (token instanceof MultiLineCommentToken) {
            return "/*" + token.getText() + "*/";
        }

        if (token instanceof WhitespaceToken) {
            return token.getText();
        }

        throw new UnsupportedOperationException(
                "Unsupported token of type " + token.getClass().getSimpleName() + "."
        );
    }
}
