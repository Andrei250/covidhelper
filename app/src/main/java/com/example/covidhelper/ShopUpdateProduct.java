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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ShopUpdateProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "ShopUpdateProduct";

    private String current_user_id;
    private String searched_name;
    private String old_name;
    private double old_quantity;
    private String unit;
    private double old_price;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_product);

        current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Stores");

        Toolbar toolbar = findViewById(R.id.idToolBarShopUpdateP);
        Button update = findViewById(R.id.idShopUpdateProductBtn);
        Button cancel = findViewById(R.id.idCancelUpdateProductBtn);
        Button search = findViewById(R.id.idShopSearchUpdatePBtn);
        Button delete = findViewById(R.id.idShopDeleteUP);
        Spinner spinner = findViewById(R.id.idShopSpinnerUP);

        //  deals with the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.spinner_array, R.layout.spinner_design);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        layoutVisibility(View.INVISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update products");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSearch();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel_intent = new Intent(getApplicationContext(), ShopUpdateStock.class);
                startActivity(cancel_intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    //  sets visibility of layout and lines
    void layoutVisibility(int visibility) {
        ConstraintLayout layout = findViewById(R.id.constraintLayout1UP);
        View line1 = findViewById(R.id.idShopLine1UP);
        View line2 = findViewById(R.id.idShopLine2UP);

        layout.setVisibility(visibility);
        line1.setVisibility(visibility);
        line2.setVisibility(visibility);
    }

    //  get the name of the product and call function getValues()
    private void productSearch() {
        EditText name = findViewById(R.id.idSearchedNameUP);
        searched_name = name.getText().toString().trim().toUpperCase();
        getValues();
    }

    //  gets the values of the fields of the searched products from database
    private void getValues() {
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //  get the values of the fields as Objects
                Object verification_name = snapshot.child("stock").child(searched_name)
                        .child("name").getValue();
                Object verification_quantity = snapshot.child("stock").child(searched_name)
                        .child("quantity").getValue();
                Object verification_price = snapshot.child("stock").child(searched_name)
                        .child("price").getValue();

                boolean value_verification = verification(verification_name,
                        verification_quantity, verification_price);

                //  if the verification is alright then make layout visible and
                //  update hints with the current values from firebase
                if (value_verification) {
                    layoutVisibility(View.VISIBLE);
                    updateHint(verification_name.toString(), verification_quantity.toString(),
                            verification_price.toString());
                    old_name = verification_name.toString();
                    old_quantity = Double.parseDouble(verification_quantity.toString());
                    old_price = Double.parseDouble(verification_price.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }


    void updateHint(String name_hint, String quantity_hint, String price_hint) {
        EditText name = findViewById(R.id.idNewProductNameUP);
        EditText quantity = findViewById(R.id.idNewQuantityUP);
        EditText price = findViewById(R.id.idNewPriceUP);
        name.setHint(name_hint);
        quantity.setHint(quantity_hint);
        price.setHint(price_hint);
    }

    private boolean verification(Object name, Object quantity, Object price) {
        if (name == null) {
            Toast.makeText(ShopUpdateProduct.this,
                    "Name could not be found!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (quantity == null) {
            Toast.makeText(ShopUpdateProduct.this,
                    "Quantity could not be found!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (price == null) {
            Toast.makeText(ShopUpdateProduct.this,
                    "Price could not be found!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void updateProduct() {
        EditText edit_new_name = findViewById(R.id.idNewProductNameUP);
        EditText edit_new_quantity = findViewById(R.id.idNewQuantityUP);
        EditText edit_new_price = findViewById(R.id.idNewPriceUP);

        final String new_name;
        final double new_quantity;
        final double new_price;

        if (!edit_new_name.getText().toString().isEmpty()) {
            new_name = edit_new_name.getText().toString().trim().toUpperCase();
        } else {
            new_name = old_name;
        }
        if (!edit_new_quantity.getText().toString().isEmpty()) {
            new_quantity = Double.parseDouble(edit_new_quantity.getText().toString().trim());
        } else {
            new_quantity = old_quantity;
        }
        if (!edit_new_price.getText().toString().isEmpty()) {
            new_price = Double.parseDouble(edit_new_price.getText().toString().trim());
        } else {
            new_price = old_price;
        }

        if (new_name.equals(old_name)) {
            reference.child(current_user_id).child("stock").child(searched_name).child("quantity").setValue(new_quantity);
            reference.child(current_user_id).child("stock").child(searched_name).child("price").setValue(new_price);
            reference.child(current_user_id).child("stock").child(searched_name).child("measureUnit").setValue(unit);
        } else {
            reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap map;
                    map = (HashMap) snapshot.child("stock").getValue();
                    map.put(new_name, new Product(new_name, new_quantity, unit, new_price));
                    map.remove(old_name);
                    reference.child(current_user_id).child("stock").setValue(map);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.v(TAG, error.getMessage());
                }
            });
        }
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

    private void deleteProduct () {
        reference.child(current_user_id).child("stock").child(old_name).removeValue();
    }
}