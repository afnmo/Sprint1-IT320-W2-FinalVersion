package com.example.navigationdrawer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.View;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class AddFragmentActivity extends Fragment {

    EditText dressName;

    DatabaseHelper databaseHelper;
    ImageView dressPhoto;

//    ListView DressListView;
    FloatingActionButton fab;
    Button addButton;
    int SELECT_PICTURE = 200;


    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

//    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
//
//        setContentView(R.layout.fragment_add);
        dressName = rootView.findViewById(R.id.dressName);
        dressPhoto = rootView.findViewById(R.id.image_view_dress);
        fab = rootView.findViewById(R.id.floating_action_button);
        databaseHelper = new DatabaseHelper(container.getContext());
        addButton = rootView.findViewById(R.id.addButton);
//        DressListView = findViewById(R.id.list_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the dress name and image from the EditText and ImageView

//                Toast.makeText(getActivity(), "image: " + dressPhoto.getDrawable(), Toast.LENGTH_SHORT).show();
//
//                if(dressPhoto.getDrawable() != null){
                    Bitmap dressImage = ((BitmapDrawable) dressPhoto.getDrawable()).getBitmap();
                    // Create a new DressModel object with the name and image
                    DressModel dressModel = new DressModel(-1, dressName.getText().toString(), dressImage);
                    // Add the DressModel to the database
                    boolean success = databaseHelper.addOne(dressModel);

                    if(success){

                        Toast.makeText(getActivity(), "Dress added successfully", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragmentActivity()).commit();

                    }else{
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
//                }
//                else {
//                    Toast.makeText(getActivity(), "image not added", Toast.LENGTH_SHORT).show();
//                }


            }
        });

        return rootView;
    }

//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.item_done) {
//            Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.fome_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }


//    private void ShowDressOnListView(DatabaseHelper dataBaseHelper) {
//        DressrArrayAdapter = new DressAdaptor(AddFragmentActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAll());
//        DressListView.setAdapter(DressrArrayAdapter);
//    }


    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    dressPhoto.setImageURI(selectedImageUri);
                }
            }
        }

}

}