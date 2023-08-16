package dev.gruff.aa.filevisitor;

import java.util.Set;

public interface PathProvider {
    Set<Path> expand(Path root);

    Path rootPath();
}
