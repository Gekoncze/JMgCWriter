package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CType;
import cz.mg.collections.list.List;

public @Service class FunctionPointerFormatter {
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

    public @Mandatory List<String> format(@Mandatory CType type) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
