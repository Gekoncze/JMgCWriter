package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CArray;
import cz.mg.collections.list.List;

public @Service class ArrayFormatter {
    private static volatile @Service ArrayFormatter instance;

    public static @Service ArrayFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new ArrayFormatter();
                    instance.expressionFormatter = ExpressionFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionFormatter expressionFormatter;

    private ArrayFormatter() {
    }

    public @Mandatory String format(@Mandatory List<CArray> arrays) {
        StringBuilder builder = new StringBuilder();

        for (CArray array : arrays) {
            builder.append("[");
            builder.append(expressionFormatter.formatSingleLine(array.getExpression()));
            builder.append("]");
        }

        return builder.toString().trim();
    }
}