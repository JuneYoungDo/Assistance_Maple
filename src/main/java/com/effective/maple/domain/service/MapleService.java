package com.effective.maple.domain.service;

import com.effective.maple.global.exception.BaseException;
import com.effective.maple.global.exception.errorCode.CharacterErrorCode;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapleService {

    @Value("${nexon.maple.api-key}")
    private String API_KEY;

    public JSONObject getStatInformation(String nickname)
        throws IOException, ParseException, InterruptedException {
        String ocid = useGetOcidApi(nickname);
        JSONObject jsonObject = useGetCharacterInformation(ocid);
        jsonObject.put("level", getLevel(ocid));
        return jsonObject;
    }

    private Object getLevel(String ocid)
        throws IOException, InterruptedException, ParseException {
        String id = URLEncoder.encode(ocid, StandardCharsets.UTF_8);
        String url = "https://open.api.nexon.com/maplestory/v1/character/basic?ocid=" + id;
        HttpResponse<String> response = useApi(url, "GET");
        if (response.statusCode() != 200) {
            throw new BaseException(CharacterErrorCode.INVALID_NICKNAME);
        }
        JSONObject jsonObject = parseBody(response);
        return jsonObject.get("character_level");
    }

    private String useGetOcidApi(String nickname)
        throws IOException, InterruptedException, ParseException {
        String characterName = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        String url = "https://open.api.nexon.com/maplestory/v1/id?character_name=" + characterName;
        HttpResponse<String> response = useApi(url, "GET");
        if (response.statusCode() != 200) {
            throw new BaseException(CharacterErrorCode.INVALID_NICKNAME);
        }
        JSONObject jsonObject = parseBody(response);
        return (String) jsonObject.get("ocid");
    }

    public JSONObject useGetCharacterInformation(String ocid)
        throws IOException, InterruptedException, ParseException {
        String id = URLEncoder.encode(ocid, StandardCharsets.UTF_8);
        String url = "https://open.api.nexon.com/maplestory/v1/character/item-equipment?ocid=" + id;

        HttpResponse<String> response = useApi(url, "GET");
        if (response.statusCode() != 200) {
            throw new BaseException(CharacterErrorCode.SERVER_ERROR);
        }
        return parseBody(response);
    }

    private HttpResponse<String> useApi(String url, String method)
        throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("x-nxopen-api-key", API_KEY)
            .method(method, HttpRequest.BodyPublishers.noBody())
            .build();

        return HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());
    }

    private JSONObject parseBody(HttpResponse<String> response)
        throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        return (JSONObject) obj;
    }
}
