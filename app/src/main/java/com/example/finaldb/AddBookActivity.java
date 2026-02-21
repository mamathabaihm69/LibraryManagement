package com.example.finaldb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddBookActivity extends AppCompatActivity {

    private ImageView coverImg;
    private EditText etIsbn, etTitle, etAuthor, etCategory, etEdition, etTotal, etIssued, etDesc;
    private Uri imageUri;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mDatabase = FirebaseDatabase.getInstance().getReference("Books");
        mStorage = FirebaseStorage.getInstance().getReference("BookCovers");

        coverImg = findViewById(R.id.img_book_cover);
        etIsbn = findViewById(R.id.et_isbn);
        etTitle = findViewById(R.id.et_title);
        etAuthor = findViewById(R.id.et_author);
        etCategory = findViewById(R.id.et_category);
        etEdition = findViewById(R.id.et_edition);
        etTotal = findViewById(R.id.et_total);
        etIssued = findViewById(R.id.et_issued);
        etDesc = findViewById(R.id.et_desc);

        findViewById(R.id.btn_select_image).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        findViewById(R.id.btn_save_book).setOnClickListener(v -> uploadBook());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            coverImg.setImageURI(imageUri);
        }
    }

    private void uploadBook() {
        String isbn = etIsbn.getText().toString().trim();
        if (imageUri == null || isbn.isEmpty()) {
            Toast.makeText(this, "Select image and enter ISBN", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference fileRef = mStorage.child(isbn + ".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Book newBook = new Book(isbn, etTitle.getText().toString(), etAuthor.getText().toString(),
                            etCategory.getText().toString(), etEdition.getText().toString(),
                            Integer.parseInt(etTotal.getText().toString()), Integer.parseInt(etIssued.getText().toString()),
                            etDesc.getText().toString(), uri.toString());

                    mDatabase.child(isbn).setValue(newBook).addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Book Added!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                })
        );
    }
}