package cz.mg.c.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CHeaderFile;
import cz.mg.collections.list.List;

public @Service class HeaderFileFormatter {
    private static volatile @Service HeaderFileFormatter instance;

    public static @Service HeaderFileFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new HeaderFileFormatter();
                    instance.fileFormatter = FileFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service FileFormatter fileFormatter;

    private HeaderFileFormatter() {
    }

    public @Mandatory List<String> format(@Mandatory CHeaderFile headerFile) {
        throw new UnsupportedOperationException(); // TODO
    }
}