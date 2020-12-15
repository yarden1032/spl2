package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InOutputJsonClass {



    public static Map Read (String inputString) {
        Reader reader = null;
        Map<?,?> map=null;
        try {
            Gson g=new Gson();
            reader = Files.newBufferedReader(Paths.get(inputString));
             map = g.fromJson(reader, Map.class);



    } catch (Exception ex) {
        ex.printStackTrace();

    }


       return map;
    }

    public static ArrayList readAttack (Map map) {


        Map<String , ArrayList<Attack>>map2=map;
        Attack[] attacktoreturn=null;
        ArrayList arr=null;
            int i = 0;
             attacktoreturn = new Attack[map.size()];
            for (Map.Entry<String, ArrayList<Attack>> entry : map2.entrySet()) {

                if (entry.getKey().equals("attacks"))
                {
                     arr=  entry.getValue();
                    return arr;
                }

                i++;
            }






        return null;
    }
    public static double readOther (Map map,String string) {
        Map<String, Double> map2 = map;
        for (Map.Entry<String, Double> entry : map2.entrySet()) {

            if (entry.getKey().equals(string)) {
                return entry.getValue();
            }

        }

        return 0;
    }

   /* static void Output (Gson toOutput, List  ls) throws IOException {
        try (FileWriter writer = new FileWriter("output.json")) {
            toOutput.toJson(ls,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

    public static void output()
    {
        try (Writer writer = new FileWriter("Output.json")){
            List listDiary=Diary.getInstance().getLittleDiary();
            Gson g= new Gson();
            g.toJson(listDiary,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
