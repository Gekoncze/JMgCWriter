package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CArray;
import cz.mg.c.parser.entities.CFunction;
import cz.mg.c.parser.entities.CVariable;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class VariableFormatter implements CEntityFormatter<CVariable> {
    private static volatile @Service VariableFormatter instance;

    public static @Service VariableFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new VariableFormatter();
                    instance.typeFormatter = TypeFormatter.getInstance();
                    instance.expressionFormatter = ExpressionFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service TypeFormatter typeFormatter;
    private @Service ExpressionFormatter expressionFormatter;

    private VariableFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CVariable variable) {
        if (variable.getType().getTypename() instanceof CFunction) {
            return typeFormatter.format(variable.getType());
        } else {
            List<String> lines = typeFormatter.format(variable.getType());
            addName(lines, variable);
            addArrays(lines, variable);
            return lines;
        }
    }

    private void addName(@Mandatory List<String> lines, @Mandatory CVariable variable) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addName(lineItem.get(), variable));
    }

    private void addArrays(@Mandatory List<String> lines, @Mandatory CVariable variable) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addArrays(lineItem.get(), variable));
    }

    private @Mandatory String addName(@Mandatory String line, @Mandatory CVariable variable) {
        StringBuilder builder = new StringBuilder(line);

        if (variable.getName() != null) {
            builder.append(" ");
            builder.append(variable.getName());
        }

        return builder.toString().trim();
    }

    private @Mandatory String addArrays(@Mandatory String line, @Mandatory CVariable variable) {
        StringBuilder builder = new StringBuilder(line);

        for (CArray array : variable.getType().getArrays()) {
            builder.append("[");
            builder.append(expressionFormatter.format(array.getExpression()));
            builder.append("]");
        }

        return builder.toString().trim();
    }
}
