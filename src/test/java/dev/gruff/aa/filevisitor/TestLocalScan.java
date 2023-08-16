package dev.gruff.aa.filevisitor;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;

public class TestLocalScan {

    private static File home=new File(new File(System.getProperty("user.home"),".m2"),"repository");

    @Test
    public void testfindMyPOM() throws MalformedURLException {
            VisitorBuilder vb=new VisitorBuilder();
            long poms=vb.root("file:"+home.getAbsolutePath())
                    //.onLeaf(l-> {System.out.println("leaf "+l.toURL().toExternalForm());})
                    .stream().dropWhile(p -> {return !p.toURL().toExternalForm().endsWith(".pom");}).count();
            System.out.println(poms);

    }
}
