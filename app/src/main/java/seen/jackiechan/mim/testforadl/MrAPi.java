package seen.jackiechan.mim.testforadl;

import android.content.Context;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;


/**
 * authored by https://twitter.com/96rajabi
 * MrAPi v 2.1
 */
public class MrAPi extends AsyncTask<Void, Void, Void> {
    public static final String HOST = "http://198.143.183.52/";
    private String response, token, target, method = "get", type = "string";
    private Integer status;
    private List<NameValuePair> params;
    private MrOnTaskExecute onTaskComplete;
    private MrOnTaskExecuteObject onTaskExecuteObject;
    private MrOnTaskExecuteList onTaskExecuteList;
    private MrOnTaskExecuteJsonObject onTaskExecuteJsonObject;
    private MrOnTaskExecuteJsonArray onTaskExecuteJsonArray;
    private Exception exception;
    private static final String[] FILTER_COLUMNS = {"$change", "serialVersionUID"};

    /*
     * Developed by https://twitter.com/96rajabi
     */

    public MrAPi() {
        this.response = "";
        this.token = "";
        this.status = 0;
        this.target = "";
        this.params = new ArrayList<NameValuePair>();
    }

    public MrAPi(String uri, String method) {
        this.response = "";
        this.method = method;
        this.status = 0;
        this.token = "";
        this.target = HOST + uri;
        this.params = new ArrayList<NameValuePair>();
    }

    public MrAPi(String uri, String method, Context tokenContext) {
        this.token = getToken(tokenContext);
        this.response = "";
        this.method = method;
        this.status = 0;
        this.target = HOST + uri;
        this.params = new ArrayList<NameValuePair>();
    }

    public MrAPi(String uri, String method, HashMap<String, String> params) {
        this.response = "";
        this.method = method;
        this.status = 0;
        this.token = "";
        this.target = HOST + uri;
        this.params = makeNameValuePair(params);
    }

    public MrAPi(String uri, String method, Object params, Context token) {
        this.response = "";
        this.status = 0;
        this.token = getToken(token);
        this.target = HOST + uri;
        this.method = method;
        this.params = makeNameValuePair(params);
    }

    public MrAPi(String uri, String method, List<NameValuePair> params, Context token) {
        this.response = "";
        this.status = 0;
        this.token = getToken(token);
        this.target = HOST + uri;
        this.method = method;
        this.params = params;
    }

    public MrAPi(String uri, String method, List<NameValuePair> params) {
        this.response = "";
        this.method = method;
        this.status = 0;
        this.token = "";
        this.target = HOST + uri;
        this.params = params;
    }

    public MrAPi(String uri, String method, Object params) {
        this.response = "";
        this.method = method;
        this.status = 0;
        this.token = "";
        this.target = HOST + uri;
        this.params = makeNameValuePair(params);
    }

    public MrAPi(String uri, String method, HashMap<String, String> params, Context token) {
        this.response = "";
        this.status = 0;
        this.token = getToken(token);
        this.target = HOST + uri;
        this.method = method;
        this.params = makeNameValuePair(params);
    }

    public void getArrayList(MrOnTaskExecuteList onTaskExecuteList) {
        this.type = "list";
        this.onTaskExecuteList = onTaskExecuteList;
        this.execute();
    }

    public void getObject(MrOnTaskExecuteObject onTaskExecuteObject) {
        this.type = "object";
        this.onTaskExecuteObject = onTaskExecuteObject;
        this.execute();
    }

    public void getString(MrOnTaskExecute onTaskComplete) {
        this.type = "string";
        this.onTaskComplete = onTaskComplete;
        this.execute();
    }

    public void getJsonObject(MrOnTaskExecuteJsonObject mrOnTaskExecuteJsonObject) {
        this.type = "JsonObject";
        this.onTaskExecuteJsonObject = mrOnTaskExecuteJsonObject;
        this.execute();
    }

