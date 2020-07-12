package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

// TODO deal with chaining

public class ShopAddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ShopAddProduct";

    private String name;
    private double quantity;
    private String unit;
    private double price;
    private String current_user_id;

    private HashMap <String, Product> product = new HashMap<>();

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add_product);

        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Stores");

        Button add = findViewById(R.id.idAddProdBtn);
        Button cancel = findViewById(R.id.idCancelAddProdBtn);
        Spinner spinner = findViewById(R.id.idShopSpinner);
        Toolbar toolbar = findViewById(R.id.idToolBarShopAddP);

        //  deals with the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.spinner_array, R.layout.spinner_design);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //  deals with the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add product");

        showSize();

        //  when add button is pressed
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        //  when cancel button is pressed
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel_intent = new Intent(getApplicationContext(), ShopUpdateStock.class);
                startActivity(cancel_intent);
            }
        });
    }

    private void addProduct() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EditText new_name = findViewById(R.id.idNewProduct);
                EditText new_quantity = findViewById(R.id.idProdQuantity);
                EditText new_price = findViewById(R.id.idProductPrice);

                boolean verification = verify(new_name.getText().toString(),
                        new_quantity.getText().toString(), unit, new_price.getText().toString());

                // if no field is empty then proceed
                if (verification) {
                    name = new_name.getText().toString().trim().toLowerCase();
                    price = Double.parseDouble(new_price.getText().toString().trim());
                    quantity = Double.parseDouble(new_quantity.getText().toString().trim());

                    product = (HashMap) snapshot.child("stock").getValue();

                    //  if the map si empty then create new map
                    //  else proceed with the adding
                    if (product == null) {
                        product = new HashMap<String, Product>();
                        addingOperation();
                    } else {
                        // verify if the product is already in stock
                        if (product.get(name) == null) {
                            addingOperation();
                        } else {
                            Toast.makeText(ShopAddProduct.this,
                                    "Product already in stock!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }

    // verify if there are empty fields
    private boolean verify(String name, String quantity, String unit, String price) {
        if (name.isEmpty()) {
            Toast.makeText(ShopAddProduct.this,
                    "Don't forget the name of the product!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (quantity.isEmpty()) {
            Toast.makeText(ShopAddProduct.this,
                    "Don't forget the quantity!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (price.isEmpty()) {
            Toast.makeText(ShopAddProduct.this,
                    "Don't forget the name of the product!", Toast.LENGTH_LONG).show();
            return false;
        }
//        if (unit.isEmpty()) {
//            Toast.makeText(ShopAddProduct.this,
//                    "Don't forget to select the unit!", Toast.LENGTH_LONG).show();
//                return false;
//        }
        return true;
    }

    //  operations that are done when adding in stock
    private void addingOperation() {
        product.put(name, new Product(name, quantity, unit, price));
        reference.child(current_user_id).child("stock").setValue(product);
        showSize();
    }

    //  method for TextView that shows the number of products in stock
    private void showSize() {
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView prod_stock = findViewById(R.id.idNumberProdStock);
                //  copy of the map from firebase
                Map<String, Product> map_size = new HashMap<>();
                map_size = (Map) snapshot.child("stock").getValue();
                //  if there are products in stock then create a new string with the text that's going to be shown
                //  else show that there are 0 products (done this to eliminate null pointer)
                if (map_size != null) {
                    String map_size_string = "There are " + map_size.size() + " products in stock";
                    prod_stock.setText(map_size_string);
                } else {
                    String map_size_string = "There are 0 products in stock";
                    prod_stock.setText(map_size_string);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                unit = "Kilograms";
                break;
            case 1:
                unit = "Grams";
                break;
            case 2:
                unit = "Pieces";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}