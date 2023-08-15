package dev.gruff.pomanz.scanner;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class FileSystemPathProvider implements PathProvider{

    private class FilePath implements Path {

        private File base;

        private FilePath(File base) {
            this.base=base;
        }

        @Override
        public URI toURL() {
            return base.toURI();
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
