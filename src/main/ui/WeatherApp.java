package ui;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;



// Weather application
public class WeatherApp {
    private Scanner input;

    // EFFECTS: runs the get location function
    public WeatherApp() {
        getLocation();
    }

    // REQUIRES: a valid country, state, or city name
    // MODIFIES: this
    // EFFECTS: processes user input
    public void getLocation() {
        init();
        System.out.println("Enter location to check for today's weather: ");
        String location = input.nextLine();
        weatherToday(location);
    }

    // EFFECTS: reads in weather data from openweathermap.org as a string
    public void weatherToday(String location) {
        try {
            String apiKey = "77430d0d2b5763a80bd7678cb3524877";
            URL myUrl = new URL("http://api.openweathermap.org/data/2.5/weather?q="
                    + location + "&appid=" + apiKey);
            Scanner scanner = new Scanner(myUrl.openStream());
            StringBuffer stringBuffer = new StringBuffer();
            while (scanner.hasNext()) {
                stringBuffer.append(scanner.next() + " ");
            }
            String weather = stringBuffer.toString();
            weather = weather.replaceAll("<[^>]*>", "");
            data(weather);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    private void init() {
        input = new Scanner(System.in);
    }

    // EFFECTS: parses the string
    public void data(String weather) throws Exception {
        Object obj = new JSONParser().parse(weather);
        weatherDescription(obj);
        weatherData(obj);
        getCoordinate(obj);
    }

    // EFFECTS: retrieves the weather description
    public void weatherDescription(Object obj) {
        JSONObject jo = (JSONObject) obj;
        System.out.println("\nWeather Description:");
        JSONArray weatherDescription = (JSONArray) jo.get("weather");
        Iterator itr3 = weatherDescription.iterator();
        while (itr3.hasNext()) {
            Iterator<Map.Entry> itr1 = ((Map) itr3.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
    }

    // EFFECTS: retrieves weather data
    public void weatherData(Object obj) {
        JSONObject jo = (JSONObject) obj;
        System.out.println("\nWeather Data: ");
        Map temperature = ((Map)jo.get("main"));
        Iterator<Map.Entry> itr2 = temperature.entrySet().iterator();
        while (itr2.hasNext()) {
            Map.Entry pair = itr2.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }

    // EFFECTS: retrieves the coordinate of location
    public void getCoordinate(Object obj) {
        JSONObject jo = (JSONObject) obj;
        System.out.println("\nCoordinate: ");
        Map coordinate = ((Map)jo.get("coord"));
        Iterator<Map.Entry> itr1 = coordinate.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }


}


