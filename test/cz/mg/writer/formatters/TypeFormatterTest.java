package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.CPointer;
import cz.mg.c.parser.entities.CType;
import cz.mg.c.parser.entities.CTypename;
import cz.mg.c.writer.formatters.TypeFormatter;
import cz.mg.collections.list.List;
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
        test.testFormatMultiLine();
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
        // TODO
    }

    private void testFormatUnion() {
        // TODO
    }

    private void testFormatEnum() {
        // TODO
    }

    private void testFormatMultiLine() {
        // TODO
    }

    private void testFormatFunctionPointer() {
        // TODO
    }
}
