package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;
import helper.ListAdaptershop;
import helper.SQLiteHandler;
import helper.SessionManager;
import helper.shopprod;
import info.androidhive.loginandregistration.R;

public class DetailsActivity1 extends AppCompatActivity implements Serializable {
    private static final String TAG = ProductsActivity.class.getSimpleName();

   // Context context;
    String category;
    RecyclerView recyclerView;
    TextView title;
    Activity activity;

    ListView SubjectListView;
    ProgressBar progressBarSubject;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private shopprod shopprod;
    public Context context;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    private static final int REQUEST_CODE = 101;



     public ArrayList<shopprod> shopList = new ArrayList<shopprod>();


    String s;
    //String ServerURL = "http://192.168.43.169/android_login_api/shopproduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        //fetchLocation();

       // SubjectListView = (ListView) findViewById(R.id.listview1);

        //progressBarSubject = (ProgressBar) findViewById(R.id.progressBar);
        //pDialog = new ProgressDialog(this);
        //pDialog.setCancelable(false);
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
                        Location location=loc.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                        Location loc2=new Location(LocationManager.GPS_PROVIDER);
                        double clong=location.getLongitude();
                        double clat=location.getLatitude();
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
                    recyclerView.setHasFixedSize(true);
                    //recyclerView.setAdapter(new helper.MyRecyclerViewAdapter( getApplicationContext(), shopList));
                    //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    //recyclerView.setHasFixedSize(true);




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

    /*private void builtTextView() {

       // Point size = getDisplayDimensions();
       // int padding = size.x / 4;
       // recyclerView.setPadding(padding, 0, padding, 0);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
     //   recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int curr = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();

                for (int i = 0; i < shopList.size(); i++) {
                    if (i != curr) {
                        RListAdapter.ViewHolder viewHolder = ((RListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i));
                        if (viewHolder != null) {
                            viewHolder.resetCardPosition();
                        }
                    }
                }
            }
        });

    }*/

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



}
