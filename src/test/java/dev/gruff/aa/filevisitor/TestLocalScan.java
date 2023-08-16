package dev.gruff.aa.filevisitor;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLocalScan {

    private static File home=new File(new File(System.getProperty("user.home"),".m2"),"repository");

    @Test
    public void testfindMyPOM() throws MalformedURLException {
            VisitorBuilder vb=new VisitorBuilder();
            long poms=vb.root("file:"+home.getAbsolutePath())
                    .selectLeaf( p-> {
                        String name=p.toURL().toExternalForm();
                        if(!name.endsWith(".pom")) return false;
                        String[] parts=name.split("/");
                       return parts[parts.length-1].startsWith("filevisitor");

                    })

                    .stream().count();

            assertEquals(1,poms);


    }
}
