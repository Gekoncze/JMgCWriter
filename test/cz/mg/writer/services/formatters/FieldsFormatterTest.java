
package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.services.formatters.FieldsFormatter;
import cz.mg.collections.list.List;
import cz.mg.writer.test.LineValidator;

public @Test class FieldsFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + FieldsFormatterTest.class.getSimpleName() + " ... ");

        FieldsFormatterTest test = new FieldsFormatterTest();
        test.testFormatEmpty();
        test.testFormatSingle();
        test.testFormatMultiple();
        test.testFormatNestedTypes();

        System.out.println("OK");
    }

    private final @Service FieldsFormatter fieldsFormatter = FieldsFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatEmpty() {
        List<CVariable> fields = new List<>();
        lineValidator.validate(
            fieldsFormatter.format(fields)
        );
    }

    private void testFormatSingle() {
        List<CVariable> fields = new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "a")
        );
        lineValidator.validate(
            fieldsFormatter.format(fields),
            "int a;"
        );
    }

    private void testFormatMultiple() {
        List<CVariable> fields = new List<>(
            new CVariable(new CType(new CTypename("int"), true, new List<>(), new List<>()), "a"),
            new CVariable(new CType(new CTypename("float"), false, new List<>(new CPointer()), new List<>()), "b")
        );
        lineValidator.validate(
            fieldsFormatter.format(fields),
            "const int a;",
            "float* b;"
        );
    }

    private void testFormatNestedTypes() {
        CStruct innerStruct = new CStruct("Bar", new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i")
        ));
        List<CVariable> fields = new List<>(
            new CVariable(new CType(innerStruct, true, new List<>(new CPointer()), new List<>()), "inner")
        );
        lineValidator.validate(
            fieldsFormatter.format(fields),
            "const struct Bar {",
            "    int i;",
            "}* inner;"
        );
    }
}