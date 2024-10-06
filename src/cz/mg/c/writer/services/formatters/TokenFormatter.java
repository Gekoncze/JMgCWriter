package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.token.Token;
import cz.mg.token.tokens.*;
import cz.mg.token.tokens.comment.MultiLineCommentToken;
import cz.mg.token.tokens.comment.SingleLineCommentToken;
import cz.mg.token.tokens.quote.DoubleQuoteToken;
import cz.mg.token.tokens.quote.SingleQuoteToken;

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

        if (token instanceof SymbolToken) {
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
