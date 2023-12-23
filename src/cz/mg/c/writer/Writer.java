package cz.mg.c.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.c.parser.entities.CFile;
import cz.mg.collections.list.List;
import cz.mg.collections.services.StringJoiner;
import cz.mg.file.File;
import cz.mg.file.FileWriter;

public @Service class Writer {
    private static volatile @Service Writer instance;

    public static @Service Writer getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Writer();
                    instance.writer = FileWriter.getInstance();
                    instance.formatter = Formatter.getInstance();
                    instance.joiner = StringJoiner.getInstance();
                }
            }
        }
        return instance;
    }

    private @Service FileWriter writer;
    private @Service Formatter formatter;
    private @Service StringJoiner joiner;

    public void write(@Mandatory CFile cFile) {
        List<String> lines = formatter.format(cFile);
        String content = joiner.join(lines, "\b");
        File file = new File(cFile.getPath(), content);
        writer.write(file);
    }
}
