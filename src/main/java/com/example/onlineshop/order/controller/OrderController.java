package com.example.onlineshop.order.controller;

import com.example.onlineshop.order.model.CustomDataDto;
import com.example.onlineshop.order.service.OrderService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin("*")       // cors 문제를 백엔드 서버에서 막아서 요청을 받는다.
@Slf4j
public class OrderController {

    @Value("${imp.key}")
    private String key;

    @Value("${imp.secret}")
    private String secret;

    private final OrderService orderService;

    public String getToken() throws IOException{
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/users/getToken");
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", key);
        json.addProperty("imp_secret", secret);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String token = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close();
        conn.disconnect();
        return token;
    }

    public Map<String, String> getPaymentInfo(String impUid) throws IOException{
        String token = getToken();

        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/" + impUid);
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

        String amount = response.split("amount")[1].split(",")[0].replace("=", "");
        String name = response.split(" name")[1].split(",")[0].replace("=", "");
        String customData = response.split(" custom_data")[1].split(", c")[0].replace("=", "");

        br.close();
        conn.disconnect();

        Map<String, String> result = new HashMap<>();
        result.put("name", name);
        result.put("amount", amount);
        result.put("customData", customData);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/valid")
    public ResponseEntity<Object> paymentValidation(String impUid) throws IOException{
        Map<String, String> paymentInfo = getPaymentInfo(impUid);

        Gson gson = new Gson();
        CustomDataDto customDataDto = gson.fromJson(paymentInfo.toString(), CustomDataDto.class);

        if (orderService.calculate(paymentInfo.get("amount"), customDataDto)) {
            return ResponseEntity.ok().body("ok");
        } else {
            String token = getToken();
            paymentCancel(token, impUid, paymentInfo.get("amount"));

            return ResponseEntity.badRequest().body("error");
        }
    }

    private void paymentCancel(String token, String impUid, String amount) throws IOException {

        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", token);
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_uid", impUid);
        json.addProperty("amount", amount);
        json.addProperty("reason", "결제가 취소되었습니다.");
        json.addProperty("checksum", amount);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();
    }
}
