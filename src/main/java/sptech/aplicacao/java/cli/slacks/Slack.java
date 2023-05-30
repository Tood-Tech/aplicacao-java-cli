package sptech.aplicacao.java.cli.slacks;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aleex
 */
public class Slack {

    private static HttpClient client = HttpClient.newHttpClient();
    private static final String URL = "";

    public static void sendMessage(JSONObject content) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(URL))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(String.format("Status: %s"
                + "\nResponse: %s",
                response.statusCode(),
                response.body()));

    }

    // https://hooks.slack.com/services/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
    
}
