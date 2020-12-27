package helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import activity.CartActivity;
import activity.DetailsActivity1;
import app.AppConfig;
import app.AppController;
import info.androidhive.loginandregistration.R;

import static app.AppController.TAG;


public class RListAdapter extends RecyclerView.Adapter {
    private ArrayList<helper.shopprod> list;
    public Context context;
    private Activity activity;
    public int pos=0;
    public String shopproduct_id;
    public String user_id;
public storeAdapter storeAdapter;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;
    private static final int REQUEST_CODE = 101;
    public RListAdapter(Activity activity, Context context, ArrayList<helper.shopprod> list) {
        this.list = list;
        this.context = context;
        this.activity = activity;


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout topLayout;
        RelativeLayout bottomLayout;
        CardView card;
        TextView shopname;
        TextView price;
        TextView specialoffers;
        TextView distance;
        Button addcart;
        Button navigate;
        private SQLiteHandler db;
        //Button fav;

        Boolean up = false;


        ViewHolder(View v) {
            super(v);
            up = false;

            card = (CardView) v.findViewById(R.id.card);
            topLayout = (RelativeLayout) v.findViewById(R.id.top);
            bottomLayout = (RelativeLayout) v.findViewById(R.id.bottom);
            shopname = (TextView) v.findViewById(R.id.shopname);
            price = (TextView) v.findViewById(R.id.price);
           specialoffers = (TextView) v.findViewById(R.id.specialoffer);
            distance = (TextView) v.findViewById(R.id.distance);

            addcart = (Button) v.findViewById(R.id.addcart);
            navigate=(Button) v.findViewById(R.id.navigate);





           addcart.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(final View view) {

                                                pos = getAdapterPosition();


                                               db = new SQLiteHandler(view.getContext().getApplicationContext());
                                               System.out.println("db");
                                               HashMap<String, String> user = db.getUserDetails();
                                               System.out.println("hashmap");
                                               user_id = user.get("uid");
                                               System.out.println(user_id+"user_id");

                                               System.out.println("hi");

                                               String tag_string_req = "req_register";
                                                shopproduct_id=list.get(pos).shopproduct_id;
                                               System.out.println(shopproduct_id);

                                               StringRequest strReq = new StringRequest(Request.Method.POST,
                                                       AppConfig.URL_addcart, new Response.Listener<String>() {

                                                   @Override
                                                   public void onResponse(String response) {
                                                       Log.d(TAG, "Register Response: " + response.toString());
                                                       System.out.println("response");

                                                       try {
                                                          //  System.out.println("try");
                                                           JSONObject jObj = new JSONObject(response);
                                                           //System.out.println("json");
                                                           boolean error = jObj.getBoolean("error");

                                                           System.out.println("try");

                                                       } catch (JSONException e) {

                                                           Log.d(TAG, "catch" +e.getMessage()) ;
                                                           e.printStackTrace();
                                                       }

                                                   }
                                               }, new Response.ErrorListener() {

                                                   @Override
                                                   public void onErrorResponse(VolleyError error) {
                                                       Log.e(TAG, "Registration Error: " + error.getMessage());
                                                       Toast.makeText(view.getContext().getApplicationContext(),
                                                               error.getMessage(), Toast.LENGTH_LONG).show();
                                                       hideDialog();
                                                   }

                                                   private void hideDialog() {
                                                   }
                                               }) {

                                                   @Override
                                                   protected Map<String, String> getParams() {

                                                       // Posting params to register url
                                                       Map<String, String> params = new HashMap<>();
                                                       params.put("user_id", user_id);
                                                       params.put("shopproduct_id", shopproduct_id);

                                                       return params;
                                                   }

                                               };

                                               // Adding request to request queue
                                               AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

                                                //String pos=String.valueOf(pos);
                                               System.out.println(list.get(pos).price);
                                                System.out.println(pos+"pos0");
                                                Intent intent2=new Intent(view.getContext().getApplicationContext(), CartActivity.class);
                                                intent2.putExtra("pos",pos);
                                             Toast.makeText(view.getContext(), "Item is saved  ", Toast.LENGTH_SHORT).show();

                                           }
//
//
//
//                                           }
//
//                                           private void storeproduct( int id,String shopproduct_id) {
//
//                                           }
//
                                      });


