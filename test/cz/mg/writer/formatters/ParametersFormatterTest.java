
package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.*;
import cz.mg.c.writer.formatters.ParametersFormatter;
import cz.mg.collections.list.List;
import cz.mg.writer.test.LineValidator;

public @Test class ParametersFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + ParametersFormatterTest.class.getSimpleName() + " ... ");

        ParametersFormatterTest test = new ParametersFormatterTest();
        test.testFormatEmpty();
        test.testFormatSingle();
        test.testFormatMultiLine();
        test.testFormatHorizontal();
        test.testFormatVertical();
        test.testFormatMultipleSimpleInnerTypes();

        System.out.println("OK");
    }

    private final @Service ParametersFormatter parametersFormatter = ParametersFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatEmpty() {
        List<CVariable> fields = new List<>();
        lineValidator.validate(
            parametersFormatter.format(fields)
        );
    }

    private void testFormatSingle() {
        List<CVariable> fields = new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "a")
        );
        lineValidator.validate(
            parametersFormatter.format(fields),
            "int a"
        );
    }

    private void testFormatMultiLine() {
        CStruct innerStruct = new CStruct(null, new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i")
        ));
        List<CVariable> fields = new List<>(
            new CVariable(new CType(innerStruct, false, new List<>(), new List<>()), "inner")
        );
        lineValidator.validate(
            parametersFormatter.format(fields),
            "struct {",
            "    int i;",
            "} inner"
        );
    }

    private void testFormatHorizontal() {
        List<CVariable> fields = new List<>(
            new CVariable(new CType(new CTypename("int"), true, new List<>(), new List<>()), "a"),
            new CVariable(new CType(new CTypename("float"), false, new List<>(new CPointer()), new List<>()), "b")
        );
        lineValidator.validate(
            parametersFormatter.format(fields),
            "const int a, float* b"
        );
    }

    private void testFormatVertical() {
        CStruct innerStruct = new CStruct(null, new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i")
        ));
        List<CVariable> fields = new List<>(
            new CVariable(new CType(innerStruct, true, new List<>(new CPointer()), new List<>()), "inner"),
            new CVariable(new CType(new CTypename("float"), false, new List<>(), new List<>()), "f")
        );
        lineValidator.validate(
            parametersFormatter.format(fields),
            "const struct {",
            "    int i;",
            "}* inner,",
            "float f"
        );
    }

    private void testFormatMultipleSimpleInnerTypes() {
        CStruct firstStruct = new CStruct("Foo", null);
        CStruct secondStruct = new CStruct("Bar", null);
        List<CVariable> fields = new List<>(
            new CVariable(new CType(firstStruct, false, new List<>(), new List<>()), "foo"),
            new CVariable(new CType(secondStruct, false, new List<>(), new List<>()), "bar")
        );
        lineValidator.validate(
            parametersFormatter.format(fields),
            "struct Foo foo, struct Bar bar"
        );
    }
}
