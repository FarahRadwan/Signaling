package helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import activity.CartActivity;
import app.AppConfig;
import app.AppController;
import info.androidhive.loginandregistration.R;

import static app.AppController.TAG;


public class RListadaptercart extends RecyclerView.Adapter {
    private ArrayList<helper.cartproduct> list;
    public Context context;
    private Activity activity;
    public int pos=0;
    public String shopproduct_id;
    public String user_id;
    public storeAdapter storeAdapter;
   public RListadaptercart Rlistadptercart;
    public RListadaptercart(Activity activity, Context context, ArrayList<helper.cartproduct> list) {
        this.list = list;
        this.context = context;
        this.activity = activity;


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout topLayout;
        RelativeLayout bottomLayout;
        CardView card;
        TextView shopname;
        TextView productname;
        Button remove;
        private SQLiteHandler db;
        //Button fav;

        Boolean up = false;


        ViewHolder(View v) {
            super(v);
            up = false;

            card = (CardView) v.findViewById(R.id.card2);
            topLayout = (RelativeLayout) v.findViewById(R.id.top);
            bottomLayout = (RelativeLayout) v.findViewById(R.id.bottom);
            shopname = (TextView) v.findViewById(R.id.shopname);
            productname = (TextView) v.findViewById(R.id.productname);
            remove = (Button) v.findViewById(R.id.remove);
            remove.setOnClickListener(new View.OnClickListener() {
                public void onClick( View view) {
                    //  db = new SQLiteHandler(view.getContext().getApplicationContext());
                    pos = getAdapterPosition();
                    System.out.println(pos+"pos");


                    String tag_string_req = "req_register";
               //     shopproduct_id=list.get(pos).shopproduct_id;
                    //System.out.println(shopproduct_id);

                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            AppConfig.URL_remove, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Register Response: " + response.toString());
                            System.out.println("response");

                            try {

                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");

                                System.out.println("try");
                } catch (JSONException e) {
                                System.out.println("catch");
                                Log.d(TAG, "catch" +e.getMessage()) ;
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Registration Error: " + error.getMessage());
//                            Toast.makeText(view.getContext().getApplicationContext(),
//                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            hideDialog();
                        }

                        private void hideDialog() {
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {

                            // Posting params to register url
                            Map<String, String> params = new HashMap<>();
                            params.put("cart_id", list.get(pos).cart_id);


                            return params;
                        }

                    };


                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
                    Intent i=new Intent(view.getContext().getApplicationContext(), CartActivity.class);
                    view.getContext().startActivity(i);



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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card2, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;


        //System.out.println(list.get(position).name);



        viewHolder.productname.setText("product : " + list.get(position).productname);
        viewHolder.shopname.setText("Shop:" + list.get(position).shopname );
       // viewHolder.notifyAll();





        viewHolder.resetCardPosition();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public cartproduct getItem(int id){
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

