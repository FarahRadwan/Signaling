package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.ListAdaptershop;
import helper.RListAdapter;
import helper.SQLiteHandler;
import helper.SessionManager;
import helper.shopprod;
import info.androidhive.loginandregistration.R;

public class DetailsActivity1 extends AppCompatActivity implements Serializable, AdapterView.OnItemSelectedListener, RListAdapter.storeAdapter {
    private static final String TAG = DetailsActivity1.class.getSimpleName();

   // Context context;
   public Criteria criteria;
    public String bestProvider;
    String category;
    RecyclerView recyclerView;
    TextView title;
    Activity activity;
    public static double clong;
    public  static double clat;

//    public String value1=getIntent().getStringExtra("email");
    ListView SubjectListView;
    ProgressBar progressBarSubject;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private shopprod shopprod;
    public Context context;
    public Button addcart;

    public RListAdapter adapter;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    private static final int REQUEST_CODE = 101;



     public ArrayList<shopprod> shopList = new ArrayList<shopprod>();


    String s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        addcart=(Button) findViewById(R.id.addcart);

       // String title = actionBar.getTitle().toString(); // get the title
        actionBar.show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  final Spinner spin = findViewById(R.id.coursesspinner);
        //spin.setOnItemSelectedListener(this);
        setContentView(R.layout.activity_details1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        context = getApplicationContext();
        activity = this;
        category = getIntent().getStringExtra("category");

        this.context = context;


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        title = (TextView) findViewById(R.id.title);
        //builtTextView();

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

                    shopList= new ArrayList<shopprod>();
                    for (int i = 0; i < x.length(); i++) {
                        LocationManager loc=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        Location l=loc.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Location location=loc.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                        Location loc2=new Location(LocationManager.GPS_PROVIDER);
//                        boolean elocnull=false;
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            Log.d("loc", "not null locc: ");
//                        }
//if((LocationManager.GPS_PROVIDER).equals(null)){
  //   elocnull=true;
//}

                        criteria = new Criteria();
                       // bestProvider = String.valueOf(LocationManager.getBestProvider(criteria, true)).toString();

                      //  LocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER,true);
                       // System.out.print(loc.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude()+"el loccccccccccccccccc");
                         clong=location.getLongitude();
                         clat=location.getLatitude();
                        System.out.println(clong+"clong");
                        System.out.println(clat+"clat");
                        LatLng here=new LatLng(clat,clong);


                        shopprod=new shopprod();
                        jObj = x.getJSONObject(i);
                        //subjects s=new subjects(jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image_url"));
                        shopprod.shop_id = jObj.getString("shop_id");
                        System.out.println("shopid" + shopprod.shop_id);
                        shopprod.product_id = jObj.getString("product_id");
                        System.out.println("productid" + shopprod.product_id);
                        shopprod.price = jObj.getString("price");
                        shopprod.shopproduct_id=jObj.getString("shopproduct_id");
                        shopprod.latitude= jObj.getString("latitude");
                        shopprod.longitude= jObj.getString("longitude");
                       double dlat=Double.parseDouble(shopprod.latitude);
                        double dlong=Double.parseDouble(shopprod.longitude);

                        LatLng to=new LatLng(dlat,dlong);
                        double distance= SphericalUtil.computeDistanceBetween(here,to);

                        loc2.setLatitude(Double.parseDouble(shopprod.latitude));
                        loc2.setLatitude(Double.parseDouble(shopprod.longitude));


                        shopprod.availablespecialoffers = jObj.getString("availablespecialoffers");
                        shopprod.name = jObj.getString("name");
                        double dist=Math.sqrt(Math.pow((111.3*(Double.parseDouble(shopprod.latitude)-clat)),2)+Math.pow(71.5* (Double.parseDouble(shopprod.longitude)-clong),2));
                        //shopprod.distance= String.valueOf((location.distanceTo(loc2)/1000));
                       // shopprod.distance=  String.valueOf(distance/1000);
                        DecimalFormat df = new DecimalFormat("#.##"); String twoDigitNum = df.format(distance/1000);
                        shopprod.distance=  String.valueOf(twoDigitNum);
                        System.out.println("distance" + shopprod.distance);

                        System.out.println("avaliable Spec" + shopprod.availablespecialoffers);
                        System.out.println("price" + shopprod.price);
                        System.out.println("name" + shopprod.name);


                        shopList.add(shopprod);


                    }

                    for(int i=0;i<shopList.size();i++){
                        System.out.println(shopList.size());
                        System.out.println(shopList.get(i).name);
                        System.out.println(shopList.get(i).price);
                        System.out.println(shopList.get(i).product_id);
                        System.out.println(shopList.get(i).availablespecialoffers);
                    }


                    // System.out.println("shopl="+ shopList.size());

                    recyclerView.setAdapter(new helper.RListAdapter(activity, context, shopList));
                   // recyclerView.setOnClickListener();
                    recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
                    recyclerView.setHasFixedSize(true);

                }
                catch (JSONException e) {
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


        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);




