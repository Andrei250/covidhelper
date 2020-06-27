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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.zip.Inflater;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<User> items;
    private List<String> Uid;
    private DisplayUsersActivity display_activity;

    public ItemAdapter(DisplayUsersActivity display_activity,Context context, List<User> items, List<String> Uid) {
        this.context = context;
        this.items = items;
        this.Uid = Uid;
        this.display_activity = display_activity;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_item, parent, false);

        ItemAdapter.ViewHolder view_holder = new ItemAdapter.ViewHolder(view);

        view_holder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(display_activity);
                String[] options = {"Update", "Delete"};

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
                            Intent intent = new Intent(display_activity, DisplayUsersActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Uid.get(position)).removeValue();
                            context.startActivity(intent);
                        }
                    }
                }).create().show();
            }
        });

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        final User user = items.get(position);
        holder.id.setText(String.valueOf(position + 1));
        holder.email.setText(user.getEmail());
        holder.address.setText(user.getAddress());
        holder.name.setText(user.getFullName());
        holder.phone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, name, email, phone, address;
        private ViewHolder.ClickListener m_click_listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    m_click_listener.onItemLongClick(v, getAdapterPosition());
                    return false;
                }
            });
        }

        public void setOnClickListener(ItemAdapter.ViewHolder.ClickListener clickListener) {
            m_click_listener = clickListener;
        }

        public interface ClickListener {
            void onItemLongClick(View view, int position);
        }
    }






}
