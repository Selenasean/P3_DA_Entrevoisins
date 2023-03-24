package com.openclassrooms.entrevoisins.service;

import androidx.annotation.Nullable;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Get favorite neighbours List */
    List<Neighbour> getFavoriteNeighbours();

    /** Get one neighbour by his id
     * @return {@link Neighbour}
     * */
    @Nullable Neighbour getNeighbourById(long id);

    /**
     * Deletes a neighbour
     * @param id
     */
    void deleteNeighbour(long id);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /** Put neighbour in favorite List
     * @param neighbour
     * */
    void addNeighbourToFavorite(Neighbour neighbour);

    /** Remove neighbour from favorite List
     * @param id
     * */
    void removeNeighbourFromFavorite(long id);

}
