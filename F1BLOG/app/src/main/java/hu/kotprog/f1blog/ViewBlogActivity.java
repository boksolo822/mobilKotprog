package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewBlogActivity extends AppCompatActivity {

    public static String title;
    public static String longText;
    public static String image;
    private TextView titleText;
    private ImageView titleImage;
    private TextView blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);
        titleText = findViewById(R.id.titleText);
        titleText.setText(title);
        titleImage = findViewById(R.id.blogImage);
        Glide.with(this).load(image).into(titleImage);
        blog = findViewById(R.id.blogText);
        blog.setText(longText);
        blog.setMovementMethod(new ScrollingMovementMethod());
    }
}