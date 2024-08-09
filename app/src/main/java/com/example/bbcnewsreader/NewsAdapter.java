package com.example.bbcnewsreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final Context context;
    private List<NewsItem> newsItems;
    private final OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context, List<NewsItem> newsItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.newsItems = newsItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItem newsItem = newsItems.get(position);
        holder.titleTextView.setText(newsItem.getTitle());
        holder.descriptionTextView.setText(newsItem.getDescription());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(newsItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateNewsItems(List<NewsItem> newNewsItems) {
        this.newsItems = newNewsItems;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}

