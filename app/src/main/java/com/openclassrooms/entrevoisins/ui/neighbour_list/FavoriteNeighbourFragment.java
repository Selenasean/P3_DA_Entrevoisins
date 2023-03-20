package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FavoriteNeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavNeighbours;
    private RecyclerView mRecyclerView;
    MyNeighbourRecyclerViewAdapter mAdapter;


    /**
     * Create and return a new instance
     * @return @{@link FavoriteNeighbourFragment}
     */
    public static FavoriteNeighbourFragment newInstance(){
        return (new FavoriteNeighbourFragment());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Link fragment and his layout
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();

        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initFavList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       initFavList();
    }


    /**
     * Init the List of favorite neighbours
     */
    private void initFavList(){
        Log.i("MAJ_LIST_FAV", "va chercher la liste des favoris");
        mFavNeighbours = mApiService.getFavoriteNeighbours();
        mAdapter = new MyNeighbourRecyclerViewAdapter(mFavNeighbours, new OnTrashClickListener() {

            @Override
            public void onDeleteClicked(long id) {
                mApiService.removeNeighbourFromFavorite(mApiService.getNeighbourById(id));

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
