package com.iconium.webalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static CustomRequestQueue customRequestQueue;

    private static Spinner countriesSpinner;
    private static Button button;
    private static EditText inputEntry;
    private static CardView ethCard;
    private static CardView btcCard;

    private static Double amountToConvert = 0.0;
    private static Double[] arrayCountriesRates = {};
    private static Double selectedCountryRate = 0.0;
    private static ArrayList<JSONObject> ethRates;
    private static JSONObject ethRatesJsonObject ;
    private static JSONObject btcRatesJsonObject ;
    private static ArrayList<JSONObject> btcRates;
    private static int size = 0;



    String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,AFN,USD,GHS,XAF,EUR,GEL,GNF,INR,JPY,KES,LAK,MYR,MXN,PEN,PHP,PLN,ZAR,XOF,TRY,TND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load api from the crypto currency site
        getConversionRates();


        ethCard = (CardView) findViewById(R.id.card_view_eth);
        ethCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(ethRatesJsonObject != null){
                size = ethRatesJsonObject.length();
            }

            if( ethRatesJsonObject == null){
                size = 0;
            }



                if(size > 0){
                    // TODO: open the conversion activity
                    Intent intent = new Intent(MainActivity.this, ConversionActivity.class);
                    intent.putExtra("Bitcoin", "ETH");
                    intent.putExtra("JSON-ARRAY", ethRatesJsonObject.toString());
                    startActivity(intent);
                }

                if(size < 1){
                    Toast toast = Toast.makeText(MainActivity.this, "Error moving current conversion rates to the next activity", Toast.LENGTH_LONG);
                    toast.show();
                }



            }
        });

        btcCard = (CardView) findViewById(R.id.card_view_btc);
        btcCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: open the conversion activity

                if(btcRatesJsonObject != null){
                    size = ethRatesJsonObject.length();
                }

                if( btcRatesJsonObject == null){
                    size = 0;
                }



                if(size > 0){
                    // TODO: open the conversion activity
                    Intent intent = new Intent(MainActivity.this, ConversionActivity.class);
                    intent.putExtra("Bitcoin", "BTC");
                    intent.putExtra("JSON-ARRAY", btcRatesJsonObject.toString());
                    startActivity(intent);
                }

                if(size < 1){
                    Toast toast = Toast.makeText(MainActivity.this, "Error fetching current conversion rates", Toast.LENGTH_LONG);
                    toast.show();
                }



            }
        });

        // TODO: add a loader here
        // display a loader while data is being transfarred from the site


    }


    /**
     * gets the convertion rates from the crpyto currency site
     */
    public int getConversionRates() {

        customRequestQueue = new CustomRequestQueue(this.getApplicationContext());


        //  mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  mTxtDisplay.setText("Response: " + response.toString());
                        Log.d("Data from Api call : ", response.toString());

                        // TODO: convert data from JSON to simple array
                        // convert the results from JSON objects to array;

                        try {
                            JSONArray jsonArrayNames = response.names();
                            JSONArray combinedArray = response.toJSONArray(jsonArrayNames);
                            ethRates = new ArrayList<JSONObject>();
                          //  JSONArray jsonObject = combinedArray.getJSONObject(0).toJSONArray();

                            ethRatesJsonObject = combinedArray.getJSONObject(0);
                           int miniSize = ethRatesJsonObject.length();
                            Log.e("SIZE: ",miniSize + "");
                            ethRates.add(combinedArray.getJSONObject(0));

//                            JSONObject[] placeHolderarray = new JSONObject[ethRates.size()];
//                            ethRates.toArray(placeHolderarray);


                            btcRates = new ArrayList<JSONObject>();
                            btcRatesJsonObject = combinedArray.getJSONObject(1);

                            btcRates.add(combinedArray.getJSONObject(1));


                            Log.e("eth -- array ",ethRates.toString());
                            Log.e("eth -- array ",ethRates.get(0).opt("NGN").toString());
                            Log.e("eth -- array ",combinedArray.getJSONObject(0).opt("NGN").toString());

                            for (int index = 0; index < combinedArray.length(); index++) {

                                String stringHolder = jsonArrayNames.getString(index) + "--" + combinedArray.getString(index);
                                        Log.e("Eth Data", stringHolder);

                            }

                        } catch (JSONException e) {
                            Log.d("Err converting", e.getMessage());

                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                        Log.d("Error with volley", error.getMessage());
                        Toast toast = Toast.makeText(MainActivity.this, "Could Not Fetch Data from Severs", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        customRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);

        // TODO: dismiss loader here
        // if loader is still visible make it invisible


        return 1;

    }
}
