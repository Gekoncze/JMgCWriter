package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CVariable;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.list.ReadableList;

public @Service class ParametersFormatter {
    private static volatile @Service ParametersFormatter instance;

    public static @Service ParametersFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new ParametersFormatter();
                    instance.variableFormatter = VariableFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service VariableFormatter variableFormatter;

    private ParametersFormatter() {
    }

    public @Mandatory List<String> format(@Mandatory ReadableList<CVariable> parameters) {
        List<List<String>> parametersLines = formatSeparately(parameters);
        if (!parameters.isEmpty()) {
            if (fitsSingleLine(parametersLines)) {
                return formatHorizontally(parametersLines);
            } else {
                return formatVertically(parametersLines);
            }
        } else {
            return new List<>();
        }
    }

    private boolean fitsSingleLine(@Mandatory List<List<String>> parametersLines) {
        for (List<String> lines : parametersLines) {
            if (lines.count() > 1) {
                return false;
            }
        }
        return true;
    }

    private @Mandatory List<List<String>> formatSeparately(@Mandatory ReadableList<CVariable> parameters) {
        List<List<String>> parametersLines = new List<>();
        for (CVariable parameter : parameters) {
            parametersLines.addLast(variableFormatter.format(parameter));
        }
        return parametersLines;
    }

    private @Mandatory List<String> formatHorizontally(@Mandatory List<List<String>> parametersLines) {
        StringBuilder builder = new StringBuilder();
        for (List<String> parameterLines : parametersLines) {
            if (parameterLines != parametersLines.getLast()) {
                addHorizontalSeparator(parameterLines);
            }
            builder.append(parameterLines.getFirst());
        }
        return new List<>(builder.toString());
    }

    private @Mandatory List<String> formatVertically(@Mandatory List<List<String>> parametersLines) {
        List<String> lines = new List<>();
        for (List<String> parameterLines : parametersLines) {
            if (parameterLines != parametersLines.getLast()) {
                addVerticalSeparator(parameterLines);
            }
            lines.addCollectionLast(parameterLines);
        }
        return lines;
    }

    private void addHorizontalSeparator(@Mandatory List<String> lines) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addHorizontalSeparator(lineItem.get()));
    }

    private void addVerticalSeparator(@Mandatory List<String> lines) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addVerticalSeparator(lineItem.get()));
    }

    private @Mandatory String addHorizontalSeparator(@Mandatory String line) {
        return line + ", ";
    }

    private @Mandatory String addVerticalSeparator(@Mandatory String line) {
        return line + ",";
    }
}