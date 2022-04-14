package hu.kotprog.f1blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    private EditText title;
    private EditText longText;
    private ImageView blogImage;
    private Button postButton;
    private Uri pickedImgUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        title = findViewById(R.id.postTitle);
        longText = findViewById(R.id.postLong);
        blogImage = findViewById(R.id.blogImage);
        postButton = findViewById(R.id.postButton);

        setupPopupImageClick();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("blog_images");

                final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());


                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageDownlaodLink = uri.toString();
                                // create post Object
                                BlogItem blog = new BlogItem(title.getText().toString(),
                                        longText.getText().toString(),
                                        imageDownlaodLink);

                                // Add post to firebase database

                                addPost(blog);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // something goes wrong uploading picture

                                System.out.println("BAJVA");

//                                showMessage(e.getMessage());
//                                popupClickProgress.setVisibility(View.INVISIBLE);
//                                popupAddBtn.setVisibility(View.VISIBLE);


                            }
                        });


                    }
                });


            }
        });






    }

    private void addPost(BlogItem item) {

        System.out.println("Megpróbálom addolni.");



        FirebaseFirestore mFireStore;
        CollectionReference mItems;
        mFireStore = FirebaseFirestore.getInstance();
        mItems = mFireStore.collection("Blogs");

        mItems.add(item);

        Intent toList=new Intent(PostActivity.this,ListBlogsActivity.class);
        startActivity(toList);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }





    private void setupPopupImageClick() {


       blogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Érzékelte");
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...

                checkAndRequestForPermission();

            }
        });



    }






    private void checkAndRequestForPermission() {



        if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(PostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(PostActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {

                ActivityCompat.requestPermissions(PostActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else

        System.out.println("Galériát kéne megnyitni");
            openGallery();
    }




    private void openGallery() {

        System.out.println("Ez mégis micsoda?");
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            blogImage.setImageURI(pickedImgUri);

        }


    }










}