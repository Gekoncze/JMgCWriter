package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.entities.CHeaderFile;
import cz.mg.c.entities.CSourceFile;
import cz.mg.c.writer.services.HeaderFileFormatter;
import cz.mg.c.writer.services.SourceFileFormatter;
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
                    instance.headerFileFormatter = HeaderFileFormatter.getInstance();
                    instance.sourceFileFormatter = SourceFileFormatter.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service PageWriter writer;
    private @Service HeaderFileFormatter headerFileFormatter;
    private @Service SourceFileFormatter sourceFileFormatter;

    public void write(@Mandatory CHeaderFile headerFile) {
        writer.write(
            new Page(
                headerFile.getPath(),
                headerFileFormatter.format(headerFile)
            )
        );
    }

    public void write(@Mandatory CSourceFile sourceFile) {
        writer.write(
            new Page(
                sourceFile.getPath(),
                sourceFileFormatter.format(sourceFile)
            )
        );
    }
}