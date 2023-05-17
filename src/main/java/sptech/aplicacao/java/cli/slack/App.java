
package sptech.aplicacao.java.cli.slack;

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
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("text","Tuc tuc tuc 🍼");
        Slack.sendMessage(json);
    }
}
