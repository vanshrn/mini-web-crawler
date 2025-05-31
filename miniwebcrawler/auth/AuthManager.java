package src.miniwebcrawler.auth;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class AuthManager {
    public static Map<String, String> login(String loginUrl, String username, String password) throws IOException {
        Connection.Response response = Jsoup.connect(loginUrl)
                .header("Authorization", "Basic " + java.util.Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes()))
                .method(Connection.Method.GET)
                .execute();

        if (response.statusCode() == 200) {
            System.out.println("✅ Login successful!");
            return response.cookies();
        } else {
            System.out.println("❌ Login failed with status: " + response.statusCode());
            return null;
        }
    }

    public static Document getAuthenticatedPage(String url, Map<String, String> cookies) throws IOException {
        return Jsoup.connect(url)
                .cookies(cookies)
                .get();
    }
}
