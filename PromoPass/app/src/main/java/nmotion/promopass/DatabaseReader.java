package nmotion.promopass;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

class DatabaseReader extends AsyncTask<String, Void, String> {

	private Exception exception;

	OkHttpClient client = new OkHttpClient();

	protected String doInBackground(String... urls) {
		Request request = new Request.Builder()
				.url(urls[0])
				.build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(String feed) {
		// TODO: check this.exception
		// TODO: do something with the feed
	}
}