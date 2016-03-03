package io.eldon.representapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by eldon on 3/1/2016.
 * Translates from CongressPerson to a ViewHolder
 */
public class CongressPersonAdapter extends RecyclerView.Adapter<CongressPersonAdapter.CongressPersonViewHolder>{
    List<CongressPerson> persons;

    public static class CongressPersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personWebsite;
        TextView personEmail;
        TextView personLastTweet;
        ImageView personPhoto;

        CongressPersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.congressperson_cardview);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personWebsite = (TextView)itemView.findViewById(R.id.person_website);
            personEmail = (TextView)itemView.findViewById(R.id.person_email);
            personLastTweet = (TextView)itemView.findViewById(R.id.person_last_tweet);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    CongressPersonAdapter(List<CongressPerson> persons){
        this.persons = persons;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public CongressPersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.congressperson_card, viewGroup, false);
        CongressPersonViewHolder pvh = new CongressPersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CongressPersonViewHolder congressPersonViewHolder, int i) {
        congressPersonViewHolder.personName.setText(persons.get(i).getName());
        congressPersonViewHolder.personWebsite.setText(persons.get(i).getWebsite());
        congressPersonViewHolder.personEmail.setText(persons.get(i).getEmail());
        congressPersonViewHolder.personLastTweet.setText(persons.get(i).getTruncatedLastTweet());
        congressPersonViewHolder.personPhoto.setImageResource(persons.get(i).getPhotoID());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}