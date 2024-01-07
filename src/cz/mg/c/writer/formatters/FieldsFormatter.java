package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CVariable;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.collections.list.ReadableList;

public @Service class FieldsFormatter {
    private static volatile @Service FieldsFormatter instance;

    public static @Service FieldsFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FieldsFormatter();
                    instance.variableFormatter = VariableFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service VariableFormatter variableFormatter;

    private FieldsFormatter() {
    }

    public @Mandatory List<String> format(@Mandatory ReadableList<CVariable> variables) {
        List<String> body = new List<>();
        for (CVariable variable : variables) {
            List<String> lines = variableFormatter.format(variable);
            addSeparator(lines);
            body.addCollectionLast(lines);
        }
        return body;
    }

    private void addSeparator(@Mandatory List<String> lines) {
        ListItem<String> line = lines.getLastItem();
        line.set(addSeparator(line.get()));
    }

    private @Mandatory String addSeparator(@Mandatory String line) {
        return line + ";";
    }
}