package com.iconium.webalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ConversionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static Spinner countriesSpinner;
    private static Button button;
    private static EditText inputEntry;

    private static String selectedCurrency;
    private static Double amountToConvert = 0.0;
    private static Double selectedCountryRate = 0.0;
    private static Double conversionRate = 0.0;
    private static TextView feedBackView;
    private static ImageView imageView;

    private static JSONObject arrayCountriesRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);


        // TODO: get the array that will sent to this activity through intent


        try {
            arrayCountriesRates = new JSONObject(getIntent().getStringExtra("JSON-ARRAY"));
        } catch (JSONException e) {

        }
        // TODO: convert array from jsonObject to JsonArray

        // TODO: initialized arraycountries here


        Spinner countriesSpinner = (Spinner) findViewById(R.id.countries);

        inputEntry = (EditText) findViewById(R.id.input_entry_for_conversion);

        feedBackView = (TextView) findViewById(R.id.feedback_view);



        String bitCoinType = getIntent().getStringExtra("Bitcoin").toString();
        if(bitCoinType.equals("ETH")){
            ImageView bitCoin = (ImageView) findViewById(R.id.imageView);
            bitCoin.setImageResource(R.drawable.eth);
        }
        if(bitCoinType.equals("BTC")){
            ImageView bitCoin = (ImageView) findViewById(R.id.imageView);
            bitCoin.setImageResource(R.drawable.btc);
        }



        Button conversionButton = (Button) findViewById(R.id.convert);

        conversionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double conversionResult = doConversion();
                feedBackView.setText(selectedCurrency + ": " + conversionResult.toString());
            }
        });


        // get the list of countries
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array,
                android.R.layout.simple_list_item_1);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

        // set spinner adapter
        countriesSpinner.setAdapter(arrayAdapter);
        countriesSpinner.setOnItemSelectedListener(this);

        // initialized selectedCountryRate
        initSelectedCountryRate();


    }


    @Override
    protected void onResume() {
        super.onResume();

            try {
                arrayCountriesRates = new JSONObject(getIntent().getStringExtra("JSON-ARRAY"));
            } catch (JSONException e) {

            }
    }




    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        selectedCurrency = parent.getItemAtPosition(pos).toString();

        conversionRate = (Double) arrayCountriesRates.opt(selectedCurrency);


        Toast newToast = Toast.makeText(this, parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG);
        newToast.show();


        Log.e("selected rate of " + selectedCurrency, conversionRate + "");



    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    private Double doConversion() {

        Double valueToConvert = Double.parseDouble(inputEntry.getText().toString());

        Double convertedValue = valueToConvert * conversionRate;
        Log.e("converted Value", convertedValue + "");
        return convertedValue;
    }

    public void initSelectedCountryRate() {
        if (selectedCountryRate == 0.0) {
            if (arrayCountriesRates.length() < 1) {
                selectedCountryRate = 0.0;
            }

            if (arrayCountriesRates.length() > 0) {
                if (selectedCurrency != null) {
                    selectedCountryRate = (Double) arrayCountriesRates.opt(selectedCurrency);
                }
            }
        }

    }

}
