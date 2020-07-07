package com.example.covidhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ListUsersAdapter extends RecyclerView.Adapter<ListUsersAdapter.ViewHolder> {

    private Context context;
    private List<User> items;
    private List<String> Uid;
    private RequestQueue request_queue;

    public ListUsersAdapter(Context context, List<User> its, List<String> ids) {
        this.context = context;
        this.items = its;
        this.Uid = ids;
    }

    @NonNull
    @Override
    public ListUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.display_item, parent, false);
        ListUsersAdapter.ViewHolder v_holder = new ListUsersAdapter.ViewHolder(v);

        v_holder.setOnClickListener(new ListUsersAdapter.ViewHolder.ClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] options = {"Update", "Delete", "See More Details"};

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //update
                            String id = Uid.get(position).toString();
                            String name = items.get(position).getFullName();
                            String address = items.get(position).getAddress();
                            String phone = items.get(position).getPhone();
                            String email = items.get(position).getEmail();

                            Intent intent = new Intent(context, ModifyUser.class);

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
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Uid.get(position)).removeValue();
                        }

                        if (which == 2) {
                            //see more details
                            String name = items.get(position).getFullName();
                            String address = items.get(position).getAddress();
                            String phone = items.get(position).getPhone();
                            String email = items.get(position).getEmail();

                            Intent intent = new Intent(context, SeeUserDetails.class);

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

        return v_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = items.get(position);
        holder.name.setText(user.getFullName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ListUsersAdapter.ViewHolder.ClickListener m_click_listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    m_click_listener.onItemLongClick(v, getAdapterPosition());
                    return false;
                }
            });
        }

        public void setOnClickListener(ListUsersAdapter.ViewHolder.ClickListener clickListener) {
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