        System.out.println("List size is "+shopList.size());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item= findViewById(R.id.actionsort);
        MenuItem itemcart= findViewById(R.id.action_addshop);

        // item.setEnabled(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.actionsort:
                showSortDialog();
                return true;
            case R.id.action_addshop:
                Intent i= new Intent(this,CartActivity.class);
                startActivity(i);
                finish();
                return true;}

        return true;


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
//        if (pDialog.isShowing())
//            pDialog.dismiss();
    }





    private void showSortDialog() {
        String [] options={"Price","Distance"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sort BY");
        builder.setIcon(R.drawable.ic_action_sort);
        builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    if (i==0){//price
                        Toast.makeText(getApplicationContext(),
                                "price", Toast.LENGTH_LONG)
                                .show();
                        Collections.sort(shopList, new Comparator<helper.shopprod>() {
                            @Override
                            public int compare(helper.shopprod t0, helper.shopprod t1) {
                                return  t0.price.compareTo(t1.price);


                            }

                        });
                        recyclerView.setAdapter(new helper.RListAdapter(activity, context, shopList));

                        recyclerView.setHasFixedSize(true);

                    }
                        if(i==1){//distance
                            Toast.makeText(getApplicationContext(),
                                    "distance", Toast.LENGTH_LONG)
                                    .show();
                            Collections.sort(shopList, new Comparator<helper.shopprod>() {
                                @Override
                                public int compare(helper.shopprod t0, helper.shopprod t1) {
                                    return  t0.distance.compareTo(t1.distance);
                                }
                            });
                            recyclerView.setAdapter(new helper.RListAdapter(activity, context, shopList));

                            recyclerView.setHasFixedSize(true);
                        }
                    }
             });

        Intent intent2=new Intent(this, CartActivity.class);
        intent2.putExtra("shopList",shopList);
        builder.create().show();
       // storeproduct();

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void storeproduct(){
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        int user_id = Integer.parseInt(user.get("id"));
        System.out.println("hi");
         String value=getIntent().getStringExtra("pos");
       // String value="1";
        final String value1=getIntent().getStringExtra("email");
        // final String value1="farah@gmail.com";
         System.out.println(value1+"mail");
        String tag_string_req = "req_register";

       final String shopproduct_id=shopList.get(Integer.parseInt(value)).shopproduct_id;
       System.out.println(shopproduct_id);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_addcart, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                System.out.println("response");

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    System.out.println("try");
                    if (!error) {
                       System.out.println("!error");
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String cart_id = jObj.getString("cart_id");
                        JSONObject cart = jObj.getJSONObject("cart");
                        String user_id = cart.getString("user_id");
                        String shopproduct_id = cart.getString("shopproduct_id");




                        // Inserting row in users table
                     //   db.addcart(Integer.parseInt(user_id),shopproduct_id);

                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

                        // Launch login activity

                    } else {


                        // Error occurred in registration. Get the error
                        // message
                        System.out.println("else");
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    Log.d(TAG, "catch" +e.getMessage()) ;
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", value1);
                params.put("shopproduct_id", shopproduct_id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

        }

