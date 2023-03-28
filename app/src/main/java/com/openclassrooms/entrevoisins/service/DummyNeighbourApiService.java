package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours;

    public DummyNeighbourApiService(List<Neighbour> neighbours){
      this.neighbours = neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
     return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favoriteList = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if(neighbour.isFavorite()){
               favoriteList.add(neighbour);
            }
        }
        return favoriteList;
    }

    /**
     * {@inheritDoc}
     * @param id
     */
    @Override
    public Neighbour getNeighbourById(long id){
        for (Neighbour neighbour : neighbours) {
            if(neighbour.getId() == id){
                return neighbour;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param id
     */
    @Override
    public void deleteNeighbour(long id) {
        for (Neighbour neighbour : neighbours) {
            if(neighbour.getId() == id){
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                neighbours.remove(neighbour);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void addNeighbourToFavorite(Neighbour neighbour){
        neighbour.setFavorite(true);
    }

    /**
     * {@inheritDoc}
     * @param id
     */
    @Override
    public void removeNeighbourFromFavorite(long id){
        Neighbour neighbour = getNeighbourById(id);
        if (neighbour != null) {
            neighbour.setFavorite(false);
        }
    }


}
