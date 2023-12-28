package cz.mg.writer;

import cz.mg.annotations.classes.Service;
import cz.mg.annotations.classes.Test;
import cz.mg.c.writer.Indentation;
import cz.mg.collections.list.List;
import cz.mg.test.Assert;

public @Test class IndentationTest {
    public static void main(String[] args) {
        System.out.print("Running " + IndentationTest.class.getSimpleName() + " ... ");

        IndentationTest test = new IndentationTest();
        test.testAddEmpty();
        test.testAddSingle();
        test.testAddMultiple();

        System.out.println("OK");
    }

    private final @Service Indentation indentation = Indentation.getInstance();

    private void testAddEmpty() {
        List<String> lines = new List<>();
        List<String> actualLines = indentation.add(lines);
        Assert.assertSame(lines, actualLines);
        Assert.assertEquals(true, lines.isEmpty());
    }

    private void testAddSingle() {
        List<String> lines = new List<>("test line");
        List<String> actualLines = indentation.add(lines);
        Assert.assertSame(lines, actualLines);
        Assert.assertEquals(1, lines.count());
        Assert.assertEquals("    test line", lines.getFirst());
    }

    private void testAddMultiple() {
        List<String> lines = new List<>("test line one", "    test line two");
        List<String> actualLines = indentation.add(lines);
        Assert.assertSame(lines, actualLines);
        Assert.assertEquals(2, lines.count());
        Assert.assertEquals("    test line one", lines.getFirst());
        Assert.assertEquals("        test line two", lines.getLast());
    }
}
