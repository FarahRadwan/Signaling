package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.HttpServicesClass;
import helper.ListAdapterClass;
import helper.ListAdaptershop;
import helper.SQLiteHandler;
import helper.SessionManager;
import helper.shopprod;
import helper.subjects;
import info.androidhive.loginandregistration.R;
import activity.ProductsActivity.*;

public class DetailsActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = ProductsActivity.class.getSimpleName();
    ListView SubjectListView;
    ProgressBar progressBarSubject;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private shopprod shopprod;
    public Context context;
    final public ArrayList<shopprod> shopList = new ArrayList<shopprod>();


    String s;
    String ServerURL = "http://192.168.1.8/android_login_api/shopproduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        SubjectListView = (ListView) findViewById(R.id.listview1);

        progressBarSubject = (ProgressBar) findViewById(R.id.progressBar);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        this.context = context;
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_shopproduct, new Response.Listener<String>() {


            private Context context;

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "shopproduct Response: " + response.toString());
                hideDialog();


                try {

                    System.out.println("1");
                    JSONArray x = new JSONArray(response);
                    JSONObject jObj;
                    System.out.println("2");
                    // boolean error = jObj.getBoolean("error");
                    System.out.println("3");

                    //ArrayList<helper.shopprod> shopList = new ArrayList<shopprod>();
                    System.out.println("4");
                    System.out.println("jsonarray" + shopList);


                    for (int i = 0; i < x.length(); i++) {


                        jObj = x.getJSONObject(i);
                        //subjects s=new subjects(jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image_url"));
                        shopprod.shop_id = jObj.getString("shop_id");
                        System.out.println("shopid" + shopprod.shop_id);
                        shopprod.product_id = jObj.getString("product_id");
                        System.out.println("productid" + shopprod.product_id);
                        shopprod.price = jObj.getString("price");
                        shopprod.availablespecialoffers = jObj.getString("availablespecialoffers");
                        shopprod.name = jObj.getString("name");

                        System.out.println("avaliable Spec" + shopprod.availablespecialoffers);
                        System.out.println("price" + shopprod.price);
                        System.out.println("name" + shopprod.name);


                        shopList.add(shopprod);


                    }


                   // System.out.println("shopl="+ shopList.size());
                    System.out.println("list="+shopList.size());
                    List(shopList);





                    } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    System.out.println("Catch1");
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("catch2");
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", ProductsActivity.product_id);
                return params;
            }


        };

        // Adding request to request queue
        // RequestQueue rq= Volley.newRequestQueue(ProductsActivity.this);

        //rq.add(strReq);
        //ListAdaptershop adapter = new ListAdaptershop(shopList,context);

        //SubjectListView.setAdapter(adapter);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);



                       /* if(shopList.isEmpty()){
                            Toast.makeText(getApplicationContext(),
                                    "0", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "1", Toast.LENGTH_LONG).show();
                        }*/


                        /*Intent intent2=new Intent(ProductsActivity.this,
                                DetailsActivity.class);*/
        //System.out.println("List size is "+shopList.size());
        System.out.println("List size is "+shopList.size());

       //     ListAdaptershop adapter = new ListAdaptershop(shopList, context);
         //  SubjectListView.setAdapter(adapter);

//        System.out.println("shopl="+ shopList.size());
//        if (shopList != null) {
//
//
//            ListAdaptershop adapter = new ListAdaptershop(shopList, context);
//            SubjectListView.setAdapter(adapter);
//        }

        }
        private void List(ArrayList<helper.shopprod>shopList){
           // progressBarSubject.setVisibility(View.GONE);

            //SubjectListView.setVisibility(View.VISIBLE);
            if (shopList != null) {
//
//
                ListAdaptershop adapter = new ListAdaptershop(shopList,context);
                SubjectListView.setAdapter(adapter);
            }

    }
private void showDialog() {
        if (!pDialog.isShowing())
        pDialog.show();
        }
public void addList(shopprod shopprod){
        shopList.add(shopprod);
        }

private void hideDialog() {
        if (pDialog.isShowing())
        pDialog.dismiss();
        }



    }
