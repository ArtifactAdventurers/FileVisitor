package dev.gruff.aa.filevisitor.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class HTMLHelper {

    public static Set<URL> getLinks(URL url) {

        try (InputStream in = url.openStream()) {
            Document doc = Jsoup.parse(in, "UTF-8", url.toExternalForm());
            return parseDoc(doc,url);
        } catch (IOException ioe) {
            ;
        }

        return new HashSet<>();


    }

    private static Set<URL> parseDoc(Document doc,URL loc) {

        Set<URL> links=new HashSet<>();
        String external=loc.toExternalForm();
        String path= loc.getPath();
        int l=path.length();
        String hprefix=external.substring(0,external.length()-l);

        if(path.endsWith("/")) path=path.substring(0,path.length()-1);
        int lastSlash=path.lastIndexOf('/');
        String prefix;
        if(lastSlash<0) {
            // at root
            prefix=hprefix;
        } else {
            prefix=hprefix+path.substring(0,lastSlash+1);
        }

         Elements anchors = doc.select("a[href]");
        anchors.forEach( a-> {
            if(a.hasAttr("href")) {
                String title=a.attr("href");
                if(title.endsWith("/")) {
                    if (!title.equalsIgnoreCase("../")) links.add(toURL(prefix+title));
                }
                else links.add(toURL(prefix+title));
            }
        } );
        return links;
    }

    private static URL toURL(String s) {

        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
