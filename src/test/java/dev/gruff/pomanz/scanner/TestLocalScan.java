package dev.gruff.pomanz.scanner;

import org.junit.jupiter.api.Test;

import java.io.File;

public class TestLocalScan {

    private static File home=new File(System.getProperty("user.home"),".m2");

    @Test
    public void testfindMyPOM() {
            VisitorBuilder vb=new VisitorBuilder();
            long poms=vb.createFor(home)
                    .onLeaf( l -> {System.out.println("leaf "+l.toURL());})
                    .stream().count();
            System.out.println(poms);

    }
}
