package com.example.covidhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{
    private Context context;
    private List<Shop> items;
    private List<String> Uid;
    private DisplayStoresActivity display_activity;
    private RequestQueue request_queue;

    public ShopAdapter(DisplayStoresActivity display_activity,
                       Context context,
                       List<Shop> items,
                       List<String> Uid) {
        this.context = context;
        this.items = items;
        this.Uid = Uid;
        this.display_activity = display_activity;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_item, parent, false);

        ShopAdapter.ViewHolder view_holder = new ShopAdapter.ViewHolder(view);

        view_holder.setOnClickListener(new ShopAdapter.ViewHolder.ClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(display_activity);
                String[] options = {"Update", "Delete", "See More Details"};

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //update
                            String id = Uid.get(position).toString();
                            String name = items.get(position).getName();
                            String address = items.get(position).getAddress();
                            String phone = items.get(position).getPhoneNumber();
                            String email = items.get(position).getEmail();

                            Intent intent = new Intent(display_activity, ModifyUser.class);

                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("address", address);
                            intent.putExtra("email", email);
                            intent.putExtra("phone", phone);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }

                        if (which == 1) {
                            //delete user
                            JSONObject data = new JSONObject();
                            try {
                                data.put("userEmail", items.get(position).getEmail().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Submit(data);
                            FirebaseDatabase.getInstance().getReference("Stores")
                                    .child(Uid.get(position)).removeValue();
                        }

                        if (which == 2) {
                            //see more details
                            String name = items.get(position).getName();
                            String address = items.get(position).getAddress();
                            String phone = items.get(position).getPhoneNumber();
                            String email = items.get(position).getEmail();

                            Intent intent = new Intent(display_activity, SeeUserDetails.class);

                            intent.putExtra("name", name);
                            intent.putExtra("address", address);
                            intent.putExtra("email", email);
                            intent.putExtra("phone", phone);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }
                    }
                }).create().show();
            }
        });

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        final Shop shop = items.get(position);
//        holder.id.setText(String.valueOf(position + 1));
//        holder.email.setText(user.getEmail());
//        holder.address.setText(user.getAddress());
        holder.name.setText(shop.getName());
        holder.phone.setText(shop.getPhoneNumber());
        holder.img.setImageResource(R.drawable.ic_baseline_storefront_white_54);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, phone;
        private ImageView img;
        private ShopAdapter.ViewHolder.ClickListener m_click_listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_users);
            //  id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
//            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
//            address = itemView.findViewById(R.id.address);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    m_click_listener.onItemLongClick(v, getAdapterPosition());
                    return false;
                }
            });
        }

        public void setOnClickListener(ShopAdapter.ViewHolder.ClickListener clickListener) {
            m_click_listener = clickListener;
        }

        public interface ClickListener {
            void onItemLongClick(View view, int position);
        }
    }

    private void Submit(JSONObject data) {
        final JSONObject saved_data = data;
        String URL = "https://us-central1-covidhelper-680e7.cloudfunctions.net/deleteUserByEmail";

        request_queue = Volley.newRequestQueue(context.getApplicationContext());

        JsonObjectRequest json_req = new JsonObjectRequest(URL, data, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.d("POST deleteUserByEmail", response.toString());
                Toast.makeText(context.getApplicationContext(), "DELETED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error", error.getMessage());
            }
        }) {
            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        request_queue.add(json_req);
    }
}
