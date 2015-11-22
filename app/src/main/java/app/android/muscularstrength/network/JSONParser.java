package app.android.muscularstrength.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Bisht Bhawna on 8/8/2015.
 */
public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result = new StringBuilder();
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public JSONObject makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        Log.d("Params",""+sbParams);

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);
                Log.d("url=",""+url.toString());
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", "" +
                        Integer.toString(sbParams.toString().getBytes().length));
                conn.setRequestProperty("Content-Language", "en-US");
                conn.setUseCaches (false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();
                paramsString = sbParams.toString();

                //Send request
                 wr = new DataOutputStream (
                        conn.getOutputStream ());
                wr.writeBytes (paramsString);
                wr.flush ();
                wr.close ();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }
Log.i("URL","url="+url);
           try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {

                e.printStackTrace();
                return null;
            }

        }

        try {
            //Get Response
            InputStream is = conn.getInputStream();
            Log.d("RESPONSE CODE=",""+conn.getResponseCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
           // StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                result.append(line);
                result.append('\r');
            }
            rd.close();

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            return null;
        }

        // return JSON Object
        return jObj;
    }
}