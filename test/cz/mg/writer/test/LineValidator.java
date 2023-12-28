package cz.mg.writer.test;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Service class LineValidator
{
    private static volatile @Service LineValidator instance;

    public static @Service LineValidator getInstance() {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new LineValidator();
                }
            }
        }
        return instance;
    }

    private LineValidator() {
    }

    public void validate(@Mandatory List<String> actualLines, @Mandatory String... expectedLines) {
        Assert.assertThatCollections(new Array<>(expectedLines), actualLines)
                .withPrintFunction(line -> '"' + line + '"')
                .areEqual();
    }
}
