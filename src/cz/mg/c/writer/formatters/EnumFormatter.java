package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CEnum;
import cz.mg.c.parser.entities.CEnumEntry;
import cz.mg.c.writer.Indentation;
import cz.mg.collections.list.List;

public @Service class EnumFormatter implements EntityFormatter<CEnum> {
    private static volatile @Service EnumFormatter instance;

    public static @Service EnumFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new EnumFormatter();
                    instance.enumEntryFormatter = EnumEntryFormatter.getInstance();
                    instance.indentation = Indentation.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service EnumEntryFormatter enumEntryFormatter;
    private @Service Indentation indentation;

    private EnumFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CEnum enom) {
        List<String> lines = new List<>();
        lines.addLast(formatHeader(enom));
        if (enom.getEntries() != null) {
            lines.addCollectionLast(
                indentation.add(
                    formatEntries(enom.getEntries())
                )
            );
            lines.addLast("}");
        }
        return lines;
    }

    private @Mandatory List<String> formatEntries(@Mandatory List<CEnumEntry> entries) {
        List<String> lines = new List<>();
        for (CEnumEntry entry : entries) {
            StringBuilder builder = new StringBuilder(enumEntryFormatter.format(entry));
            if (entry != entries.getLast()) {
                builder.append(",");
            }
            lines.addLast(builder.toString());
        }
        return lines;
    }

    private @Mandatory String formatHeader(@Mandatory CEnum enom) {
        StringBuilder builder = new StringBuilder("enum");
        if (enom.getName() != null) {
            builder.append(" ");
            builder.append(enom.getName());
        }
        if (enom.getEntries() != null) {
            builder.append(" ");
            builder.append("{");
        }
        return builder.toString();
    }
}
