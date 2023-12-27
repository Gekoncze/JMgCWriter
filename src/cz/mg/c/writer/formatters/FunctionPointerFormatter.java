package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFunction;
import cz.mg.collections.list.List;

public @Service class FunctionPointerFormatter implements CEntityFormatter<CFunction> {
    private static volatile @Service FunctionPointerFormatter instance;

    public static @Service FunctionPointerFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new FunctionPointerFormatter();
                }
            }
        }
        return instance;
    }

    private FunctionPointerFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CFunction function) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
