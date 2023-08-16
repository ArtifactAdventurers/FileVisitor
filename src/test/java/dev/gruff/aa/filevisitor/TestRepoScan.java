package dev.gruff.aa.filevisitor;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class TestRepoScan {

    @Test
    public void testfindMyPOM() throws MalformedURLException {
            VisitorBuilder vb=new VisitorBuilder();
            long poms=vb.root("https://repo.maven.apache.org/maven2/")
                    //.onLeaf(l-> {System.out.println("leaf "+l.toURL().toExternalForm());})
                    .stream().dropWhile(p -> {return !p.toURL().toExternalForm().endsWith(".pom");}).count();
            System.out.println(poms);

    }
}
