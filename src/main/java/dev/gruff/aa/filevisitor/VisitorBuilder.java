package dev.gruff.aa.filevisitor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class VisitorBuilder {

    private PathProvider provider;
    private List<Consumer<Path>> leafVisitors=new LinkedList<>();
    private List<Predicate<Path>> selectors=new LinkedList<>();
    public VisitorBuilder root(PathProvider p) {
        this.provider=p;
        return this;
    }

    public VisitorBuilder root(File f) {
       provider=new FileSystemPathProvider(f);
       return this;
    }
    public VisitorBuilder root(URL u) {

        String protocol=u.getProtocol();
        switch(protocol) {
            case  "https" :
            case "http" : provider = new HTMLBasedPathProvider(u); break;
            case "file" :
                File path=new File(u.getPath());
                provider=new FileSystemPathProvider(path);
                break;
        }
        return this;
    }

    public VisitorBuilder root(String s) throws MalformedURLException{
        URL u=new URL(s);
        return this.root(u);

    }

    public VisitorBuilder selectLeaf(Predicate<Path> p) {
        selectors.add(p);
        return this;
    }
    public void visit() {
         new Visitor(provider,leafVisitors,selectors).visit();
    }

    public Stream<Path> stream() {
        return new Visitor(provider,leafVisitors,selectors).stream();
    }

    public VisitorBuilder onLeaf(Consumer<Path> c) {
        leafVisitors.add(c);
        return this;
    }

    public class Visitor {


        private PathProvider provider;
        private  List<Consumer<Path>> leaftConsumers;
        private List<Predicate<Path>> selectors;
        private final List<Consumer<Path>> interMediateConsumers = new LinkedList<>();

        private Visitor(PathProvider provider, List<Consumer<Path>> leafVisitors, List<Predicate<Path>> selectors) {
            this.provider = provider;
            this.leaftConsumers=leafVisitors;
            this.selectors=selectors;
        }

        public void visit() {

            long c = unpack(provider.rootPath()).count();

        }


        private Stream<Path> unpack(Path root) {

             if(root==null) throw new RuntimeException("null root");
            Set<Path> roots=provider.expand(root);
            if(roots.isEmpty()) {

                // leaf node ..
                if(wanted(root)) {
                    // leaf point
                    for (Consumer<Path> c : leaftConsumers) {
                        c.accept(root);
                    }
                    roots.add(root);
                }
                return roots.stream();
            } else {
                for(Consumer<Path> c: interMediateConsumers) {
                    c.accept(root);
                }
                return roots.stream().flatMap(f -> unpack(f));
            }

        }

        private boolean wanted(Path root) {

            if(selectors.isEmpty()) return true;

            for(Predicate<Path> p:selectors) {
                if(p.test(root)) return true;
            }
            return false;

        }


        public Stream<Path> stream() {

            return unpack(provider.rootPath());
        }
    }
}
