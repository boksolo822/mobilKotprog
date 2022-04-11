package hu.kotprog.f1blog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewBlogActivity extends AppCompatActivity {

public static String title;
private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);

titleText=findViewById(R.id.titleText);
titleText.setText(title);


    }
}