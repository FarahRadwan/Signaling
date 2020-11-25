package helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import info.androidhive.loginandregistration.R;



public class RListAdapter extends RecyclerView.Adapter {
    private ArrayList<helper.shopprod> list;
    private Context context;
    private Activity activity;

    public RListAdapter(Activity activity, Context context, ArrayList<helper.shopprod> list) {
        this.list = list;
        this.context = context;
        this.activity = activity;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout topLayout;
        RelativeLayout bottomLayout;
        CardView card;
        TextView shopname;
        TextView price;
        TextView specialoffers;
        TextView distance;
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
            System.out.print("el card ");
          //  fav = (Button) v.findViewById(R.id.favorite);
if(card ==null){
    System.out.print("el card nulllllllllllllllllllllll");
}
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

}

