package nmotion.promopass;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

class Connection extends AsyncTask<String, Void, JSONArray> {

	OkHttpClient client = new OkHttpClient();

	protected JSONArray doInBackground(String... urls) {
        Request request;

        if(urls.length == 1) {
            request = new Request.Builder()
                    .url(urls[0])
                    .build();
        }
        else{
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), urls[1]);
            request = new Request.Builder()
                    .url(urls[0])
                    .post(body)
                    .build();
        }

		Response response;

		try {
			response = client.newCall(request).execute();

            if(urls.length == 2) return null;

            String jsonString = response.body().string();
			JSONObject json = new JSONObject(jsonString);  //DB obj

			Iterator<String> iter =json.keys();
			String tableName = iter.next();
			String allArrays = json.getString(tableName);

			JSONArray allArrays_JSONARRAY = new JSONArray(allArrays);

			return allArrays_JSONARRAY;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}