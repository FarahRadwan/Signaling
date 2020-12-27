package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.RListadaptercart;
import helper.SQLiteHandler;
import helper.SessionManager;
import helper.cartproduct;
import info.androidhive.loginandregistration.R;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = CartActivity.class.getSimpleName();
    private SQLiteHandler db;
    RecyclerView recyclerView;
    TextView title;
    Activity activity;
    //    public String value1=getIntent().getStringExtra("email");
    ListView SubjectListView;
    ProgressBar progressBarSubject;
    private ProgressDialog pDialog;
    private SessionManager session;

    private helper.cartproduct cartproduct;
    public Context context;
   // public Button addcart;

    public RListadaptercart adapter;

    private static final int REQUEST_CODE = 101;

   public String user_id;

    public ArrayList<cartproduct> cartList = new ArrayList<>();







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = new SQLiteHandler(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();

        user_id = user.get("uid");
        ActionBar actionBar = getSupportActionBar();

        actionBar.show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_cart);


        context = getApplicationContext();
        activity = this;

        this.context = context;


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        title = (TextView) findViewById(R.id.title);
        //builtTextView();

        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_saveditems, new Response.Listener<String>() {


            private Context context;

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "shopproduct Response: " + response.toString());
                hideDialog();

                JSONArray cx=null;
                try {
                    System.out.println("try11");
                     cx = new JSONArray(response);
                    System.out.println("response11");
                    JSONObject jObj;
                    cartList= new ArrayList<cartproduct>();
                    for (int i = 0; i < cx.length(); i++) {
                       System.out.println("for");
                        cartproduct=new cartproduct();
                        jObj = cx.getJSONObject(i);
                        System.out.println("jobj");
                        System.out.println(jObj.getString("productname"));
                        System.out.println(jObj.getString("name"));
                        cartproduct.productname = jObj.getString("productname");
                        cartproduct.cart_id=jObj.getString("cart_id");
                        System.out.println("j");

                        cartproduct.shopname = jObj.getString("name");
                        System.out.println("jf");


                        //subjects s=new subjects(jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image_url"));
                     //   cartproduct.cart_id = jObj.getString("cart_id");

                        cartList.add(cartproduct);
//                        System.out.println(cartList.get(i).cart_id+"hh");
//
//                        System.out.println(cartList.get(i).shopproduct_id+"shopname");

                    }

                    recyclerView.setAdapter(new helper.RListadaptercart(activity, context, cartList));
                    // recyclerView.setOnClickListener();
                    System.out.println("hi");
                    recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
                    recyclerView.setHasFixedSize(true);

                }
                catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    System.out.println("Catch1");
                   // Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("user_id", user_id);
                return params;
            }



        };


        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    private void hideDialog() {
    }
}