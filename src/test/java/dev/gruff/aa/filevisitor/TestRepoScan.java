package dev.gruff.aa.filevisitor;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRepoScan {

    @Test
    public void testfindMyPOM() throws MalformedURLException {
            VisitorBuilder vb=new VisitorBuilder();
       long poms=   vb.root("https://repo.maven.apache.org/maven2/HTTPClient/")
                    .stream()
                    .filter(p -> {return p.toURL().toExternalForm().endsWith(".pom");})
               .count();

            assertEquals(1,poms);


    }
}
