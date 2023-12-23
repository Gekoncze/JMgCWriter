package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CTypedef;
import cz.mg.collections.list.List;

public @Service class TypedefFormatter implements CEntityFormatter<CTypedef> {
    private static volatile @Service TypedefFormatter instance;

    public static @Service TypedefFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new TypedefFormatter();
                }
            }
        }
        return instance;
    }

    private TypedefFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CTypedef typedef) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
