package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CEnum;
import cz.mg.collections.list.List;

public @Service class EnumFormatter implements CEntityFormatter<CEnum> {
    private static volatile @Service EnumFormatter instance;

    public static @Service EnumFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new EnumFormatter();
                }
            }
        }
        return instance;
    }

    private EnumFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CEnum enom) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
