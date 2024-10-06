package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.components.StringJoiner;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.token.Token;
import cz.mg.token.tokens.NumberToken;
import cz.mg.token.tokens.WhitespaceToken;
import cz.mg.token.tokens.WordToken;

public @Service class ExpressionFormatter {
    private static volatile @Service ExpressionFormatter instance;

    public static @Service ExpressionFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new ExpressionFormatter();
                    instance.tokenFormatter = TokenFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service TokenFormatter tokenFormatter;

    private ExpressionFormatter() {
    }

    public @Mandatory String formatSingleLine(@Mandatory ReadableList<Token> expression) {
        return new StringJoiner<>(formatMultiLine(expression)).withDelimiter(" ").join();
    }

    public @Mandatory List<String> formatMultiLine(@Mandatory ReadableList<Token> expression) {
        List<String> lines = new List<>();

        if (!expression.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            Class<?> previousClass = null;
            for (Token token : expression) {
                Class<?> currentClass = token.getClass();

                if (previousClass == WordToken.class && currentClass == WordToken.class) {
                    builder.append(" ");
                }

                if (previousClass == NumberToken.class && currentClass == NumberToken.class) {
                    builder.append(" ");
                }

                if (!isNewLine(token)) {
                    builder.append(tokenFormatter.format(token));
                } else {
                    lines.addLast(builder.toString());
                    builder = new StringBuilder();
                }

                previousClass = currentClass;
            }

            lines.addLast(builder.toString());
        }

        return lines;
    }

    private boolean isNewLine(@Mandatory Token token) {
        if (token instanceof WhitespaceToken whitespace) {
            return whitespace.getText().equals("\n");
        } else {
            return false;
        }
    }
}
