package cz.mg.writer;

import cz.mg.annotations.classes.Test;
import cz.mg.writer.services.IndentationTest;
import cz.mg.writer.services.formatters.*;

public @Test class AllTests {
    public static void main(String[] args) {
        // cz.mg.c.writer.services.formatters
        ArrayFormatterTest.main(args);
        EnumEntryFormatterTest.main(args);
        EnumFormatterTest.main(args);
        ExpressionFormatterTest.main(args);
        FieldsFormatterTest.main(args);
        FunctionFormatterTest.main(args);
        FunctionPointerFormatterTest.main(args);
        MainEntityFormattersTest.main(args);
        ParametersFormatterTest.main(args);
        PointerFormatterTest.main(args);
        StructFormatterTest.main(args);
        TokenFormatterTest.main(args);
        TypedefFormatterTest.main(args);
        TypeFormatterTest.main(args);
        UnionFormatterTest.main(args);
        VariableFormatterTest.main(args);

        // cz.mg.c.writer.services
        IndentationTest.main(args);
    }
}
