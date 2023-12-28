package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.parser.entities.CPointer;
import cz.mg.c.writer.formatters.PointerFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Test class PointerFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + PointerFormatterTest.class.getSimpleName() + " ... ");

        PointerFormatterTest test = new PointerFormatterTest();
        test.testFormatEmpty();
        test.testFormatSingle();
        test.testFormatMultiple();
        test.testFormatConst();
        test.testFormatMixedConst();

        System.out.println("OK");
    }

    private final @Service PointerFormatter pointerFormatter = PointerFormatter.getInstance();

    private void testFormatEmpty() {
        Assert.assertEquals(
            "",
            pointerFormatter.format(new List<>())
        );
    }

    private void testFormatSingle() {
        Assert.assertEquals(
            "*",
            pointerFormatter.format(new List<>(new CPointer(false)))
        );
    }

    private void testFormatMultiple() {
        Assert.assertEquals(
            "**",
            pointerFormatter.format(new List<>(new CPointer(false), new CPointer(false)))
        );
    }

    private void testFormatConst() {
        Assert.assertEquals(
            "* const",
            pointerFormatter.format(new List<>(new CPointer(true)))
        );
    }

    private void testFormatMixedConst() {
        Assert.assertEquals(
            "* const **",
            pointerFormatter.format(new List<>(new CPointer(true), new CPointer(false), new CPointer(false)))
        );
    }
}
