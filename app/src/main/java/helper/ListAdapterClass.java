package helper;

import android.content.Context;
import java.util.List;
import android.app.Activity;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.loginandregistration.R;

public class ListAdapterClass extends BaseAdapter {

    Context context;
    List<subjects> valueList;
    List<shopprod> valist;



    public ListAdapterClass(List<subjects> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
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
           // LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LayoutInflater layoutInfiater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

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
      return "Name: "+ valueList.get(position).subjectName+ "\n " +"\n "+
             "Description: "+ valueList.get(position).description+"\n ";
    }

}

class ViewItem
{
    TextView TextViewSubjectName;

}

