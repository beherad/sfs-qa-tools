package com.sfs.project.emp.web.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtil {

    public static JsonElement stringToJson(String text) {
        JsonElement jsonElement;
        JsonParser parser = new JsonParser();
        try {
            jsonElement = parser.parse(text);
        } catch (Exception e) {
            throw new NullPointerException("Json error:" + e.getMessage() + "\n" + text);
        }
        return jsonElement;
    }

    public Object deserialize(Object object, String jsonFilePath) {
        try {
            JsonReader input = new JsonReader(new FileReader(jsonFilePath));
            Gson gson = new Gson();
            object = gson.fromJson(input, object.getClass());
        } catch (FileNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } catch (JsonSyntaxException j) {
            throw new JsonSyntaxException(j.getMessage());
        }
        return object;
    }

    public String jsonFileToString(String jsonFilePath) {
        String jsonString;
        try {
            JsonReader input = new JsonReader(new FileReader(jsonFilePath));
            jsonString = new Gson().fromJson(input, Object.class).toString();
            jsonString = jsonString.replaceAll("([a-zA-Z_][\\w-]*)", "\"$1\"");
        } catch (FileNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        } catch (JsonSyntaxException j) {
            throw new JsonSyntaxException(j.getMessage());
        }
        return jsonString;
    }
}
