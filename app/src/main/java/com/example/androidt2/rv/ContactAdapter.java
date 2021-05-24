package com.example.androidt2.rv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidt2.AddActivity;
import com.example.androidt2.R;
import com.example.androidt2.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {

        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        TextView textNome = holder.itemView.findViewById(R.id.textNome);
        textNome.setText(contact.getNome());

        TextView textTelefone = holder.itemView.findViewById(R.id.textTelefone);
        textTelefone.setText(contact.getTelefone());

        TextView textObs = holder.itemView.findViewById(R.id.textObs);
        textObs.setText(contact.getObservacao());

        ImageView fotoView = holder.itemView.findViewById(R.id.fotoView);
        fotoView.setImageBitmap(contact.getFoto());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public ContactViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddActivity.class);
                context.startActivity(intent);
            });
        }
    }

}
