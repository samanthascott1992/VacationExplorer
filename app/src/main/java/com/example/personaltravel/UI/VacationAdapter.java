package com.example.personaltravel.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltravel.R;
import com.example.personaltravel.entities.Excursion;
import com.example.personaltravel.entities.Vacation;

import java.util.ArrayList;
import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;
    private List<Excursion> allExcursions = new ArrayList<>();

    private boolean isSearching = false;

    public void setSearching(boolean searching) {
        this.isSearching = searching;
        notifyDataSetChanged();
    }

    public VacationAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        private final TextView excursionItemView;
        public VacationViewHolder(@NonNull View itemView) {

            super(itemView);
            vacationItemView=itemView.findViewById(R.id.textView2);
            excursionItemView=itemView.findViewById(R.id.excursionInfo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("title", current.getVacationName());
                    intent.putExtra("hotelName", current.getHotelName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }



    @NonNull
    @Override
    public VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item,parent,false);
        return new VacationViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull VacationViewHolder holder, int position) {
        if (mVacations != null) {
            Vacation current = mVacations.get(position);
            holder.vacationItemView.setText(current.getVacationName());

            // Build related excursion summary
            StringBuilder excursionSummary = new StringBuilder();
            for (Excursion excursion : allExcursions) {
                if (excursion.getVacationID() == current.getVacationID()) {
                    if (excursionSummary.length() > 0) excursionSummary.append(", ");
                    excursionSummary.append(excursion.getExcursionName());
                }
            }

            if (isSearching && excursionSummary.length() > 0) {
                holder.excursionItemView.setText("Includes: " + excursionSummary);
                holder.excursionItemView.setVisibility(View.VISIBLE);
            } else {
                holder.excursionItemView.setVisibility(View.GONE);
            }

        } else {
            holder.vacationItemView.setText("No Vacation Name");
            holder.excursionItemView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        if(mVacations != null){
        return mVacations.size();
        }
        else return 0;
    }

    public void setVacations(List<Vacation> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }

    public void setExcursions(List<Excursion> excursions){
        this.allExcursions = excursions;
        notifyDataSetChanged();
    }

    public List<Vacation> getDisplayedVacations(){
        return mVacations;
    }
}
