package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private List<Neighbour> neighbours = new ArrayList<>();
    private Neighbour firstNeighbour = new Neighbour(1, "name1", "avatarURl", "adress", "phone number", "aboutMe", false);
    private Neighbour favoriteNeighbour = new Neighbour(2, "name1", "avatarURl", "adress", "phone number", "aboutMe", true);

    @Before
    public void setup() {
        neighbours.add(firstNeighbour);
        neighbours.add(favoriteNeighbour);
        service = DI.getNewInstanceApiService(neighbours);
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> expectedNeighbours = service.getNeighbours();
        MatcherAssert.assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> favNeighbours = service.getFavoriteNeighbours();
        Neighbour expectedFavNeighbours =  favoriteNeighbour;
        // check if fav list is an array
        MatcherAssert.assertThat(favNeighbours,IsIterableContainingInAnyOrder.containsInAnyOrder(expectedFavNeighbours));
    }

    @Test
    public void getNeighbourByIdWithSuccess() {
        Neighbour neighbourById = service.getNeighbourById(1);
        assertEquals(firstNeighbour, neighbourById);
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = firstNeighbour;
        service.deleteNeighbour(neighbourToDelete.getId());
        assertFalse(neighbours.contains(neighbourToDelete));
        assertTrue(neighbours.contains(favoriteNeighbour));
    }

    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbourCreated = new Neighbour(0, "name", "https://i.pravatar.cc/150?u=a042581f3e39026702d", "Saint-Pierre-du-Mont ; 5km",
                "+33 567899000", "About me", false);
        service.createNeighbour(neighbourCreated);
        assertTrue(neighbours.contains(neighbourCreated));
    }

    @Test
    public void addNeighbourToFavoriteWithSuccess() {
        // add a neighbour which isFavorite = false
        Neighbour neighbourToAdd = firstNeighbour;
        service.addNeighbourToFavorite(neighbourToAdd);
        List <Neighbour> favList = service.getFavoriteNeighbours();
        MatcherAssert.assertThat(favList, IsIterableContainingInAnyOrder.containsInAnyOrder(neighbourToAdd, favoriteNeighbour));
    }

    @Test
    public void removeNeighbourFromFavoriteWithSuccess() {
        // remove a neighbour which isFavorite = true
        Neighbour neighbourToRemove = favoriteNeighbour;
        service.removeNeighbourFromFavorite(neighbourToRemove.getId());
        assertFalse(neighbourToRemove.isFavorite());
        assertFalse(service.getFavoriteNeighbours().contains(neighbourToRemove));
    }
}
