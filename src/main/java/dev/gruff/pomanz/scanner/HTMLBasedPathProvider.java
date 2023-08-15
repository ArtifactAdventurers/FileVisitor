package dev.gruff.pomanz.scanner;

import java.util.Set;

public class HTMLBasedPathProvider implements PathProvider{

    String root=null;

    public HTMLBasedPathProvider(String root) {
        this.root=root;
    }

    @Override
    public Set<Path> expand(Path root) {
        return null;
    }

    @Override
    public Path rootPath() {
        return null;
    }
}
