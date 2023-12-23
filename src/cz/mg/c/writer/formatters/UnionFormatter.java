package cz.mg.c.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CUnion;
import cz.mg.collections.list.List;

public @Service class UnionFormatter implements CEntityFormatter<CUnion> {
    private static volatile @Service UnionFormatter instance;

    public static @Service UnionFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new UnionFormatter();
                }
            }
        }
        return instance;
    }

    private UnionFormatter() {
    }

    @Override
    public @Mandatory List<String> format(@Mandatory CUnion union) {
        throw new UnsupportedOperationException("TODO"); // TODO
    }
}
