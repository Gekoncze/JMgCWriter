package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Test;

public @Test class TypeFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + TypeFormatterTest.class.getSimpleName() + " ... ");

        TypeFormatterTest test = new TypeFormatterTest();
        test.testFormatTypename();
        test.testFormatStruct();
        test.testFormatUnion();
        test.testFormatEnum();
        test.testFormatFunctionPointer();

        System.out.println("OK");
    }

    private void testFormatTypename() {
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
