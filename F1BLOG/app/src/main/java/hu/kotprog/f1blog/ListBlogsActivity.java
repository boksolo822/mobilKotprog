package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class ListBlogsActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private RecyclerView blogRecycler;
    private ArrayList<BlogItem> mItemList;
    private BlogItemAdapter mAdapter;
    private int gridNumber = 1;

    private FirebaseFirestore mFireStore;
    private CollectionReference mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_blogs_recycle);


        blogRecycler = findViewById(R.id.blogRecycler);
        blogRecycler.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();
        mAdapter = new BlogItemAdapter(this, mItemList);
        blogRecycler.setAdapter(mAdapter);


        mFireStore = FirebaseFirestore.getInstance();
        mItems = mFireStore.collection("Blogs");
        queryData();


    }

    private void queryData() {
        mItemList.clear();

        mItems.orderBy("title").limit(50).get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                BlogItem item = document.toObject(BlogItem.class);
                mItemList.add(item);
            }

            if(mItemList.size()==0){
                initializeData();
                queryData();
            }
            mAdapter.notifyDataSetChanged();
        });

    }


    public void toRead(BlogItem item){
        ViewBlogActivity.title=item.getTitle();

        Intent toView=new Intent(ListBlogsActivity.this,ViewBlogActivity.class);
        startActivity(toView);

    }

    private void initializeData() {

        String[] titleList = getResources().getStringArray(R.array.blog_item_title);
        String[] longText = getResources().getStringArray(R.array.blog_item_longtext);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.blog_item_images);

        //

        for (int i = 0; i < titleList.length; i++) {

            mItems.add(new BlogItem(
                    titleList[i],
                    longText[i],
                    itemsImageResource.getResourceId(i, 0)));
        }

        itemsImageResource.recycle();
        //  mAdapter.notifyDataSetChanged();
    }
}