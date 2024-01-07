package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CUnion;
import cz.mg.c.writer.Indentation;
import cz.mg.collections.list.List;

public @Service class UnionFormatter implements EntityFormatter<CUnion> {
    private static volatile @Service UnionFormatter instance;

    public static @Service UnionFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new UnionFormatter();
                    instance.fieldsFormatter = FieldsFormatter.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service FieldsFormatter fieldsFormatter;
    private @Service Indentation indentation;

    private UnionFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CUnion union) {
        List<String> lines = new List<>();
        lines.addLast(formatHeader(union));
        if (union.getVariables() != null) {
            lines.addCollectionLast(
                indentation.add(
                    fieldsFormatter.format(union.getVariables())
                )
            );
            lines.addLast("}");
        }
        return lines;
    }

    private @Mandatory String formatHeader(@Mandatory CUnion union) {
        StringBuilder builder = new StringBuilder("union");
        if (union.getName() != null) {
            builder.append(" ");
            builder.append(union.getName());
        }
        if (union.getVariables() != null) {
            builder.append(" ");
            builder.append("{");
        }
        return builder.toString();
    }
}