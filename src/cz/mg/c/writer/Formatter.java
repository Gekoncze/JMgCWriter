package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFile;
import cz.mg.c.parser.entities.CMainEntity;
import cz.mg.c.writer.formatters.CEntityFormatters;
import cz.mg.collections.list.List;

public @Service class Formatter {
    private static volatile @Service Formatter instance;

    public static @Service Formatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Formatter();
                    instance.formatters = CEntityFormatters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service CEntityFormatters formatters;

    private Formatter() {
    }

    public @Mandatory List<String> format(@Mandatory CFile file) {
        List<String> lines = new List<>();
        for (CMainEntity entity : file.getEntities()) {
            lines.addCollectionLast(formatters.format(entity));
        }
        return lines;
    }
}
