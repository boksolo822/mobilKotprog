package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListBlogsActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private RecyclerView blogRecycler;
    private ArrayList<BlogItem> mItemList;
    private BlogItemAdapter mAdapter;
    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_blogs_recycle);


        blogRecycler = findViewById(R.id.blogRecycler);
        blogRecycler.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();
        mAdapter = new BlogItemAdapter(this, mItemList);
        blogRecycler.setAdapter(mAdapter);
        initializeData();
    }

    private void initializeData() {
    }
}