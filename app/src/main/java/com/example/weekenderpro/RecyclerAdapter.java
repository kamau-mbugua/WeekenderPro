package com.example.weekenderpro;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private static final String TAG = "hotelAdapter";
    private Context mContext;
    private List<Events> eventss;
    // private OnItemClickListener mListener;

    public RecyclerAdapter(Context context, List<Events> uploads) {
        mContext = context;
        eventss = uploads;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder:new View ...");
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_home, parent, false);
        RecyclerViewHolder hotelView = new RecyclerViewHolder(v);

        Log.d(TAG, "onCreateViewHolder: view created...");
        return hotelView;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: align to recycler...");
        final Events currentEvent = eventss.get(position);

       /* mratings, meventVenue, meventTitle, meventType, meventDate, meventTime,meventDescription,meventContacts,tvPrice;
        public ImageView meventImage;*/


        holder.meventVenue.setText("Venue :" + currentEvent.getEventVenue());
        holder.meventTitle.setText("Event Title :" + currentEvent.getEventTitle());
        holder.mratings.setText("Ratings :" + currentEvent.getRatings());
        holder.meventType.setText("EventType :" + currentEvent.getEventType());
        holder.tvPrice.setText("Price : KES."+" "+currentEvent.getEventPrice());
        holder.meventContacts.setText(currentEvent.getEventContacts());
        holder.meventDate.setText(currentEvent.getEventDate());
        holder.meventTime.setText(currentEvent.getEventTime());
        holder.meventDescription.setText(currentEvent.getEventDescription());


        Picasso.get()
                .load(currentEvent.getImageUri())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(holder.meventImage);


        holder.clickedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passIntent = new Intent(mContext, EventDetail.class);
                passIntent.putExtra("eventLocation1", currentEvent.getEventVenue());
                passIntent.putExtra("eventName1", currentEvent.getEventTitle());
                passIntent.putExtra("eventRating1", currentEvent.getRatings());
                passIntent.putExtra("eventType1", currentEvent.getEventType());
                passIntent.putExtra("imageUri1", currentEvent.getImageUri());
                passIntent.putExtra("eventPrice1", currentEvent.getEventPrice());
                passIntent.putExtra("eventContacts1", currentEvent.getEventContacts());
                passIntent.putExtra("eventDate1", currentEvent.getEventDate());
                passIntent.putExtra("evenTime1", currentEvent.getEventTime());
                passIntent.putExtra("eventDescription1", currentEvent.getEventDescription());


                mContext.startActivity(passIntent);
                Log.d(TAG, "onClick: detail view...");
            }
        });
        Log.d(TAG, "onBindViewHolder: done binding....");
    }

    @Override
    public int getItemCount() {
        //return hotels.size();

        Log.d(TAG, "getItemCount: Counting method...");
        if (eventss != null) {
            Log.d(TAG, "getItemCount: Done counting for list...");
            return eventss.size();
        }
        Log.d(TAG, "getItemCount: Done counting for non-list...");
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView mratings, meventVenue, meventTitle, meventType, meventDate, meventTime,meventDescription,meventContacts,tvPrice;
        public ImageView meventImage;
        CardView clickedLayout;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mratings = itemView.findViewById(R.id.ratings);
            meventVenue = itemView.findViewById(R.id.eventVenue);
            meventTitle = itemView.findViewById(R.id.eventTitle);
            meventType = itemView.findViewById(R.id.eventType);
            meventImage = itemView.findViewById(R.id.eventImage);
            clickedLayout = itemView.findViewById(R.id.eventCard);
            meventContacts = itemView.findViewById(R.id.eventContacts);
            meventDate= itemView.findViewById(R.id.eventDate);
            meventTime=itemView.findViewById(R.id.eventTime);
            meventDescription = itemView.findViewById(R.id.eventDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);



        }
    }
}