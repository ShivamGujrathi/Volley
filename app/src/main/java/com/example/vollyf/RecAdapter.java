package com.example.vollyf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<info> contacts;

    public RecAdapter(Context ctx, List<info> contacts) {
        this.inflater = LayoutInflater.from(ctx);
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
