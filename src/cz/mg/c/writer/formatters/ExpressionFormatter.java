package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.ReadableList;
import cz.mg.tokenizer.entities.Token;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.tokenizer.entities.tokens.WordToken;

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

    public @Mandatory String format(@Mandatory ReadableList<Token> expression) {
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

            builder.append(tokenFormatter.format(token));
            previousClass = currentClass;
        }

        return builder.toString();
    }
}
