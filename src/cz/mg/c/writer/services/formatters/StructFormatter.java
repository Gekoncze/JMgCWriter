package cz.mg.c.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CStruct;
import cz.mg.c.writer.services.Indentation;
import cz.mg.collections.list.List;

public @Service class StructFormatter implements EntityFormatter<CStruct> {
    private static volatile @Service StructFormatter instance;

    public static @Service StructFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new StructFormatter();
                    instance.fieldsFormatter = FieldsFormatter.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service FieldsFormatter fieldsFormatter;
    private @Service Indentation indentation;

    private StructFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CStruct struct) {
        List<String> lines = new List<>();
        lines.addLast(formatHeader(struct));
        if (struct.getVariables() != null) {
            lines.addCollectionLast(
                indentation.add(
                    fieldsFormatter.format(struct.getVariables())
                )
            );
            lines.addLast("}");
        }
        return lines;
    }

    private @Mandatory String formatHeader(@Mandatory CStruct struct) {
        StringBuilder builder = new StringBuilder("struct");
        if (struct.getName() != null) {
            builder.append(" ");
            builder.append(struct.getName());
        }
        if (struct.getVariables() != null) {
            builder.append(" ");
            builder.append("{");
        }
        return builder.toString();
    }
}