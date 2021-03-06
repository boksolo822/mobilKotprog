package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyPosts extends AppCompatActivity {

    private RecyclerView blogRecycler;
    private ArrayList<BlogItem> mItemList;
    private MyPostsAdapter mAdapter;
    private FirebaseFirestore mFireStore;
    private CollectionReference mItems;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userEmail = user.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        blogRecycler = findViewById(R.id.blogRecycler);
        blogRecycler.setLayoutManager(new GridLayoutManager(this,1));
        mItemList = new ArrayList<>();
        mAdapter = new MyPostsAdapter(this, mItemList);
        blogRecycler.setAdapter(mAdapter);
        mFireStore = FirebaseFirestore.getInstance();
        mItems = mFireStore.collection("Blogs");
        queryData();
    }

    private void queryData() {
        mItemList.clear();
        Query whereQuery=mItems.whereEqualTo("userEmail",userEmail);
        whereQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                BlogItem item = document.toObject(BlogItem.class);
                item.setId(document.getId());
                mItemList.add(item);
            }
            mAdapter.notifyDataSetChanged();
        });
    }


    public void toRead(BlogItem item) {
        ViewBlogActivity.title = item.getTitle();
        ViewBlogActivity.longText = item.getLongerText();
        ViewBlogActivity.image = item.getImage();
        Intent toView = new Intent(MyPosts.this, ViewBlogActivity.class);
        startActivity(toView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.newPost) {
            Intent tonewPost = new Intent(MyPosts.this, PostActivity.class);
            startActivity(tonewPost);
        }

        if(menuItem.getItemId()==R.id.toStarter){
            Intent toList = new Intent(MyPosts.this, ListBlogsActivity.class);
            startActivity(toList);
        }

        if(menuItem.getItemId()==R.id.logOut){
            MainActivity.logOut();
            Intent toLog = new Intent(MyPosts.this, LoginActivity.class);
            startActivity(toLog);
        }
        return true;
    }

    public void deleteItem(BlogItem item) {
        DocumentReference ref = mItems.document(item._getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                })
                .addOnFailureListener(fail -> {
                });

        queryData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryData();
    }
}
