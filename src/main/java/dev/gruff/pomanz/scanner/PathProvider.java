package dev.gruff.pomanz.scanner;

import java.util.Set;

public interface PathProvider {
    Set<Path> expand(Path root);

    Path rootPath();
}
