package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CVariable;
import cz.mg.collections.list.List;

public @Service class VariableFormatter implements CEntityFormatter<CVariable> {
    private static volatile @Service VariableFormatter instance;

    public static @Service VariableFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new VariableFormatter();
                }
            }
        }
        return instance;
    }

    private VariableFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CVariable variable) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
