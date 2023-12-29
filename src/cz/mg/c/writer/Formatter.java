package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFile;
import cz.mg.c.parser.entities.CFunction;
import cz.mg.c.parser.entities.CMainEntity;
import cz.mg.c.writer.formatters.CMainEntityFormatters;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;

public @Service class Formatter {
    private static volatile @Service Formatter instance;

    public static @Service Formatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Formatter();
                    instance.formatters = CMainEntityFormatters.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service CMainEntityFormatters formatters;

    private Formatter() {
    }

    public @Mandatory List<String> format(@Mandatory CFile file) {
        List<String> lines = new List<>();
        for (CMainEntity entity : file.getEntities()) {
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
