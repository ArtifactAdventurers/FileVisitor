package dev.gruff.anz.file.visitor;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilePath {

    private static File home=new File(System.getProperty("user.home"));
    @Test
    public void testFilePath() {

        FileSystemPathProvider fp=new FileSystemPathProvider(new File(home,".m2"));
        Path p=fp.rootPath();
        assertEquals("file:/"+home+"/.m2",p.toURL().toExternalForm());

    }

    @Test
    public void testFilePathExpansion() {
        FileSystemPathProvider fp=new FileSystemPathProvider(new File(home,".m2"));
        Path p=fp.rootPath();
        Set<Path> kids=fp.expand(p);
        assert(kids.isEmpty()==false);
    }


    }
