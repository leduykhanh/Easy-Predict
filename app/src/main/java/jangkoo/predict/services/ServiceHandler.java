package jangkoo.predict.services;


import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import java.nio.charset.Charset;

import kotlin.Triple;

/**
 * Created by ittus on 12/1/15.
 */
public class ServiceHandler {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
//        return this.makeServiceCall(url, method, null);
        String return_data ="";
        try {
            if(method == GET){
                Triple<Request, Response, String> data = Fuel.get(url).responseString(Charset.forName("UTF-8"));
                Request request = data.getFirst();
                Response response = data.getSecond();
                String text = data.getThird();
                return_data = text;
            }
        } catch (Exception networkError) {

        }
        return return_data;
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
//    public String makeServiceCall(String url, int method,
//                                  List<NameValuePair> params) {
//        try {
//            // http client
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpEntity httpEntity = null;
//            HttpResponse httpResponse = null;
//
//            // Checking http request method type
//            if (method == POST) {
//                HttpPost httpPost = new HttpPost(url);
//                // adding post params
//                if (params != null) {
//                    httpPost.setEntity(new UrlEncodedFormEntity(params));
//                }
//
//                httpResponse = httpClient.execute(httpPost);
//
//            } else if (method == GET) {
//                // appending params to url
//                if (params != null) {
//                    String paramString = URLEncodedUtils
//                            .format(params, "utf-8");
//                    url += "?" + paramString;
//                }
//                HttpGet httpGet = new HttpGet(url);
//
//                httpResponse = httpClient.execute(httpGet);
//
//            }
//            httpEntity = httpResponse.getEntity();
//            response = EntityUtils.toString(httpEntity);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return response;
//
//    }
//
//    public String sendJsonPostRequest(String url,  JSONObject jobj){
//        String response = null;
//        try {
//
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppostreq = new HttpPost(url);
//            StringEntity se = new StringEntity(jobj.toString());
//            se.setContentType("application/json;charset=UTF-8");
//            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
//            httppostreq.setEntity(se);
//            HttpResponse httpresponse = httpclient.execute(httppostreq);
//            response = EntityUtils.toString(httpresponse.getEntity());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
}
