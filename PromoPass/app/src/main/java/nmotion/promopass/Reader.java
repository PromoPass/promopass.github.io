package nmotion.promopass;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

/**
 * Created by Anna on 11/10/2015.
 */

public class Reader {

    public static JSONArray getResults(String url){
        Connection conn = new Connection();
        conn.execute(url);

        JSONArray results = null;
        try {
            results = conn.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static void update(String url){
        Connection conn = new Connection();
        conn.execute(url);

        try {
            conn.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static void insert(String url, String jsonParameters){

        Connection conn = new Connection();
        conn.execute(url, jsonParameters);

        try {
            conn.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
