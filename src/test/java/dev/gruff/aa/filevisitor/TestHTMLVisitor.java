package dev.gruff.aa.filevisitor;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHTMLVisitor {

    @Test
    public void testNoLinks() throws URISyntaxException {

            VisitorBuilder vb=new VisitorBuilder();
            URL u=getClass().getResource("/testdir/nolinks/index.html");
            HTMLBasedPathProvider h=new HTMLBasedPathProvider(u);

            long c=vb.root(h).stream().count();

            assertEquals(1,c);


    }

    @Test
    public void testLinks1() throws URISyntaxException {

        VisitorBuilder vb=new VisitorBuilder();
        URL u=getClass().getResource("/testdir/links/links1.html");
        HTMLBasedPathProvider h=new HTMLBasedPathProvider(u);
       long l=vb.root(h).stream().count();
        assertEquals(1,l);


    }


    @Test
    public void testLinks() throws URISyntaxException {

        VisitorBuilder vb=new VisitorBuilder();
        URL u=getClass().getResource("/testdir/links");
        long l=vb.root(u).stream().count();
        assertEquals(3,l);


    }
}
