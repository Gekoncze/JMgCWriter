package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFunction;
import cz.mg.collections.list.List;

public @Service class FunctionFormatter implements CEntityFormatter<CFunction> {
    private static volatile @Service FunctionFormatter instance;

    public static @Service FunctionFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FunctionFormatter();
                }
            }
        }
        return instance;
    }

    private FunctionFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CFunction function) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
