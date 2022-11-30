package me.jrpc;

import java.net.URL;
import java.nio.file.Paths;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigJson {

    private static volatile ConfigJsonObject instance = null;
    private static Object synObject = new Object();

    public static ConfigJsonObject getInstance(Gson gson) {
        if (instance != null) {
            return instance;
        }
        synchronized (synObject) {
            String json = readJsonConfigFile();
            instance = gson.fromJson(json, ConfigJsonObject.class);
        }

        return instance;
    }

    private static String readJsonConfigFile() {

        try {
            URL resource = ConfigJson.class.getClassLoader().getResource("jrpc-config.json");
            String filePath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
            String json = readFileAsString(filePath);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF8"));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

}
