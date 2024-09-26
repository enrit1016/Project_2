package kr.co.duck.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
public class YouTubeService {
    private static final String API_KEY = "AIzaSyBLzIORdM8y-Clju3H0X7lVciqTLMKJ7eg"; // 여기에 API 키를 넣으세요.
    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search";

    public JSONArray searchVideos(String query) throws Exception {
        String urlString = YOUTUBE_API_URL + "?part=snippet&q=" + query + "&key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }

        conn.disconnect();

        JSONObject jsonResponse = new JSONObject(response.toString());
        
        
        return jsonResponse.getJSONArray("items"); // 비디오 리스트 반환
    }
}
