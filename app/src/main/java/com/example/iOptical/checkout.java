package com.example.iOptical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iOptical.Database.CartDataSource;
import com.example.eeshop.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class checkout extends AppCompatActivity {
    DatabaseReference ref;
    checkoutDB checkoutDB;
    private CartDataSource cartDataSource;
    MaterialEditText cardNumber ,mmyy,cvv,cardName,address;
    EditText test;
    TextView map;
//    int PLACE_PICKER_REQUEST = 1;
    //
    WifiManager wifiManager;
    private final static int PLACE_PICKER_REQUEST = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        checkoutDB = new checkoutDB();
        ref = FirebaseDatabase.getInstance().getReference().child("Orders");


        wifiManager= (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        map = (TextView) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                Intent intent;
//                try {
//                    intent = builder.build(checkout.this);
//                    startActivityForResult(intent , PLACE_PICKER_REQUEST);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }

                wifiManager.setWifiEnabled(false);
                openPlacePicker();

            }
        });

        FButton pay = (FButton) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNumber = (MaterialEditText)findViewById(R.id.cardNumber);
                mmyy = (MaterialEditText)findViewById(R.id.mmyy);
                cvv = (MaterialEditText)findViewById(R.id.cvv);
                cardName = (MaterialEditText) findViewById(R.id.cardName);
                address = (MaterialEditText)findViewById(R.id.address);

                ////////////////////
                String cardN , my , cv , cn , ad;

                cardN = cardNumber.getText().toString();
                my = mmyy.getText().toString();
                cv = cvv.getText().toString();
                cn = cardName.getText().toString();
                ad = address.getText().toString();

                //////////////////////////
                checkoutDB.setCardNumber(cardN.trim());
                checkoutDB.setCardName(cn.trim());
                checkoutDB.setMmyy(my.trim());
                checkoutDB.setCvv(cv.trim());
                checkoutDB.setAddress(ad.trim());

                ///////////////////////

                if (cardN.matches("") || cardN.length() <= 15) {
                    Toast.makeText(checkout.this, "الرجاء ادخل رقم البطاقة", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cn.matches("") && cn.length() <= 39) {
                    Toast.makeText(checkout.this, "الرجاء ادخل اسم صاحب البطاقة", Toast.LENGTH_SHORT).show();
                    return;
                }else if (my.matches("") || my.length() <= 3) {
                    Toast.makeText(checkout.this, "الرجاء ادخل تاريخ انتهاء البطاقة", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cv.matches("") || cv.length() <= 2) {
                    Toast.makeText(checkout.this, "الرجاء ادخل رقم السري الثلاثي", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ad.matches("")) {
                    Toast.makeText(checkout.this, "الرجاء ادخال عنوان المنزل", Toast.LENGTH_SHORT).show();
                    return;
                } else
                {


                    ref.push().setValue(checkoutDB);
                    AlertDialog alertDialog = new AlertDialog.Builder(checkout.this).create();
                    alertDialog.setTitle("Success !");
                    alertDialog.setMessage("تم ارسال طلبك بنجاح لقاعدة البيانات. سيتواصل المندوب معك قريبا");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "حسنا",


                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    cardNumber.setText("");
                                    mmyy.setText("");
                                    cvv.setText("");
                                    cardName.setText("");
                                    address.setText("");


                                    startActivity(new Intent(checkout.this, successPay.class));
                                }
                            });
                    alertDialog.show();

                }
                /////////////////////

//                ProgressDialog pd = new ProgressDialog(checkout.this);
//                pd.setMessage("الرجاء الانتظار");
//                pd.show();
//                pd.show();
//                pd.show();
//                ///////////////////
//
//                ////////////////////
//                startActivity(new Intent(checkout.this,successPay.class));

            }
        });

    }
//    protected void onActivityResult (int requestCode,int resultCode,Intent data) {
////        MaterialEditText add = (MaterialEditText) findViewById(R.id.address);
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, this);
////                String address = String.format("Place: %s", place.getAddress());
////                add.setText(address);
//            }
//        }
//    }

    private void openPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            //Enable Wifi
            wifiManager.setWifiEnabled(true);

        } catch (GooglePlayServicesRepairableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MaterialEditText add = (MaterialEditText) findViewById(R.id.address);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(checkout.this, data);

                    double latitude = place.getLatLng().latitude;
                    double longitude = place.getLatLng().longitude;
                    String PlaceLatLng = String.valueOf(latitude)+" , "+String.valueOf(longitude);
                    String addd = String.format("Place: %s", PlaceLatLng);
                    //
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        add.setText(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //


            }
        }
    }
}
