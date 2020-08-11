import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HelmTest {

    private static String baseUrl;

    public static void main(String... args) throws IOException, InterruptedException {
        if (args.length == 0) {
            baseUrl = "https://localhost:8080";
        } else {
            baseUrl = args[0];
        }
        System.out.println("base url is: " + baseUrl);

        readFiles();
    }

    public static void readFiles() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/read"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert response.statusCode() == 200 : "response should be 200, found " + response.statusCode();
        String body = response.body();
        assert body.length() > 0: "expected some body in response";
    }

}
