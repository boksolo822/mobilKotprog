package hu.kotprog.f1blog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyPostsAdapter extends RecyclerView.Adapter < MyPostsAdapter.ViewHolder > implements Filterable {

    private ArrayList<BlogItem> blogItemsData;
    private Context context;



    MyPostsAdapter(Context context, ArrayList < BlogItem > itemsData) {
        this.blogItemsData = itemsData;
        this.context = context;
    }


    @Override
    public Filter getFilter() {
        return null;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_myblogs, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsAdapter.ViewHolder holder, int position) {
        BlogItem currentItem = blogItemsData.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return blogItemsData.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textTitle);
            itemImage = itemView.findViewById(R.id.imageTitle);
        }

        public void bindTo(BlogItem currentItem) {
            titleText.setText(currentItem.getTitle());
            Glide.with(context).load(currentItem.getImage()).into(itemImage);
            itemView.findViewById(R.id.readButton).setOnClickListener(view -> ((ListBlogsActivity) context).toRead(currentItem));
        }
    }




}