    public void getJsonArray(MrOnTaskExecuteJsonArray mrOnTaskExecuteJsonArray) {
        this.type = "JsonArray";
        this.onTaskExecuteJsonArray = mrOnTaskExecuteJsonArray;
        this.execute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            if (method.equals("post"))
                post_method();
            else
                get_method();
        } catch (Exception err) {
            Log.e("MrApi", "doInBackground", err);
            exception = err;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            switch (type) {
                case "object":
                    if (status == 200) {
                        Gson gson = new Gson();
                        this.onTaskExecuteObject.onTaskSuccess(gson.fromJson(response, getGenericInterfaceType(this.onTaskExecuteObject.getClass())));
                    } else
                        this.onTaskExecuteObject.onTaskFailure(status, response, exception);
                    break;
                case "list":
                    if (status == 200) {
                        this.onTaskExecuteList.onTaskSuccess(makeArrayList(getGenericInterfaceType(this.onTaskExecuteList.getClass()), new JSONArray(response)));
                    } else
                        this.onTaskExecuteList.onTaskFailure(status, response, exception);
                    break;
                case "JsonObject":
                    if (status == 200)
                        this.onTaskExecuteJsonObject.onTaskSuccess(new JSONObject(response));
                    else
                        this.onTaskExecuteJsonObject.onTaskFailure(status, response, exception);
                    break;
                case "JsonArray":
                    if (status == 200)
                        this.onTaskExecuteJsonArray.onTaskSuccess(new JSONArray(response));
                    else
                        this.onTaskExecuteJsonArray.onTaskFailure(status, response, exception);
                    break;
                default:
                    if (status == 200)
                        this.onTaskComplete.onTaskSuccess(response);
                    else
                        this.onTaskComplete.onTaskFailure(status, response, exception);
                    break;
            }
        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        }
    }

    private Class getGenericInterfaceType(Class clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        return (Class<?>) typeArguments[0];
    }

    private List<NameValuePair> makeNameValuePair(HashMap<String, String> input) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : input.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairs;
    }

    private List<NameValuePair> makeNameValuePair(Object input) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Field[] fields = input.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (!Arrays.asList(FILTER_COLUMNS).contains(field.getName())) {
                    nameValuePairs.add(new BasicNameValuePair(field.getName(), String.valueOf(field.get(input))));
                }
            } catch (Exception err) {
                Log.e("MrAPi", "makeNameValuePair", err);
            }
        }
        return nameValuePairs;
    }

    private void post_method() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        System.out.println("Api connect " + target);

        Thread t = new HandlerThread("Handler") {
            @Override
            public void run() {
                try {
                    CloseableHttpClient client = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost(target);
                    System.out.println("Api body " + params.toString());
                    httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    try {
                        if (token.length() > 0) {
                            httpPost.setHeader("Authorization", "Bearer " + token);
                            System.out.println("Api auth " + token);
                        }
                    } catch (Exception ignored) {
                    }
                    CloseableHttpResponse res = client.execute(httpPost);
                    HttpEntity entity = res.getEntity();
                    status = res.getStatusLine().getStatusCode();
                    response = EntityUtils.toString(entity, "utf-8");
                    System.out.println("Api response " + String.valueOf(status) + " : " + response);
                    client.close();
                } catch (Exception e) {
                    System.out.println("Api catch " + e.getMessage());
                    exception = e;
                }
                latch.countDown();
            }
        };
        t.start();
        latch.await();
    }

    private void get_method() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        System.out.println("Api connect " + target);

        final Thread t = new HandlerThread("Handler") {
            @Override
            public void run() {
                try {
                    CloseableHttpClient client = HttpClients.createDefault();
                    HttpGet httpPost = new HttpGet(target);
                    System.out.println("Api body " + params.toString());
                    httpPost.setHeader("Content-Type", "application/json");
                    try {
                        if (token.length() > 0) {
                            httpPost.setHeader("Authorization", "Bearer " + token);
                            System.out.println("Api auth " + token);
                        }
                    } catch (Exception ignored) {
                    }
                    CloseableHttpResponse res = client.execute(httpPost);
                    HttpEntity entity = res.getEntity();
                    status = res.getStatusLine().getStatusCode();
                    response = EntityUtils.toString(entity, "utf-8");
                    System.out.println("Api response " + String.valueOf(status) + " : " + response);
                    client.close();
                } catch (Exception e) {
                    System.out.println("Api catch " + e.getMessage());
                    exception = e;
                }
                latch.countDown();
            }
        };
        t.start();
        latch.await();
    }

    private String getToken(Context tokenContext) {
        try {
            MrSQl mrSQl = new MrSQl(tokenContext);
            if (mrSQl.exists(Config.class, "name", "token"))
                return ((Config) mrSQl.get(Config.class, "name", "token")).getValue();
            else
                return "";
        } catch (Exception ignored) {
            return "";
        }
    }

    private <T> List<T> makeArrayList(Class<T> clazz, JSONArray jsonArray) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Gson gson = new Gson();
            try {
                list.add(gson.fromJson(String.valueOf(jsonArray.get(i)), clazz));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public interface MrOnTaskExecute {
        void onTaskSuccess(String response);

        void onTaskFailure(Integer status, String response, Exception exception);
    }

    public interface MrOnTaskExecuteObject {
        void onTaskSuccess(Object object);

        void onTaskFailure(Integer status, String response, Exception exception);
    }

    public interface MrOnTaskExecuteList<T> {
        void onTaskSuccess(List<T> objects);

        void onTaskFailure(Integer status, String response, Exception exception);
    }

    public interface MrOnTaskExecuteJsonObject {
        void onTaskSuccess(JSONObject jsonObject) throws JSONException;

        void onTaskFailure(Integer status, String response, Exception exception);
    }

    public interface MrOnTaskExecuteJsonArray {
        void onTaskSuccess(JSONArray jsonArray);

        void onTaskFailure(Integer status, String response, Exception exception);
    }
}
