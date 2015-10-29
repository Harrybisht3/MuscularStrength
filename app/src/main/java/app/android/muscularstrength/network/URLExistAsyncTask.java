package app.android.muscularstrength.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sa on 8/17/2015.
 */
public class URLExistAsyncTask extends AsyncTask<String, Void, Boolean> {
   // AsyncTaskCompleteListenere<Boolean> callback;

   /* public URLExistAsyncTask(AsyncTaskCompleteListenere<Boolean> callback) {
        this.callback = callback;
    }*/

    protected Boolean doInBackground(String... params) {
        int code = 0;
        try {
            URL u = new URL(params[0]);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            code = huc.getResponseCode();
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

        return code == 200;
    }

    protected void onPostExecute(Boolean result){
       // callback.onTaskComplete(result);
    }
}
///calling of the class
/*
URLExistAsyncTask task = new URLExistAsyncTask();
String URL = "http://www.google.com";
task.execute(new String[]{URL});*/
