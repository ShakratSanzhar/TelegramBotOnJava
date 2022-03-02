import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Map2Gis {
    public static Double[] getCoordinate(String name, Model2Gis model) throws IOException
    {
        String url = "https://catalog.api.2gis.com/3.0/items/geocode?q="+name+"&fields=items.point&key=rusxwe7973";
        HttpURLConnection connection = null;
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = reader.readLine()) != null)
        {
            response.append(inputLine);
        }

        JSONObject object = new JSONObject(response.toString());
        JSONObject result = object.getJSONObject("result");
        JSONArray getArray = result.getJSONArray("items");
        for(int i=0;i< getArray.length();i++)
        {
            JSONObject obj = getArray.getJSONObject(i);
            JSONObject p = (JSONObject) obj.get("point");
            model.setLat((Double) p.get("lat"));
            model.setLon((Double) p.get("lon"));
        }
        Double[] array = new Double[2];
        array[0] = model.getLat();
        array[1] = model.getLon();

        return array;

    }
}
