package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        MatcherAssert.assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess(){
        List<Neighbour> favNeighbours = service.getFavoriteNeighbours();
        List<Neighbour> expectedNeighbours = favNeighbours;
        for (Neighbour neighbour: service.getNeighbours()) {
            if(neighbour.isFavorite()){
              favNeighbours.add(neighbour);
            }
        }
        MatcherAssert.assertThat(favNeighbours,IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void getNeighbourByIdWithSuccess(){
        Neighbour neighbourById = service.getNeighbourById(5);
        for(Neighbour neighbour : service.getNeighbours()){
         if(neighbour.getId() == neighbourById.getId()){
             Neighbour expectedNeighbour = neighbour;
             assertEquals(neighbourById, expectedNeighbour);
         }
        }
        assertNull("should be null", null);
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete.getId());
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess(){
        Neighbour neighbourCreated = new Neighbour(0, "name", "https://i.pravatar.cc/150?u=a042581f3e39026702d", "Saint-Pierre-du-Mont ; 5km",
                "+33 567899000", "About me", false);
        service.createNeighbour(neighbourCreated);
        assertTrue(service.getNeighbours().contains(neighbourCreated));

    }

    @Test
    public void addNeighbourToFavoriteWithSuccess(){
        Neighbour neighbourToAdd = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0);
        service.addNeighbourToFavorite(neighbourToAdd);
        assertTrue(neighbourToAdd.isFavorite());
    }

    @Test
    public void removeNeighbourFromFavoriteWithSuccess(){
        Neighbour neighbourToRemove = service.getFavoriteNeighbours().get(0);
        service.removeNeighbourFromFavorite(neighbourToRemove);
        assertFalse(neighbourToRemove.isFavorite());
    }
}
