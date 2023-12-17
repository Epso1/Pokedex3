package com.example.pokedex3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AbilityViewModel extends ViewModel {

    private final AbilityRepository abilityRepository;
    private final MutableLiveData<List<Ability>> abilitiesLiveData = new MutableLiveData<>();

    private AbilityAdapter abilityAdapter;
    public AbilityViewModel() {
        this.abilityRepository = new AbilityRepository();
        this.abilityAdapter = new AbilityAdapter(abilitiesLiveData.getValue());
    }

    public LiveData<List<Ability>> getAbilities() {
        return abilitiesLiveData;
    }



    public void loadAbilities(int offset, int limit) {
        abilityRepository.loadAbilities(offset, limit, new AbilityRepository.Callback() {
            @Override
            public void onItemsLoaded(List<Ability> abilities) {
                if (abilitiesLiveData.getValue() != null) {
                    int startPosition = abilitiesLiveData.getValue().size();
                    abilitiesLiveData.getValue().addAll(abilities);
                    abilitiesLiveData.postValue(abilitiesLiveData.getValue());
                    abilityAdapter.notifyItemRangeInserted(startPosition, abilities.size());
                } else {
                    abilitiesLiveData.postValue(abilities);
                    abilityAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}