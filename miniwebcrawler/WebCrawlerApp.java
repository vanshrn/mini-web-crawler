package src.miniwebcrawler;

import src.miniwebcrawler.auth.AuthManager;
import src.miniwebcrawler.crawler.LinkExtractor;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class WebCrawlerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter website URL: ");
            String url = scanner.nextLine();

            System.out.print("Enter login URL (or press Enter to skip login): ");
            String loginUrl = scanner.nextLine();

            Map<String, String> cookies = null;

            if (!loginUrl.trim().isEmpty()) {
                System.out.print("Username: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                // attempt login
                cookies = AuthManager.login(loginUrl, username, password);

                if (cookies == null) {
                    System.out.println("Login failed. Exiting.");
                    return;
                }
            }

            LinkExtractor extractor = new LinkExtractor();

            if (cookies != null) {
                extractor.setCookies(cookies);
            }

            Document doc = extractor.getDocument(url);

            if (doc != null) {
                extractor.extractAndPrintLinks(doc);
            } else {
                System.out.println("Could not retrieve the page.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close(); 
        }
    }
}
