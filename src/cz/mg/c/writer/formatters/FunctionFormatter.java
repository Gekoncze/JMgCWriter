package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CFunction;
import cz.mg.c.writer.Indentation;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class FunctionFormatter implements EntityFormatter<CFunction> {
    private static volatile @Service FunctionFormatter instance;

    public static @Service FunctionFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FunctionFormatter();
                    instance.indentation = Indentation.getInstance();
                    instance.typeFormatter = TypeFormatter.getInstance();
                    instance.parametersFormatter = ParametersFormatter.getInstance();
                    instance.expressionFormatter = ExpressionFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service Indentation indentation;
    private @Service TypeFormatter typeFormatter;
    private @Service ParametersFormatter parametersFormatter;
    private @Service ExpressionFormatter expressionFormatter;

    private FunctionFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CFunction function) {
        validate(function);

        List<String> lines = typeFormatter.format(function.getOutput());
        List<String> parameterLines = parametersFormatter.format(function.getInput());

        addString(lines, " " + function.getName() + "(");

        if (parameterLines.count() > 1) {
            lines.addCollectionLast(indentation.add(parameterLines));
            lines.addLast("");
        } else if (parameterLines.count() == 1) {
            addString(lines, parameterLines.getFirst());
        }

        addString(lines, ")");

        if (function.getImplementation() != null) {
            addString(lines, " {");
            lines.addCollectionLast(
                indentation.add(
                    expressionFormatter.formatMultiLine(function.getImplementation())
                )
            );
            lines.addLast("}");
        }

        return lines;
    }

    private void addString(@Mandatory List<String> lines, @Mandatory String s) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addString(lineItem.get(), s));
    }

    private @Mandatory String addString(@Mandatory String line, @Mandatory String s) {
        return line + s;
    }

    private void validate(@Mandatory CFunction function) {
        validateName(function);
        validateOutput(function);
    }

    private void validateName(@Mandatory CFunction function) {
        if (function.getName() == null) {
            throw new IllegalArgumentException("Missing function name.");
        }
    }

    private void validateOutput(@Mandatory CFunction function) {
        if (function.getOutput().getTypename() instanceof CFunction) {
            throw new IllegalArgumentException("Function cannot return function pointer.");
        }
    }
}