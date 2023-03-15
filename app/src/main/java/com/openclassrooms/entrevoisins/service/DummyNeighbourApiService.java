package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
}
