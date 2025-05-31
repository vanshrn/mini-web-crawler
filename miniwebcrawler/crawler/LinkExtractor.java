package src.miniwebcrawler.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

public class LinkExtractor {

    private Map<String, String> cookies;

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Document getDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url).timeout(10000).userAgent("Mozilla/5.0");

            if (cookies != null) {
                connection.cookies(cookies);
            }

            return connection.get();
        } catch (Exception e) {
            System.out.println("Failed to fetch the URL: " + e.getMessage());
            return null;
        }
    }

    public void extractAndPrintLinks(Document doc) {
        Elements links = doc.select("a[href]");
        System.out.println("Found " + links.size() + " links:");
        for (Element link : links) {
            System.out.println(link.absUrl("href"));
        }
    }
}
