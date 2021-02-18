package ui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


// Top news headlines application
public class TopNewsApp {
    private Scanner input;

    // EFFECTS: runs the news application
    public TopNewsApp() throws Exception {
        news();
    }

    // EFFECTS: reads in news from newsapi.org as a string
    public void news() {
        try {
            String apiKey = "77430d0d2b5763a80bd7678cb3524877";
            URL myUrl = new URL("http://newsapi.org/v2/top-headlines?"
                    + "country=us&" + "apiKey=9b72ee11c2b44f5ca72c1666cbd78ad3");
            Scanner scanner = new Scanner(myUrl.openStream());
            StringBuffer stringBuffer = new StringBuffer();
            while (scanner.hasNext()) {
                stringBuffer.append(scanner.next() + " ");
            }
            String news = stringBuffer.toString();
            news = news.replaceAll("<[^>]*>", "");
            cleanData(news);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: parses string and retrieves status, total results, and articles
    public void cleanData(String news) throws Exception {
        Object obj = new JSONParser().parse(news);
        JSONObject jo = (JSONObject) obj;
        String status = (String) jo.get("status");
        System.out.println("Status: " + status);
        System.out.println("Total Results: " + jo.get("totalResults"));
        JSONArray todayNews = (JSONArray) jo.get("articles");
        Iterator iterateArticles = todayNews.iterator();
        while (iterateArticles.hasNext()) {
            Iterator<Map.Entry> itr = ((Map) iterateArticles.next()).entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry pair = itr.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }

    }
}
