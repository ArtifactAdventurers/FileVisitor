package dev.gruff.anz.file.visitor;

import java.util.Set;

public interface PathProvider {
    Set<Path> expand(Path root);

    Path rootPath();
}
