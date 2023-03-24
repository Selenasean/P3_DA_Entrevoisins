package com.openclassrooms.entrevoisins.ui.neighbour_list;

/**
 * Interface to deal with onclick on delete button
 */
public interface OnTrashClickListener {

    /**
     * OnClick on delete button, delete neighbour from the Neighbour List
     * @param id
    */
    void onDeleteClicked(long id);
}

