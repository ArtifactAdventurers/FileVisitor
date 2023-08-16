package dev.gruff.aa.filevisitor;

import dev.gruff.aa.filevisitor.util.HTMLHelper;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class HTMLBasedPathProvider implements PathProvider{

    public class HTMLPath implements Path {

        private URL base;



        public HTMLPath(URL u) {
            base=u;
        }

        @Override
        public URL toURL() {
            return base;
        }
    }
    HTMLPath base=null;

    public HTMLBasedPathProvider(java.net.URL root)  {

        base=new HTMLPath(root);
    }

    @Override
    public Set<Path> expand(Path root) {

        Set<Path> results=new HashSet<>();
        if(root instanceof HTMLPath) {
            HTMLPath hp= (HTMLPath) root;
            URL rootURL=hp.base;
            Set<URL> links= HTMLHelper.getLinks(rootURL);
            for(URL s:links) {
                results.add(new HTMLPath(s));
            }

        }
        return results;

    }

    @Override
    public Path rootPath() {
        return base;
    }
}
