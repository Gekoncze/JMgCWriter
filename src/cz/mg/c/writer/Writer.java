package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFile;

public @Service class Writer {
    private static volatile @Service Writer instance;

    public static @Service Writer getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Writer();
                }
            }
        }
        return instance;
    }

    public void write(@Mandatory CFile file) {
        throw new UnsupportedOperationException(); // TODO
    }
}
