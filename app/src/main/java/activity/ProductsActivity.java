package activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.AppConfig;
import helper.HttpServicesClass;
import helper.ListAdapterClass;
import helper.SQLiteHandler;
import helper.SessionManager;
import helper.shopprod;
import helper.subjects;
import info.androidhive.loginandregistration.R;

public class ProductsActivity extends AppCompatActivity {
    private static final String TAG = ProductsActivity.class.getSimpleName();
    ListView SubjectListView;
    ProgressBar progressBarSubject;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private shopprod shopprod = new shopprod();
    public ArrayList<shopprod> shopList = new ArrayList<shopprod>();
    public static String product_id;
    public static ArrayList<shopprod> finalshopList = new ArrayList<shopprod>();


    //new Intent(ProductsActivity.this,
    // DetailsActivity.class);
    String s;
   // String ServerURL = "http://192.168.43.169/android_login_api/product.php";
    String ServerURL = AppConfig.ServerURL;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products);

        SubjectListView = (ListView) findViewById(R.id.listview1);

        progressBarSubject = (ProgressBar) findViewById(R.id.progressBar);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        new GetHttpResponse(ProductsActivity.this).execute();
    }


    private class GetHttpResponse extends AsyncTask<Void, Void, Void> {
        public Context context;

        String ResultHolder;

        List<helper.subjects> subjectsList;
        // List<helper.shopprod> shopList;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try {
                httpServiceObject.ExecutePostRequest();

                if (httpServiceObject.getResponseCode() == 200) {
                    ResultHolder = httpServiceObject.getResponse();
                    System.out.println("resultholder=" + ResultHolder);

                    if (ResultHolder != null) {
                        JSONArray jsonArray = null;

                        try {

                            jsonArray = new JSONArray(ResultHolder);

                            JSONObject jsonObject;

                            subjects subjects;

                            subjectsList = new ArrayList<subjects>();
                            System.out.println("jsonarray" + subjectsList);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                subjects = new subjects();

                                jsonObject = jsonArray.getJSONObject(i);
                                //subjects s=new subjects(jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image_url"));
                                subjects.subjectName = jsonObject.getString("productname");
                                System.out.println("subj" + subjects.subjectName);
                                subjects.description = jsonObject.getString("description");
                                System.out.println("subj" + subjects.description);
                                subjects.image_url = jsonObject.getString("image_url");
                                subjects.id = Integer.parseInt(jsonObject.getString("product_id"));
                                System.out.println("id" + subjects.id);
                                System.out.println("subj" + subjects.image_url);


                                subjectsList.add(subjects);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("catch");
                        }
                    }
                } else {
                    Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBarSubject.setVisibility(View.GONE);

            SubjectListView.setVisibility(View.VISIBLE);

            if (subjectsList != null) {
                ListAdapterClass adapter = new ListAdapterClass(subjectsList, context);
                final Intent intent2 = new Intent(ProductsActivity.this, DetailsActivity1.class);
                SubjectListView.setAdapter(adapter);
                SubjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                        product_id = String.valueOf(subjectsList.get(i).id);
                        startActivity(intent2);

                    }
                });
            }
        }
    }
}