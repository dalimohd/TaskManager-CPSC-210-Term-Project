package model;
import com.google.gson.Gson;
import weather.WeatherInfo;

import java.io.*;
import java.net.URL;

public class Parser implements Serializable{
    Gson gson = new Gson();

    public String getWeather(String s){
        try {
            String WeatherJsonString = readUrl("http://api.openweathermap.org/data/2.5/weather?q=" + s + "&appid=70d77abc60c1f04eb96c937976a1297b&units=metric");
            WeatherInfo weatherData = gson.fromJson(WeatherJsonString, WeatherInfo.class);

            return weatherData.getWeather().get(0).getDescription()+", currently " + weatherData.getMain().getTemp() + "°C with a high of " +weatherData.getMain().getTempMax() + "°C and a low of " +weatherData.getMain().getTempMin()+"°C";
        } catch (IOException e){
            return null;
        }
    }


    private static String readUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        } //src: https://stackoverflow.com/questions/7467568/parsing-json-from-url
    }
}
