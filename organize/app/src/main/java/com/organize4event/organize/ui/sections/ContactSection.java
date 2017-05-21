package com.organize4event.organize.ui.sections;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.organize4event.organize.R;
import com.organize4event.organize.listeners.SectionListener;
import com.organize4event.organize.models.Contact;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class ContactSection extends StatelessSection {

    private Context context;
    private ArrayList<Contact> items = new ArrayList<>();
    private SectionListener listener;

    public ContactSection(Context context, SectionListener listener) {
        super(R.layout.header_contact, R.layout.item_contact);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getContentItemsTotal() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ContactViewHolder itemHolder = (ContactViewHolder) holder;
        final Contact contact = items.get(position);

        itemHolder.txtDescription.setText(contact.getDescription());
        itemHolder.txtContact.setText(contact.getContact());

        itemHolder.rowContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(contact);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new ContactViewHolderSection(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        ContactViewHolderSection itemViewHolderSection = (ContactViewHolderSection) holder;
        Contact contact = items.get(0);
        itemViewHolderSection.txtType.setText(contact.getContact_type().getName());
    }

    public void addItem(Contact contact) {
        this.items.add(contact);
    }

    public static class ContactViewHolderSection extends RecyclerView.ViewHolder {

        @Bind(R.id.txtType)
        TextView txtType;

        public ContactViewHolderSection(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rowContent)
        RelativeLayout rowContent;

        @Bind(R.id.txtDescription)
        TextView txtDescription;

        @Bind(R.id.txtContact)
        TextView txtContact;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
