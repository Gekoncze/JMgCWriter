
package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.*;
import cz.mg.c.writer.formatters.UnionFormatter;
import cz.mg.collections.list.List;
import cz.mg.writer.test.LineValidator;

public @Test class UnionFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + UnionFormatterTest.class.getSimpleName() + " ... ");

        UnionFormatterTest test = new UnionFormatterTest();
        test.testFormatDeclaration();
        test.testFormatDefinition();
        test.testFormatAnonymous();
        test.testFormatVariables();
        test.testFormatNestedTypes();

        System.out.println("OK");
    }

    private final @Service UnionFormatter unionFormatter = UnionFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatDeclaration() {
        CUnion union = new CUnion("Foo", null);
        lineValidator.validate(
            unionFormatter.format(union),
            "union Foo"
        );
    }

    private void testFormatDefinition() {
        CUnion union = new CUnion("Foo", new List<>());
        lineValidator.validate(
            unionFormatter.format(union),
            "union Foo {",
            "}"
        );
    }

    private void testFormatAnonymous() {
        CUnion union = new CUnion(null, new List<>());
        lineValidator.validate(
            unionFormatter.format(union),
            "union {",
            "}"
        );
    }

    private void testFormatVariables() {
        CUnion union = new CUnion("Foo", new List<>(
            new CVariable(new CType(new CTypename("int"), true, new List<>(), new List<>()), "a"),
            new CVariable(new CType(new CTypename("float"), false, new List<>(new CPointer()), new List<>()), "b")
        ));
        lineValidator.validate(
            unionFormatter.format(union),
            "union Foo {",
            "    const int a;",
            "    float* b;",
            "}"
        );
    }

    private void testFormatNestedTypes() {
        CUnion innerUnion = new CUnion("Bar", new List<>(
            new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i")
        ));
        CUnion union = new CUnion("Foo", new List<>(
            new CVariable(new CType(innerUnion, true, new List<>(new CPointer()), new List<>()), "inner")
        ));
        lineValidator.validate(
            unionFormatter.format(union),
            "union Foo {",
            "    const union Bar {",
            "        int i;",
            "    }* inner;",
            "}"
        );
    }
}
