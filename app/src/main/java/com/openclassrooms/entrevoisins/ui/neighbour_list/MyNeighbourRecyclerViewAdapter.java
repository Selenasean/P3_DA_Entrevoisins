package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private List<Neighbour> mNeighbours; // declare Neighbours List
    public static final String NEIGHBOUR_ID = "NEIGHBOUR_ID";
    private OnTrashClickListener mListener;
    private int inWhichFragment;

    /**
     * Constructor
     */
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, int inWhichfragment,  OnTrashClickListener listener) {
        mNeighbours = items;
        inWhichFragment = inWhichfragment;
        mListener = listener;

    }

    /**
     * ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view, final OnTrashClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_neighbour, parent, false);
            ViewHolder neighbourViewHolder = new ViewHolder(view, mListener);
            return neighbourViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);

        //display elements of neighbour
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        // onClick listener on items clicked of the recycleView to go to detail page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = holder.itemView.getContext();
                Neighbour clickedNeighbour = neighbour;
                launchDetailNeighbourActivity(clickedNeighbour, context);
            }

        });

        //onClick listener on delete button to delete neighbour using his id
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionBindAdapter = holder.getBindingAdapterPosition();
                if (positionBindAdapter != RecyclerView.NO_POSITION) {
                    Neighbour neighbour = mNeighbours.get(positionBindAdapter);
                    long mNeighbourId = neighbour.getId();
                    mListener.onDeleteClicked(mNeighbourId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    /**
     * Start Detail Activity
     * @param neighbour
     * @param context
     */
    public void launchDetailNeighbourActivity(Neighbour neighbour, Context context) {
        final Context myContext = context;
        Intent intent = new Intent(myContext, DetailNeighbourActivity.class);
        long mNeighbourId = neighbour.getId();
        intent.putExtra(NEIGHBOUR_ID, mNeighbourId);
        context.startActivity(intent);
    }

    /**
     * Method to update neighbours lists after a change
     * @param neighboursList
     */
    public void update(List<Neighbour> neighboursList) {
        mNeighbours = neighboursList;
        notifyDataSetChanged();
    }
}
