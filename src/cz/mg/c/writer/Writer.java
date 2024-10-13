package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CFile;
import cz.mg.c.writer.services.FileFormatter;
import cz.mg.file.page.Page;
import cz.mg.file.page.PageWriter;

public @Service class Writer {
    private static volatile @Service Writer instance;

    public static @Service Writer getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Writer();
                    instance.writer = PageWriter.getInstance();
                    instance.fileFormatter = FileFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service PageWriter writer;
    private @Service FileFormatter fileFormatter;

    public void write(@Mandatory CFile file) {
        writer.write(
            new Page(
                file.getPath(),
                fileFormatter.format(file)
            )
        );
    }
}