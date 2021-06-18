package com.example.calorieculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class snackList extends AppCompatActivity {

    String NameSearch;
    ImageView imageView;
    String CAL;
    String IMG_URL;
    String API_IMG_URL;
    CardView cardView;
    private RequestQueue queue;
    ListView listView;
    String[] itemName={"Aloo Tikki","Apple","Banana","Bhel Puri","Bread Pakoda","Burger","Chevda","Coffee","Cutlet","Dabel","Milk","Maysore Bond","Onion Pakoda","Orange","Pani Puri"};
    String[] quantity={"1 Peace","1 Normal Size Apple","1 Normal Size Banana","1 Katori","1 Bread Pakoda","1 Burger","1 Katori","1 Cup","1 Peace","1 Dabeli","1 Glass","1 Peace","50 Grams","Normal size 1 Orange","1 Peace"};
    String[] calories={"84","107","117","117","282","384","81","164","103","173","186","70","137","131","35"};
    int[] images={R.drawable.alootikki,R.drawable.apple,R.drawable.banana,R.drawable.bhelpuri,R.drawable.breadpakoda,R.drawable.burger,R.drawable.chevda,R.drawable.coffee,R.drawable.cutlet,R.drawable.dabeli,R.drawable.milk,R.drawable.mysorebonda,R.drawable.onionpakoda,R.drawable.orange,R.drawable.panipuri};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_list);

        queue = Volley.newRequestQueue(this);


        listView=findViewById(R.id.snackList);
        ProgramAdapter programAdapter=new ProgramAdapter(this,itemName,quantity,images,calories);
        listView.setAdapter(programAdapter);


        queue = Volley.newRequestQueue(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),fruitNames[i],Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),clickSnackList.class);
                intent.putExtra("name",itemName[i]);
                intent.putExtra("image",images[i]);
                intent.putExtra("quanitiy",quantity[i]);
                intent.putExtra("calories",calories[i]);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });


        cardView=findViewById(R.id.cardviewAPI);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent card = new Intent(getApplicationContext(),clickSnackList.class);
                card.putExtra("calories",CAL);
               // card.putExtra("image",Integer.parseInt(String.valueOf(imageView)));
                card.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(card);
                finish();


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.example_menu,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)menu.findItem(R.id.action_search).getActionView();


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Iteam here..");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                queue.cancelAll(query);

                // first StringRequest: getting items searched
                query = query.replaceAll(" ", "%20");
                StringRequest stringRequest = searchNameStringRequest(query);
                stringRequest.setTag(query);
                requestImage();
                listView.setVisibility(ListView.INVISIBLE);

                // executing the request (adding to queue)
                queue.add(stringRequest);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchNameStringRequest(newText);
                return false;
            }
        });
        searchItem.getIcon().setVisible(false,false);
        return true;

    }





    public StringRequest searchNameStringRequest(String nameSearch) {

        final String URL_PREFIX = "https://api.edamam.com/api/food-database/v2/parser?";
        final String URL_INGR = "ingr=";
        final String APP_ID = "&app_id=8aab8b66";
        final String APP_KEY = "&app_key=d2671a015241662f4af0296062058a5d";
        String url = URL_PREFIX + URL_INGR + nameSearch + APP_ID + APP_KEY;

        TextView apical = findViewById(R.id.apiresult);


        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        try {

                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray ja_data = jsonObj.getJSONArray("parsed");
                            System.out.println("Lenght of Parsed ARRAY===" + ja_data.length());
                            JSONObject ja_main = ja_data.getJSONObject(0);
                            JSONObject ja_food = ja_main.getJSONObject("food");
                            JSONObject ja_nutrients = ja_food.getJSONObject("nutrients");
                            CAL = ja_nutrients.getString("ENERC_KCAL");
                            IMG_URL = ja_food.getString("image");
                            SharedPreferences sharedPref = getSharedPreferences("API_IMG", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("API_IMG",IMG_URL);
                            editor.apply();


                            apical.setVisibility(TextView.VISIBLE);
                            NameSearch = nameSearch;
                            NameSearch = NameSearch.replaceAll("%20", " ");
                            apical.setText(NameSearch);

                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(snackList.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(snackList.this, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
        System.out.println("IMG URL        ========="+IMG_URL);


        return sr;
    }

    private void requestImage(){
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        SharedPreferences prfs = getSharedPreferences("API_IMG", Context.MODE_PRIVATE);
        API_IMG_URL = prfs.getString("API_IMG",null);

        System.out.println(API_IMG_URL);
        ImageRequest imageRequest=new ImageRequest(API_IMG_URL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //setContentView(R.layout.activity_click_snack_list);
                        final LayoutInflater factory = getLayoutInflater();
                        final View API_image = factory.inflate(R.layout.activity_click_snack_list,null);
                        imageView=API_image.findViewById(R.id.snackListimage);
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(snackList.this,"Image not load",Toast.LENGTH_LONG).show();
                //imageView.setImageResource(R.drawable.ic_dinner);
            }
        });
        requestQueue.add(imageRequest);

    }


}