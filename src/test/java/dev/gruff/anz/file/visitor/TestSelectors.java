package dev.gruff.anz.file.visitor;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSelectors {

    @Test
    public void testSimpleSelector() {

        URL u=getClass().getResource("/testdir");
        VisitorBuilder vb=new VisitorBuilder();
        long files=vb
                .root(u)
                .selectLeaf(l -> {return l.toURL().getPath().endsWith(".next");})
                .stream().count();

        assertEquals(1,files);

    }
}
