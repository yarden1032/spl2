package bgu.spl.mics.application;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InOutputJsonClass {

    static Gson Read (String inputString) throws IOException {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("input.json"));
            Gson gson1=new Gson();
            return gson1;



    } catch (Exception ex) {
        ex.printStackTrace();

    }


       return null;
    }

    static void Output (Gson toOutput, List  ls) throws IOException {
        try (FileWriter writer = new FileWriter("output.json")) {
            toOutput.toJson(ls,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
