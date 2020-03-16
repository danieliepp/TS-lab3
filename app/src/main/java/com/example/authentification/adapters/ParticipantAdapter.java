package com.example.authentification.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authentification.R;
import com.example.authentification.data.model.Participant;

import java.util.ArrayList;
import java.util.List;


public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> implements  Filterable {
    private List<Participant> participants;
    private List<Participant> participantList;
    private ItemParticipantOnClickListener listener;
    private String searchString;

    public ParticipantAdapter(List<Participant> participantList, ItemParticipantOnClickListener listener) {
        this.participants = participantList;
        this.participantList = new ArrayList<>(participantList);
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            searchString = constraint.toString();
            List<Participant> filteredList = new ArrayList<>();

            if (searchString == null || searchString.length() == 0) {
                filteredList.addAll(participantList);
            } else {

                String filterPattern = searchString.toLowerCase();
                for (Participant item : participantList) {
                    if (item.getParticipantName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            participants = (List<Participant>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvParticipantName;
        private TextView tvParticipantBirthday;
        private TextView tvParticipantWeight;
        private TextView tvParticipantClubName;
        private TextView tvParticipantCoachName;

        private ConstraintLayout constraintLayout;

        ViewHolder(@NonNull View view, ItemParticipantOnClickListener listener) {
            super(view);
            tvParticipantName = view.findViewById(R.id.tvParticipantName);
            tvParticipantBirthday = view.findViewById(R.id.tvParticipantBirthday);
            tvParticipantWeight = view.findViewById(R.id.tvParticipantWeight);
            tvParticipantClubName = view.findViewById(R.id.tvParticipantClubName);
            tvParticipantCoachName = view.findViewById(R.id.tvParticipantCoachName);
            constraintLayout = view.findViewById(R.id.participant_constraint);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ItemOnClick(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_list_item, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Participant participant = participants.get(position);

        holder.tvParticipantName.setText(participant.getParticipantName());
        String[] strings = participant.getBirthday().toString().split("T");
        holder.tvParticipantBirthday.setText(strings[0]);
        holder.tvParticipantWeight.setText(participant.getWeight());
        holder.tvParticipantClubName.setText(participant.getClub());
        holder.tvParticipantCoachName.setText(participant.getCoachName());

    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public interface ItemParticipantOnClickListener {
        void ItemOnClick(int position);
        void DeleteButtonOnClick(int position);
    }
}

