package cz.mg.writer.formatters;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.entities.CEnumEntry;
import cz.mg.c.writer.formatters.EnumEntryFormatter;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;
import cz.mg.tokenizer.entities.tokens.NumberToken;

public @Test class EnumEntryFormatterTest {
    public static void main(String[] args) {
        System.out.print("Running " + EnumEntryFormatterTest.class.getSimpleName() + " ... ");

        EnumEntryFormatterTest test = new EnumEntryFormatterTest();
        test.testFormatSimple();
        test.testFormatExpression();

        System.out.println("OK");
    }

    private final @Service EnumEntryFormatter enumEntryFormatter = EnumEntryFormatter.getInstance();

    private void testFormatSimple() {
        CEnumEntry entry = new CEnumEntry("MONDAY", null);
        Assert.assertEquals("MONDAY", enumEntryFormatter.format(entry));
    }

    private void testFormatExpression() {
        CEnumEntry entry = new CEnumEntry("MONDAY", new List<>(new NumberToken("1", 0)));
        Assert.assertEquals("MONDAY = 1", enumEntryFormatter.format(entry));
    }
}