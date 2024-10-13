package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CFunction;
import cz.mg.c.entities.types.CType;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class FunctionPointerFormatter implements EntityFormatter<CType> {
    private static volatile @Service FunctionPointerFormatter instance;

    public static @Service FunctionPointerFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FunctionPointerFormatter();
                    instance.typeFormatter = TypeFormatter.getInstance();
                    instance.parametersFormatter = ParametersFormatter.getInstance();
                    instance.pointerFormatter = PointerFormatter.getInstance();
                    instance.arrayFormatter = ArrayFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service TypeFormatter typeFormatter;
    private @Service ParametersFormatter parametersFormatter;
    private @Service PointerFormatter pointerFormatter;
    private @Service ArrayFormatter arrayFormatter;

    private FunctionPointerFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CType type) {
        validateType(type);
        CFunction function = (CFunction) type.getTypename();
        validateFunction(function);

        List<String> lines = typeFormatter.format(function.getOutput());
        List<String> parameterLines = parametersFormatter.format(function.getInput());
        String pointers = pointerFormatter.format(type.getPointers());
        String arrays = arrayFormatter.format(type.getArrays());

        addString(lines, " (" + pointers + " " + function.getName() + arrays + ")(");

        if (parameterLines.count() > 1) {
            lines.addCollectionLast(indentation.add(parameterLines));
            lines.addLast("");
        } else if (parameterLines.count() == 1) {
            addString(lines, parameterLines.getFirst());
        }

        addString(lines, ")");

        return lines;
    }

    private void addString(@Mandatory List<String> lines, @Mandatory String s) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addString(lineItem.get(), s));
    }

    private @Mandatory String addString(@Mandatory String line, @Mandatory String s) {
        return line + s;
    }

    private void validateType(@Mandatory CType type) {
        validatePointer(type);
        validateClass(type);
        validateConst(type);
    }

    private void validatePointer(@Mandatory CType type) {
        if (type.getPointers().isEmpty()) {
            throw new IllegalArgumentException("Illegal pointer count.");
        }
    }

    private void validateClass(@Mandatory CType type) {
        if (!(type.getTypename() instanceof CFunction)) {
            throw new IllegalArgumentException(
                "Unexpected function pointer type " + type.getTypename().getClass() + "."
            );
        }
    }

    private void validateConst(@Mandatory CType type) {
        if (type.isConstant()) {
            throw new IllegalArgumentException("Function pointer cannot be const.");
        }
    }

    private void validateFunction(@Mandatory CFunction function) {
        validateImplementation(function);
        validateName(function);
        validateOutput(function);
    }

    private void validateImplementation(@Mandatory CFunction function) {
        if (function.getImplementation() != null) {
            throw new IllegalArgumentException("Function pointer cannot have implementation.");
        }
    }

    private void validateName(@Mandatory CFunction function) {
        if (function.getName() == null) {
            throw new IllegalArgumentException("Missing function pointer name.");
        }
    }

    private void validateOutput(@Mandatory CFunction function) {
        if (function.getOutput().getTypename() instanceof CFunction) {
            throw new IllegalArgumentException("Function pointer cannot return function pointer.");
        }
    }
}