package com.example.pokedex3.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex3.R;
import com.example.pokedex3.activities.AbilityDetailsActivity;
import com.example.pokedex3.models.Ability;

import java.util.List;

public class AbilityAdapter extends RecyclerView.Adapter<AbilityAdapter.AbilityViewHolder> {

    private List<Ability> abilityList;

    public AbilityAdapter(List<Ability> abilityList) { this.abilityList = abilityList;}

    @NonNull
    @Override
    public AbilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_layout, parent, false);
        return new AbilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbilityViewHolder holder, int position) {
        Ability ability = abilityList.get(position);
        holder.bind(ability);
    }

    @Override
    public int getItemCount() { return abilityList.size();}

    public class AbilityViewHolder extends RecyclerView.ViewHolder {

        private TextView abilityNameTextView;

        public AbilityViewHolder(@NonNull View abilityView) {
            super(abilityView);
            abilityNameTextView = abilityView.findViewById(R.id.abilityNameTextView);

            abilityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Ability ability = abilityList.get(position);
                        Intent intent = AbilityDetailsActivity.newIntent(v.getContext(), ability);
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }

        public void bind(Ability ability) {
            abilityNameTextView.setText(ability.getName());

        }
    }
}