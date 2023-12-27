package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.CType;
import cz.mg.c.parser.entities.CTypename;
import cz.mg.c.writer.formatters.TypeFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

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

    private void testFormatTypename() {
        CType type = new CType(new CTypename("int"), false, new List<>(), new List<>());
        Assert.assertThatCollections(new List<>("int"), typeFormatter.format(type)).areEqual();
    }

    private void testFormatConstant() {
        CType type = new CType(new CTypename("int"), true, new List<>(), new List<>());
        Assert.assertThatCollections(new List<>("int const"), typeFormatter.format(type)).areEqual(); // TODO - fix
    }

    private void testFormatPointersWithoutConst() {
        throw new UnsupportedOperationException();
    }

    private void testFormatPointersWithConst() {
        throw new UnsupportedOperationException();
    }

    private void testFormatPointersWithMixedConst() {
        throw new UnsupportedOperationException();
    }

    private void testFormatStruct() {
        throw new UnsupportedOperationException();
    }

    private void testFormatUnion() {
        throw new UnsupportedOperationException();
    }

    private void testFormatEnum() {
        throw new UnsupportedOperationException();
    }

    private void testFormatFunctionPointer() {
        throw new UnsupportedOperationException();
    }
}
