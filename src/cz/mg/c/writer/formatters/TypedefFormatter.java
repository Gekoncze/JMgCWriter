package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.c.parser.entities.CFunction;
import cz.mg.c.parser.entities.CTypedef;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

import java.util.Objects;

public @Service class TypedefFormatter implements CEntityFormatter<CTypedef> {
    private static volatile @Service TypedefFormatter instance;

    public static @Service TypedefFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TypedefFormatter();
                    instance.typeFormatter = TypeFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service TypeFormatter typeFormatter;

    private TypedefFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CTypedef typedef) {
        validateArraysEmpty(typedef);
        List<String> lines = typeFormatter.format(typedef.getType());
        addTypedef(lines);
        if (!(typedef.getType().getTypename() instanceof CFunction)) {
            validateName(typedef.getName());
            addName(lines, typedef.getName());
        } else {
            validateFunctionPointerName(typedef);
        }
        return lines;
    }

    private void addTypedef(@Mandatory List<String> lines) {
        ListItem<String> lineItem = lines.getFirstItem();
        lineItem.set(addTypedef(lineItem.get()));
    }

    private @Mandatory String addTypedef(@Mandatory String line) {
        return "typedef" + " " + line;
    }

    private void addName(@Mandatory List<String> lines, @Mandatory String name) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addName(lineItem.get(), name));
    }

    private @Mandatory String addName(@Mandatory String line, @Mandatory String name) {
        return line + " " + name;
    }

    private void validateArraysEmpty(@Mandatory CTypedef typedef)
    {
        if (!typedef.getType().getArrays().isEmpty()) {
            throw new IllegalArgumentException("Array typedefs are not supported.");
        }
    }

    private void validateName(@Optional String name) {
        if (name == null) {
            throw new IllegalArgumentException("Missing typedef name.");
        }
    }

    private void validateFunctionPointerName(@Mandatory CTypedef typedef) {
        if (!Objects.equals(typedef.getName(), typedef.getType().getTypename().getName())) {
            throw new IllegalArgumentException("Typedef and function pointer name does not match.");
        }
    }
}
