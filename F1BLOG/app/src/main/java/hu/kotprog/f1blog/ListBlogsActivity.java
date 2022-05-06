package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListBlogsActivity extends AppCompatActivity {

    private RecyclerView blogRecycler;
    private ArrayList<BlogItem> mItemList;
    private BlogItemAdapter mAdapter;
    private FirebaseFirestore mFireStore;
    private CollectionReference mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_blogs_recycle);

        blogRecycler = findViewById(R.id.blogRecycler);
        blogRecycler.setLayoutManager(new GridLayoutManager(this,1));
        mItemList = new ArrayList<>();
        mAdapter = new BlogItemAdapter(this, mItemList);
        blogRecycler.setAdapter(mAdapter);
        mFireStore = FirebaseFirestore.getInstance();
        mItems = mFireStore.collection("Blogs");
        queryData();
    }

    private void queryData() {
        mItemList.clear();

        Query whereQuery=mItems.orderBy("clicks");
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
        mItems.document(item._getId()).update("clicks", item.getClicks() + 1);

        Intent toView = new Intent(ListBlogsActivity.this, ViewBlogActivity.class);
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
            Intent tonewPost = new Intent(ListBlogsActivity.this, PostActivity.class);
            startActivity(tonewPost);
        }

        if(menuItem.getItemId()==R.id.myPosts){
         Intent tonewPost = new Intent(ListBlogsActivity.this, MyPosts.class);
         startActivity(tonewPost);
        }

        if(menuItem.getItemId()==R.id.logOut){
            MainActivity.logOut();
            Intent toLog = new Intent(ListBlogsActivity.this, LoginActivity.class);
            startActivity(toLog);
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryData();
    }
}