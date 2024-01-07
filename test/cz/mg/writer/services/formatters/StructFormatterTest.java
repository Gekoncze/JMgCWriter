
package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.services.formatters.StructFormatter;
import cz.mg.collections.list.List;
import cz.mg.writer.test.LineValidator;

public @Test class StructFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + StructFormatterTest.class.getSimpleName() + " ... ");

        StructFormatterTest test = new StructFormatterTest();
        test.testFormatDeclaration();
        test.testFormatDefinition();
        test.testFormatAnonymous();
        test.testFormatVariables();
        test.testFormatNestedTypes();

        System.out.println("OK");
    }

    private final @Service StructFormatter structFormatter = StructFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatDeclaration() {
        CStruct struct = new CStruct("Foo", null);
        lineValidator.validate(
            structFormatter.format(struct),
            "struct Foo"
        );
    }

    private void testFormatDefinition() {
        CStruct struct = new CStruct("Foo", new List<>());
        lineValidator.validate(
            structFormatter.format(struct),
            "struct Foo {",
            "}"
        );
    }

    private void testFormatAnonymous() {
        CStruct struct = new CStruct(null, new List<>());
        lineValidator.validate(
            structFormatter.format(struct),
            "struct {",
            "}"
        );
    }

    private void testFormatVariables() {
        CStruct struct = new CStruct("Foo", new List<>(
            new CVariable(new CType(new CTypename("int"), true, new List<>(), new List<>()), "a"),
            new CVariable(new CType(new CTypename("float"), false, new List<>(new CPointer()), new List<>()), "b")
        ));
        lineValidator.validate(
            structFormatter.format(struct),
            "struct Foo {",
            "    const int a;",
            "    float* b;",
            "}"
        );
    }

    private void testFormatNestedTypes() {
        CStruct innerStruct = new CStruct("Bar", new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i")
        ));
        CStruct struct = new CStruct("Foo", new List<>(
            new CVariable(new CType(innerStruct, true, new List<>(new CPointer()), new List<>()), "inner")
        ));
        lineValidator.validate(
            structFormatter.format(struct),
            "struct Foo {",
            "    const struct Bar {",
            "        int i;",
            "    }* inner;",
            "}"
        );
    }
}