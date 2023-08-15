package dev.gruff.pomanz.scanner;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

public class TestFilePath {

    private static File home=new File(System.getProperty("user.home"));
    @Test
    public void testFilePath() {

        FileSystemPathProvider fp=new FileSystemPathProvider(new File(home,".m2"));
        Path p=fp.rootPath();
        System.out.println(p.toURL());


    }

    @Test
    public void testFilePathExpansion() {
        FileSystemPathProvider fp=new FileSystemPathProvider(new File(home,".m2"));
        Path p=fp.rootPath();
        Set<Path> kids=fp.expand(p);
        assert(kids.isEmpty()==false);
    }
}
