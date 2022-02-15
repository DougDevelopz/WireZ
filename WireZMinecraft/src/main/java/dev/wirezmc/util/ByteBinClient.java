package dev.wirezmc.util;

import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ByteBinClient {

    private static AtomicReference<String> keyFetcher = new AtomicReference<>();

    public static void postRequest(List<String> dataList, IAction requestAction) {
        try {
            URL url = new URL("https://bytebin.lucko.me/post");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setDoOutput(true);
            try (OutputStream stream = connection.getOutputStream()) {
                for (String postData : dataList) {
                    stream.write(postData.getBytes());
                }

                stream.flush();
            }

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String key = (new JsonParser()).parse(input).getAsJsonObject().get("key").getAsString();
            keyFetcher.set("https://bytebin.lucko.me/" + key);
            requestAction.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getKey() {
        return keyFetcher.get();
    }
}
