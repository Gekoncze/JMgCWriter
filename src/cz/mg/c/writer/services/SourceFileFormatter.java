package cz.mg.c.writer.services;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.list.List;

public @Service class SourceFileFormatter {
    private static volatile @Service SourceFileFormatter instance;

    public static @Service SourceFileFormatter getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new SourceFileFormatter();
                    instance.fileFormatter = FileFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service FileFormatter fileFormatter;

    private SourceFileFormatter() {
    }

    public @Mandatory List<String> format(@Mandatory CSourceFile sourceFile) {
        throw new UnsupportedOperationException(); // TODO
    }
}