package vn.shopttcn.controller.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CallApi_RS {
    // Hàm thêm parameter
    public static String addParameter(String URL, String name, String value) {
        return URL + name + value;
    }

    public static ArrayList<String> CallApi(int userId) throws IOException {
        try {
            // Tạo URL với tham số userId
            URL url = new URL(addParameter("http://127.0.0.1:12345/recommendation", "?userId=", Integer.toString(userId)));
            
            // Tạo kết nối
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            // Đọc dữ liệu từ InputStream
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder informationString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                informationString.append(line);
            }
            reader.close();
            
            // Parse dữ liệu JSON
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(informationString.toString());
            ArrayList<String> itemsId = (ArrayList<String>) jsonObject.get("recommended_items");
            return itemsId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
