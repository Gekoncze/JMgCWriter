
package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.services.formatters.EnumFormatter;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.writer.test.LineValidator;

public @Test class EnumFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + EnumFormatterTest.class.getSimpleName() + " ... ");

        EnumFormatterTest test = new EnumFormatterTest();
        test.testFormatDeclaration();
        test.testFormatDefinition();
        test.testFormatAnonymous();
        test.testFormatEntries();

        System.out.println("OK");
    }

    private final @Service EnumFormatter enumFormatter = EnumFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatDeclaration() {
        CEnum enom = new CEnum("Foo", null);
        lineValidator.validate(
            enumFormatter.format(enom),
            "enum Foo"
        );
    }

    private void testFormatDefinition() {
        CEnum enom = new CEnum("Foo", new List<>());
        lineValidator.validate(
            enumFormatter.format(enom),
            "enum Foo {",
            "}"
        );
    }

    private void testFormatAnonymous() {
        CEnum enom = new CEnum(null, new List<>());
        lineValidator.validate(
            enumFormatter.format(enom),
            "enum {",
            "}"
        );
    }

    private void testFormatEntries() {
        CEnum struct = new CEnum("Day", new List<>(
            new CEnumEntry("MONDAY", new List<>(new NumberToken("1", 0))),
            new CEnumEntry("TUESDAY", null),
            new CEnumEntry("WEDNESDAY", null)
        ));
        lineValidator.validate(
            enumFormatter.format(struct),
            "enum Day {",
            "    MONDAY = 1,",
            "    TUESDAY,",
            "    WEDNESDAY",
            "}"
        );
    }
}