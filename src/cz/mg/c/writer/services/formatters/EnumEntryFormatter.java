package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CEnumEntry;

public @Service class EnumEntryFormatter {
    private static volatile @Service EnumEntryFormatter instance;

    public static @Service EnumEntryFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new EnumEntryFormatter();
                    instance.expressionFormatter = ExpressionFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service ExpressionFormatter expressionFormatter;

    private EnumEntryFormatter() {
    }

    public @Mandatory String format(@Mandatory CEnumEntry entry) {
        StringBuilder builder = new StringBuilder(entry.getName());
        if (entry.getExpression() != null) {
            builder.append(" = ");
            builder.append(expressionFormatter.formatSingleLine(entry.getExpression()));
        }
        return builder.toString();
    }
}