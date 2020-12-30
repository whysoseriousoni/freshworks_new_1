/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freshworks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author WhysoseriousONI
 */
public class GlobalDecalarations {

    public static Path path = Paths.get("");
    public static String cwd = path.toAbsolutePath().toString();// directory to freshworks
    public static String FILESYSTEM = cwd + "\\src\\main\\java\\FileSystem";
   Gson gson = new Gson();

    public JsonObject parse_all_ds_json() {
        try ( FileReader reader = new FileReader(FILESYSTEM + "\\AllDataStore.json")) {
            JsonParser json = new JsonParser();
            JsonObject temp = new JsonObject();
            temp = (JsonObject) json.parse(reader);
            System.out.println(gson.toJson(temp));
            return temp;
        } catch (Exception e) {
            System.out.println("null called due to cant parse the file");
            System.out.println(e);
            return null;
        }
    }

}
