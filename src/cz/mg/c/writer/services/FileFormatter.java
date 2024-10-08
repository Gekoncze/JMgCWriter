package cz.mg.c.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CEntity;
import cz.mg.c.entities.CFile;
import cz.mg.c.entities.CFunction;
import cz.mg.c.writer.services.formatters.MainEntityFormatters;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class FileFormatter {
    private static volatile @Service FileFormatter instance;

    public static @Service FileFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FileFormatter();
                    instance.formatters = MainEntityFormatters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service MainEntityFormatters formatters;

    private FileFormatter() {
    }

    public @Mandatory List<String> format(@Mandatory CFile file) {
        List<String> lines = new List<>();
        for (CEntity entity : file.getEntities()) {
            lines.addCollectionLast(formatters.get(entity).format(entity));
            if (!(entity instanceof CFunction)) {
                addSemicolon(lines);
            }
        }
        return lines;
    }

    private void addSemicolon(@Mandatory List<String> lines) {
        ListItem<String> lineItem = lines.getLastItem();
        lineItem.set(addSemicolon(lineItem.get()));
    }

    private @Mandatory String addSemicolon(@Mandatory String line) {
        return line + ";";
    }
}