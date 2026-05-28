import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApp {

    public static void main(String[] args) {
        try {
            // API URL (Bhubaneswar coordinates)
            String url = "https://api.open-meteo.com/v1/forecast?latitude=20.27&longitude=85.84&current_weather=true";

            // Step 1: Create HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            // Step 2: Create Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Step 3: Send Request & get Response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Step 4: Store JSON response
            String json = response.body();

            // Step 5: Extract values manually
            double temperature = extractValue(json, "temperature");
            double windspeed = extractValue(json, "windspeed");

            // Step 6: Display structured output
            System.out.println("===== Weather Report =====");
            System.out.println("Location : Bhubaneswar");
            System.out.println("Temperature : " + temperature + " °C");
            System.out.println("Wind Speed : " + windspeed + " km/h");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to extract value from JSON string
    public static double extractValue(String json, String key) {
        try {
            String searchKey = "\"" + key + "\":";
            int start = json.indexOf(searchKey) + searchKey.length();

            int end = json.indexOf(",", start);
            if (end == -1) {
                end = json.indexOf("}", start);
            }

            String value = json.substring(start, end).trim();
            return Double.parseDouble(value);

        } catch (Exception e) {
            return 0.0;
        }
    }
}