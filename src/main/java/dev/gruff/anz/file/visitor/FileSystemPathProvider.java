package dev.gruff.anz.file.visitor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class FileSystemPathProvider implements PathProvider{

    private class FilePath implements Path {

        private File base;

        private FilePath(File base) {
            this.base = base;
        }

        @Override
        public URL toURL() {
            try {
                return new URL("file:/"+base.getAbsolutePath());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private FilePath base;
    public FileSystemPathProvider(File f) {
        this.base=new FilePath(f);
    }

    @Override
    public Set<Path> expand(Path root) {

        Set<Path> results=new HashSet<>();
        if(root instanceof  FilePath ) {
            FilePath fp= (FilePath) root;
            if(fp.base.isDirectory()) {
              for(File k:fp.base.listFiles()) {
                  results.add(new FilePath(k));
              }
            }
        }
        return results;
    }

    @Override
    public Path rootPath() {
        return base;
    }
}
