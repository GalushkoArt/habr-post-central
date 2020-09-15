package tech.mtright.habrcrawler.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ParserUtils {
    private ParserUtils() {}

    public static Document getDocumentFromLink(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(30000)
                .referrer("http://google.com")
                .get();
    }
}
