package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CStruct;
import cz.mg.collections.list.List;

public @Service class StructFormatter implements CEntityFormatter<CStruct> {
    private static volatile @Service StructFormatter instance;

    public static @Service StructFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new StructFormatter();
                }
            }
        }
        return instance;
    }

    private StructFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CStruct struct) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
