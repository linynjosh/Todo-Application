package ui;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import static java.lang.StrictMath.floor;


// Weather application
public class WeatherApp {
    private Scanner input;
    private String weatherDescription;
    private String weatherData;
    private String location;
    private String imageIcon;

    // EFFECTS: runs the get location function
    public WeatherApp(String location) {
        getLocation(location);
    }

    // REQUIRES: a valid country, state, or city name
    // MODIFIES: this
    // EFFECTS: processes user input
    public void getLocation(String location) {
        init();
//        System.out.println("Enter location to check for today's weather: ");
//        String location = input.nextLine();
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
        this.weatherDescription = "";
        while (itr3.hasNext()) {
            Iterator<Map.Entry> itr1 = ((Map) itr3.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                if (pair.getKey().toString().equals("icon")) {
                    this.imageIcon = pair.getValue().toString();
                    this.weatherDescription += "weather icon" + " : " + pair.getValue() + "\n";
                } else {
                    this.weatherDescription += pair.getKey() + " : " + pair.getValue() + "\n";
                }
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
        this.weatherData = "";
        while (itr2.hasNext()) {
            Map.Entry pair = itr2.next();
            if (pair.getKey().toString().equals("temp")) {
                kelvinsToCelcius(pair);
            } else if (pair.getKey().toString().equals("temp_min")) {
                kelvinsToCelcius(pair);
            } else if (pair.getKey().toString().equals("feels_like")) {
                kelvinsToCelcius(pair);
            } else if (pair.getKey().toString().equals("temp_max")) {
                kelvinsToCelcius(pair);
            } else {
                this.weatherData += pair.getKey() + " : " + pair.getValue() + "\n";
            }
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }

    // MODIFIES: this
    // EFFECTS: converts the temperature in kelvins to Celsius
    public void kelvinsToCelcius(Map.Entry pair) {
        Double temp = Float.parseFloat(pair.getValue().toString()) - 273.15;
        temp = 0.01 * floor(temp * 100.0);
        this.weatherData += pair.getKey() + " : " + temp + "\n";
    }

    // EFFECTS: retrieves the coordinate of location
    public void getCoordinate(Object obj) {
        JSONObject jo = (JSONObject) obj;
        System.out.println("\nCoordinate: ");
        Map coordinate = ((Map)jo.get("coord"));
        Iterator<Map.Entry> itr1 = coordinate.entrySet().iterator();
        this.location = "";
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + ": " + pair.getValue());
            this.location += pair.getKey() + ": " + pair.getValue() + "\n";
        }
    }

    // EFFECTS: returns weatherDescription
    public String getWeatherDescription() {
        return weatherDescription;
    }

    // EFFECTS: returns weatherData
    public String getWeatherData() {
        return weatherData;
    }

    // EFFECTS: returns imageIcon
    public String getImageIcon() {
        return imageIcon;
    }

    // EFFECTS: returns location
    public String getCoord() {
        return this.location;
    }

}


