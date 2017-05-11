package com.example.dell.myapplication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity  {
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button btn1 = (Button) findViewById(R.id.button1);
        // Perform action on click
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SearchData();
            }
        });

    }

    public void SearchData()
    {
        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        // editText1
        final EditText inputText = (EditText)findViewById(R.id.editText1);



        String url = "http://10.0.2.2:8089/test03.php"; //for localhost

        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("txtKeyword", inputText.getText().toString()));

        try {
            JSONArray data = new JSONArray(getJSONUrl(url,params));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("name", c.getString("name"));
                map.put("e-mail", c.getString("e-mail"));
                map.put("website", c.getString("website"));
                map.put("comment", c.getString("comment"));
                map.put("Sex", c.getString("Sex"));
                MyArrList.add(map);

            }


            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(MainActivity.this, MyArrList, R.layout.activity_column,
                    new String[] {"name", "website", "e-mail"}, new int[] {R.id.ColCustomerID, R.id.ColName, R.id.ColEmail});
            lisView1.setAdapter(sAdap);
   /*
            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
            // OnClick Item
            lisView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String strCustomerID = MyArrList.get(position).get("name")
                            .toString();
                    String sName = MyArrList.get(position).get("website")
                            .toString();
                    String strEmail = MyArrList.get(position).get("Email")
                            .toString();

                    viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                    viewDetail.setTitle("Member Detail");
                    viewDetail.setMessage("MemberID : " + strCustomerID + "\n"
                            + "Name : " + sName + "\n"
                            + "strEmail : " + strEmail + "\n"
                            );
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    viewDetail.show();

                }
            });
*/

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public String getJSONUrl(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download file..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}