package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.openclassrooms.entrevoisins.databinding.ActivityDetailNeighbourBinding;

public class DetailNeighbourActivity extends AppCompatActivity {

    private ActivityDetailNeighbourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailNeighbourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}
