package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CStruct;
import cz.mg.c.parser.entities.CVariable;
import cz.mg.c.writer.Indentation;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class StructFormatter implements CEntityFormatter<CStruct> {
    private static volatile @Service StructFormatter instance;

    public static @Service StructFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new StructFormatter();
                    instance.variableFormatter = VariableFormatter.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service VariableFormatter variableFormatter;
    private @Service Indentation indentation;

    private StructFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CStruct struct) {
        List<String> lines = new List<>();
        lines.addLast(createHeader(struct));
        if (struct.getVariables() != null) {
            lines.addCollectionLast(createBody(struct.getVariables()));
            lines.addLast("}");
        }
        return lines;
    }

    private @Mandatory String createHeader(@Mandatory CStruct struct) {
        StringBuilder builder = new StringBuilder("struct ");
        if (struct.getName() != null) {
            builder.append(struct.getName());
            builder.append(" ");
        }
        if (struct.getVariables() != null) {
            builder.append("{");
        }
        return builder.toString();
    }

    private @Mandatory List<String> createBody(@Mandatory List<CVariable> variables) {
        List<String> body = new List<>();
        for (CVariable variable : variables) {
            List<String> lines = variableFormatter.format(variable);
            addSeparator(lines);
            body.addCollectionLast(lines);
        }
        indentation.add(body);
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
