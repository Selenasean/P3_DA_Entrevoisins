package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

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
     */
    @Override
    public void deleteNeighbour(long id ) {
        for (Neighbour neighbour : neighbours) {
            if(neighbour.getId() == id){
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
     */
    @Override
    public void addNeighbourToFavorite(Neighbour neighbour){
        neighbour.setFavorite(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNeighbourFromFavorite(Neighbour neighbour){
        neighbour.setFavorite(false);
    }


}
