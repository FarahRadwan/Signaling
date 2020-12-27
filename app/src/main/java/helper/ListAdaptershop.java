package helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import info.androidhive.loginandregistration.R;

public class ListAdaptershop extends BaseAdapter {

    Context context;
    List<shopprod> valueList;
   // List<shopprod> valist;



    public ListAdaptershop(List<shopprod> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
        System.out.println("hi");
    }


    @Override
    public int getCount()
    {
        return this.valueList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();
            if(this.context ==null){
                System.out.print("context nulllll");
            }

            if(context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) ==null){
                System.out.print("activity nulllll");
                Log.d("null", "getView: ");
            }
            System.out.print("abl el nulll");

              LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            LayoutInflater layoutInfiater= (LayoutInflater) context
//                .getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.layout_items, null);

            viewItem.TextViewSubjectName = (TextView)convertView.findViewById(R.id.textView1);


            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }
        String s=toString(position);
        viewItem.TextViewSubjectName.setText(s);





        return convertView;
    }
    public  String toString( int position){
        return "Price: "+ valueList.get(position).price+ "\n " +"\n "+
                "Avaliable Special Offers: "+ valueList.get(position).availablespecialoffers+"\n ";
    }

}

class ViewItem1
{
    TextView TextViewSubjectName;

}

