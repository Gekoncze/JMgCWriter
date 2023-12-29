package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFunction;
import cz.mg.c.parser.entities.CVariable;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

import java.util.Objects;

public @Service class VariableFormatter implements EntityFormatter<CVariable> {
    private static volatile @Service VariableFormatter instance;

    public static @Service VariableFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new VariableFormatter();
                    instance.typeFormatter = TypeFormatter.getInstance();
                    instance.arrayFormatter = ArrayFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service TypeFormatter typeFormatter;
    private @Service ArrayFormatter arrayFormatter;

    private VariableFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CVariable variable) {
        if (variable.getType().getTypename() instanceof CFunction) {
            validateFunctionPointerName(variable);
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
        return line + arrayFormatter.format(variable.getType().getArrays());
    }

    private void validateFunctionPointerName(@Mandatory CVariable variable) {
        if (!Objects.equals(variable.getName(), variable.getType().getTypename().getName())) {
            throw new IllegalArgumentException("Variable name must match function pointer name.");
        }
    }
}