           navigate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   LatLng start = null;
                   LatLng end = null;
                   try {
                       fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.getContext());
                       view.getContext();

                       //  LocationManager loc=(LocationManager)getSystemService(context.LOCATION_SERVICE);
                       // Location location=loc.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);\
                       double clong = DetailsActivity1.clong;
                       double clat = DetailsActivity1.clat;
                       start = new LatLng(clat, clong);
                       end = new LatLng(Double.parseDouble(list.get(pos).latitude), Double.parseDouble(list.get(pos).longitude));
                       int pos = getAdapterPosition();
                      // System.out.println(list.get(pos).latitude+list.get(pos).longitude+"hiiii");
                       double[] dest = new double[]{Double.parseDouble(list.get(pos).latitude), Double.parseDouble(list.get(pos).longitude)};
                       System.out.println("clong:"+clong);
                       System.out.println("clat;"+clat);
                       System.out.println(pos+"pos");
                       System.out.println(list.get(pos).latitude+"latitude");
                       double[] source = new double[]{clat, clong};
                       Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + Arrays.toString(source).replace("[", "(").replace("]", ")")
                               + "/" + Arrays.toString(dest).replace("[", "(").replace("]", ")"));
                       System.out.println("uri");
                       Intent in = new Intent(Intent.ACTION_VIEW, uri);
                       System.out.println("uri1");
                       in.setPackage("com.google.android.apps.maps");
                       System.out.println("uri2");
                       in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       System.out.println("uri3");
                       //context.startActivity(in);
                       view.getContext().startActivity(in);
                     //  context.startActivity(in);
                      // view.getContext().getApplicationContext().startActivity(in);
                       System.out.println("uri4");
                   } catch (ActivityNotFoundException e) {
                       System.out.println("hicatch");
                       Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps" + start + "/" + end);
                       Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       view.getContext().startActivity(intent);
                   }

               }
           });

          //  fav = (Button) v.findViewById(R.id.favorite);
if(card ==null){
    System.out.print("el card nulllllllllllllllllllllll");
}
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view) {



                    if (up) {
                        animate(150, 500, -.5f, 75);
                        card.animate().rotationY(15.0f).setDuration(500);
                        up = false;
                    } else {
                        up = true;
                        animate(-150, 500, .5f, -75);
                        card.animate().rotationY(0.0f).setDuration(500);
                    }
                }
            });
        }

        private void animate(int y, int duration, float scale, int bottomY) {
            topLayout.animate()
                    .translationYBy(y)
                    .setDuration(duration);
            bottomLayout.animate()
                    .scaleXBy(scale)
                    .scaleYBy(scale)
                    .translationYBy(bottomY)
                    .setDuration(duration);
        }

        public void resetCardPosition() {
            if (up) {
                animate(
                        150, 500, -.5f, 75);
                card.animate().rotationY(15.0f).setDuration(500);
                up = false;
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;


    System.out.println(list.get(position).name);



    viewHolder.shopname.setText("Shop : " + list.get(position).name);
    viewHolder.price.setText("Price" + list.get(position).price + " LE");
    viewHolder.specialoffers.setText("Special Offers: " + list.get(position).availablespecialoffers);
        viewHolder.distance.setText("Distance : " + list.get(position).distance+"Km");
       // viewHolder.addcart.setText("ADD To Cart");
//}





        viewHolder.resetCardPosition();
//        viewHolder.card.animate().rotationYBy(360).setDuration(1000).setInterpolator(new AccelerateInterpolator(.15f));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public shopprod getItem(int id){
        return list.get(id);
    }

    private void scaleImage(Bitmap bitmap, RelativeLayout view) throws NoSuchElementException {

        int width;
        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = Math.round((float)250 * context.getResources().getDisplayMetrics().density);

        float xScale =  (((float) bounding) /( height*6));
        float yScale =  (((float) bounding) / (width));
        float scale = (xScale <= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        view.setBackgroundDrawable(result);

    }
public   interface storeAdapter{
         void storeproduct();
}
}

