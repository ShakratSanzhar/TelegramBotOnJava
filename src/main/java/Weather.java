import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;



public class Weather {

    public static String getWeather(Double lat , Double lon, Model model) throws IOException
    {

        String url = "https://api.weather.yandex.ru/v2/forecast?lat="+lat+"&lon="+lon+"&extra=true";
        HttpURLConnection connection = null;
        try
        {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Yandex-API-Key","90c31916-0f2b-4024-ba5b-457b49028175");
        }
        catch(ProtocolException e)
        {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = reader.readLine()) != null)
        {
            response.append(inputLine);
        }


        JSONObject object = new JSONObject(response.toString());
        JSONObject info = object.getJSONObject("info");
        JSONObject geo_object = object.getJSONObject("geo_object");
        JSONObject locality = geo_object.getJSONObject("locality");
        model.setName(locality.getString("name"));
        JSONObject fact = object.getJSONObject("fact");
        model.setTemp(fact.getInt("temp"));
        model.setIcon(fact.getString("icon"));

        return("City: " + model.getName() + "\n" + "Temperature: " + model.getTemp() + " C\n" + "icon: " + "https://yastatic.net/weather/i/icons/funky/dark/"+model.getIcon()+".svg");

    }

}
