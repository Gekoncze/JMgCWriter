package cz.mg.writer.services.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.*;
import cz.mg.c.writer.services.formatters.TypeFormatter;
import cz.mg.collections.list.List;
import cz.mg.tokenizer.entities.tokens.NumberToken;
import cz.mg.writer.test.LineValidator;

public @Test class TypeFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + TypeFormatterTest.class.getSimpleName() + " ... ");

        TypeFormatterTest test = new TypeFormatterTest();
        test.testFormatTypename();
        test.testFormatConstant();
        test.testFormatPointersWithoutConst();
        test.testFormatPointersWithConst();
        test.testFormatPointersWithMixedConst();
        test.testFormatStruct();
        test.testFormatUnion();
        test.testFormatEnum();
        test.testFormatFunctionPointer();

        System.out.println("OK");
    }

    private final @Service TypeFormatter typeFormatter = TypeFormatter.getInstance();
    private final @Service LineValidator lineValidator = LineValidator.getInstance();

    private void testFormatTypename() {
        CType type = new CType(new CTypename("int"), false, new List<>(), new List<>());

        lineValidator.validate(
            typeFormatter.format(type),
            "int"
        );
    }

    private void testFormatConstant() {
        CType type = new CType(new CTypename("int"), true, new List<>(), new List<>());

        lineValidator.validate(
            typeFormatter.format(type),
            "const int"
        );
    }

    private void testFormatPointersWithoutConst() {
        CType type = new CType(
            new CTypename("int"),
            false,
            new List<>(new CPointer(false), new CPointer(false)),
            new List<>()
        );

        lineValidator.validate(
            typeFormatter.format(type),
            "int**"
        );
    }

    private void testFormatPointersWithConst() {
        CType type = new CType(
            new CTypename("int"),
            false,
            new List<>(new CPointer(true), new CPointer(true)),
            new List<>()
        );

        lineValidator.validate(
            typeFormatter.format(type),
            "int* const * const"
        );
    }

    private void testFormatPointersWithMixedConst() {
        CType type = new CType(
            new CTypename("int"),
            false,
            new List<>(new CPointer(true), new CPointer(false), new CPointer(false)),
            new List<>()
        );

        lineValidator.validate(
            typeFormatter.format(type),
            "int* const **"
        );
    }

    private void testFormatStruct() {
        CVariable variable = new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i");
        CStruct struct = new CStruct(null, new List<>(variable));
        CType type = new CType(struct, true, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            typeFormatter.format(type),
            "const struct {",
            "    int i;",
            "}*"
        );
    }

    private void testFormatUnion() {
        CVariable variable = new CVariable(new CType(new CTypename("int"), false, new List<>(), new List<>()), "i");
        CUnion union = new CUnion(null, new List<>(variable));
        CType type = new CType(union, true, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            typeFormatter.format(type),
            "const union {",
            "    int i;",
            "}*"
        );
    }

    private void testFormatEnum() {
        CEnumEntry entry = new CEnumEntry("ONE", new List<>(new NumberToken("1", 0)));
        CEnum enom = new CEnum(null, new List<>(entry));
        CType type = new CType(enom, true, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            typeFormatter.format(type),
            "const enum {",
            "    ONE = 1",
            "}*"
        );
    }

    private void testFormatFunctionPointer() {
        CFunction function = new CFunction(
            "foo",
            new CType(new CTypename("void"), false, new List<>(), new List<>()),
            new List<>(),
            null
        );

        CType type = new CType(function, false, new List<>(new CPointer()), new List<>());

        lineValidator.validate(
            typeFormatter.format(type),
            "void (* foo)()"
        );
    }
}