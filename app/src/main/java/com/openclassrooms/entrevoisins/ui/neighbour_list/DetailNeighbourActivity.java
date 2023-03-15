package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static com.openclassrooms.entrevoisins.R.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityDetailNeighbourBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.Objects;

public class DetailNeighbourActivity extends AppCompatActivity {
    private ActivityDetailNeighbourBinding binding;
    String mNeighbourName;
    String mAvatarNeighbourURL;
    boolean mNeighbourIsFavorite;
    long mNeighbourId;
    public static  final String NEIGHBOUR_ID = "NEIGHBOUR_ID";
    NeighbourApiService mApiService;
    @NonNull Neighbour mNeighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailNeighbourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /** Get instance on NeighbourApiService */
        mApiService = DI.getNeighbourApiService();

        /** Get neighbour by Id from Intent*/
        Intent intent = getIntent();
        mNeighbourId = intent.getLongExtra(NEIGHBOUR_ID, 0);
        Log.i("SEL_NEIGHBOUR_ID", String.valueOf(mNeighbourId));
        Log.i("API_SERVICE", String.valueOf(mApiService));
        mNeighbour = Objects.requireNonNull(mApiService.getNeighbourById(mNeighbourId));
        Log.i("SEL_NEIGHBOUR_API", String.valueOf(mNeighbour));

        displayIsFavorite(mNeighbour);
        displayNeighbourDetail(mNeighbour, intent);
        onCLickFavoriteNeighbour(mNeighbour);
        onClickBackToMainActivity();
    }


    public void displayNeighbourDetail(Neighbour neighbour, Intent intent){
        if(intent != null){
            if(intent.hasExtra(NEIGHBOUR_ID)){
                mNeighbourName = neighbour.getName();
                mAvatarNeighbourURL = neighbour.getAvatarUrl();

                Glide.with(this).load(mAvatarNeighbourURL).into(binding.avatarNeighbourView);
                binding.neighbourSelectedName.setText(mNeighbourName);
                binding.neighbourNameDisplay.setText(mNeighbourName);
                binding.locationTextView.setText(neighbour.getAddress());
                binding.phoneTextView.setText(neighbour.getPhoneNumber());
                binding.webContact.setText("www.facebook.fr/" + mNeighbourName);
                binding.aboutMeDescription.setText(neighbour.getAboutMe());
            }
        }
    }

    public void displayIsFavorite(Neighbour neighbour){
        if(!neighbour.isFavorite()){
            binding.favoriteBtn.setImageResource(drawable.ic_star_border_black_24dp);
        }else{
            binding.favoriteBtn.setImageResource(drawable.ic_star_yellow_24dp);
        }
    }

    public void onCLickFavoriteNeighbour(Neighbour neighbour){
        binding.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!neighbour.isFavorite()){
                    binding.favoriteBtn.setImageResource(drawable.ic_star_yellow_24dp);

                // modify isFavorite to true using API
                    mApiService.addNeighbourToFavorite(neighbour);
                    // popup msg
                    Toast.makeText(DetailNeighbourActivity.this, "Vous avez ajouté " + mNeighbourName + " à vos voisins préférés !", Toast.LENGTH_SHORT).show();
                }else{
                    binding.favoriteBtn.setImageResource(drawable.ic_star_border_black_24dp);
                    // modify isFavorite to false using API
                    mApiService.removeNeighbourFromFavorite(neighbour);
                    // popup msg
                    Toast.makeText(DetailNeighbourActivity.this, "Vous avez retiré " + mNeighbourName + " de vos voisins préférés !", Toast.LENGTH_SHORT).show();
                }
                Log.i("SEL_DETAIL_ISFAV", String.valueOf(neighbour.isFavorite()));
            }
        });
    }

    public void onClickBackToMainActivity(){
        // onclick return btn
        binding.backToNeighbourList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }
}
