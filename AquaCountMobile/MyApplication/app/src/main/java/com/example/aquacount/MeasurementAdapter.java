package com.example.aquacount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aquacount.models.MeasurementUpdateEntity;
import java.util.List;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<MeasurementUpdateEntity> measurements;
    private static OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MeasurementUpdateEntity measurement);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setData(List<MeasurementUpdateEntity> measurements) {
        this.measurements = measurements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        return new MeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        MeasurementUpdateEntity measurement = measurements.get(position);
        holder.bind(measurement);
    }

    @Override
    public int getItemCount() {
        return measurements != null ? measurements.size() : 0;
    }

    static class MeasurementViewHolder extends RecyclerView.ViewHolder {

        private TextView counterIdTextView;
        private TextView routeIdTextView;
        private TextView clockIdTextView;
        private TextView valueTextView;
        private TextView timestampTextView;
        private TextView measurementIdTextView;

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);
            counterIdTextView = itemView.findViewById(R.id.counterIdTextView);
            routeIdTextView = itemView.findViewById(R.id.routeIdTextView);
            clockIdTextView = itemView.findViewById(R.id.clockIdTextView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            measurementIdTextView = itemView.findViewById(R.id.measurementIdTextView);
        }

        public void bind(MeasurementUpdateEntity measurement) {
            counterIdTextView.setText("Counter ID: " + measurement.getCounterid());
            routeIdTextView.setText("Route ID: " + measurement.getRouteid());
            clockIdTextView.setText("Clock ID: " + measurement.getClockid());
            valueTextView.setText("Value " + measurement.getValue());
            timestampTextView.setText("Time " + measurement.getTimestamp());
            measurementIdTextView.setText(("Measurement ID " + measurement.getMeasurementid()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(measurement);
                        }
                    }
                }
            });
        }
    }
}
