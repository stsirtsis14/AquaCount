package com.example.aquacount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aquacount.R;
import com.example.aquacount.models.CoordinateEntity;
import java.util.ArrayList;
import java.util.List;

public class CoordinateAdapter extends RecyclerView.Adapter<CoordinateAdapter.CoordinateViewHolder> {

    private final List<CoordinateEntity> coordinates = new ArrayList<>();

    @NonNull
    @Override
    public CoordinateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coordinate_item, parent, false);
        return new CoordinateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinateViewHolder holder, int position) {
        CoordinateEntity coordinate = coordinates.get(position);
        holder.latitudeTextView.setText("Latitude: " + coordinate.getLatitude());
        holder.longitudeTextView.setText("Longitude: " + coordinate.getLongitude());
    }

    @Override
    public int getItemCount() {
        return coordinates.size();
    }

    public void setCoordinates(List<CoordinateEntity> coordinates) {
        this.coordinates.clear();
        this.coordinates.addAll(coordinates);
        notifyDataSetChanged();
    }

    static class CoordinateViewHolder extends RecyclerView.ViewHolder {
        private final TextView latitudeTextView;
        private final TextView longitudeTextView;

        public CoordinateViewHolder(@NonNull View itemView) {
            super(itemView);
            latitudeTextView = itemView.findViewById(R.id.latitudeTextView);
            longitudeTextView = itemView.findViewById(R.id.longtitudeTextView);
        }
    }
}
