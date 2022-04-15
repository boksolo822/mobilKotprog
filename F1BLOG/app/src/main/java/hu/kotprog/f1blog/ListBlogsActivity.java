package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

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

            if (mItemList.size() == 0) {

                queryData();
            }
            mAdapter.notifyDataSetChanged();
        });
    }


   public void toRead(BlogItem item) {
        ViewBlogActivity.title = item.getTitle();
        ViewBlogActivity.longText = item.getLongerText();
        ViewBlogActivity.image = item.getImage();

       Intent toView = new Intent(ListBlogsActivity.this, ViewBlogActivity.class);
        startActivity(toView);

   }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.logged_menu, menu);
        MenuItem menuitem = menu.findItem(R.id.newPost);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.newPost) {
            Intent tonewPost = new Intent(ListBlogsActivity.this, PostActivity.class);
            startActivity(tonewPost);
        }


        return true;
    }
}