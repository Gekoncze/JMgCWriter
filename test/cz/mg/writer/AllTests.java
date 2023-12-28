package cz.mg.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.writer.formatters.*;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.c.writer.formatters
        EnumEntryFormatterTest.main(args);
        EnumFormatterTest.main(args);
        ExpressionFormatterTest.main(args);
        FieldsFormatterTest.main(args);
        StructFormatterTest.main(args);
        TokenFormatterTest.main(args);
        TypeFormatterTest.main(args);
        UnionFormatterTest.main(args);
        VariableFormatterTest.main(args);

        // cz.mg.c.writer
        IndentationTest.main(args);
    }
}
