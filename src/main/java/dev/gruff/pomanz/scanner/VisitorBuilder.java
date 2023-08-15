package dev.gruff.pomanz.scanner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import java.util.stream.Stream;

public class VisitorBuilder {

    private PathProvider provider;
    private List<Consumer<Path>> leafVisitors=new LinkedList<>();

    public VisitorBuilder createFor(PathProvider p) {
        this.provider=p;
        return this;
    }

    public VisitorBuilder createFor(File f) {
       provider=new FileSystemPathProvider(f);
       return this;
    }

    public void visit() {
         new Visitor(provider,leafVisitors).visit();;
    }

    public Stream<Path> stream() {
        return new Visitor(provider,leafVisitors).stream();
    }

    public VisitorBuilder onLeaf(Consumer<Path> c) {
        leafVisitors.add(c);
        return this;
    }

    public class Visitor {


        private PathProvider provider;
        private  List<Consumer<Path>> leaftConsumers;
        private final List<Consumer<Path>> interMediateConsumers = new LinkedList<>();

        private Visitor(PathProvider provider,List<Consumer<Path>> leafVisitors) {
            this.provider = provider;
            this.leaftConsumers=leafVisitors;
        }

        public void visit() {

            long c = unpack(provider.rootPath()).count();

        }


        private Stream<Path> unpack(Path root) {

            Set<Path> roots=provider.expand(root);

            if(roots.isEmpty()) {
                // leaf point
                for (Consumer<Path> c : leaftConsumers) {
                    c.accept(root);
                }
                return roots.stream();
            } else {
                for(Consumer<Path> c: interMediateConsumers) {
                    c.accept(root);
                }
                return roots.stream().flatMap(f -> unpack(f));
            }

        }


        public Stream<Path> stream() {

            return unpack(provider.rootPath());
        }
    }
}
