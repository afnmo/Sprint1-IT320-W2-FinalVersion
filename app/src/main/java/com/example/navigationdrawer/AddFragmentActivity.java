package com.example.navigationdrawer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
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
    EditText description;
    EditText price;
    EditText size;
    EditText phoneNo;
    EditText city;
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

        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        dressName = rootView.findViewById(R.id.dressName);
        dressPhoto = rootView.findViewById(R.id.image_view_dress);
        fab = rootView.findViewById(R.id.floating_action_button);
        databaseHelper = new DatabaseHelper(container.getContext());
        addButton = rootView.findViewById(R.id.addButton);

        description = rootView.findViewById(R.id.description);
        price = rootView.findViewById(R.id.price);
        size = rootView.findViewById(R.id.size);
        phoneNo = rootView.findViewById(R.id.phoneN);
        city = rootView.findViewById(R.id.city);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the dress name and image from the EditText and ImageView
//
//                    Bitmap dressImage = ((BitmapDrawable) dressPhoto.getDrawable()).getBitmap();
//                    // Create a new DressModel object with the name and image
//                    DressModel dressModel = new DressModel(-1, dressName.getText().toString(), dressImage,
//                            description.getText().toString(), Integer.parseInt(price.getText().toString()),
//                            size.getText().toString(), phoneNo.getText().toString(), city.getText().toString());
////                    (int ID, String name, Bitmap image, String description, int price, String size, String phoneNo, String city)
//
//                    // Add the DressModel to the database
//                    boolean success = databaseHelper.addOne(dressModel);
//
//                    if(success){
//
//                        Toast.makeText(getActivity(), "Dress added successfully", Toast.LENGTH_SHORT).show();
//                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragmentActivity()).commit();
//
//                    }else{
//                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get the dress name
                String name = dressName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the dress name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the dress description
                String dressDescription = description.getText().toString().trim();
                if (dressDescription.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the dress description", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the dress price
                String dressPriceText = price.getText().toString().trim();
                if (dressPriceText.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the dress price", Toast.LENGTH_SHORT).show();
                    return;
                }
                int dressPrice = Integer.parseInt(dressPriceText);

                // Get the dress size
                String dressSize = size.getText().toString().trim();
                if (dressSize.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the dress size", Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if(dressSize.toLowerCase() != "s" || dressSize.toLowerCase() != "m" || dressSize.toLowerCase() != "l"
//                || dressSize.toLowerCase() != "xs" || dressSize.toLowerCase() != "xl"){
//                    Toast.makeText(getActivity(), "Please enter dress size in s/m/l...", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                // Get the dress phone number
                String dressPhone = phoneNo.getText().toString().trim();
                if (dressPhone.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(dressPhone.length() != 10){
                    Toast.makeText(getActivity(), "Enter 10 numbers", Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if(dressPhone.substring(0,2) != "05"){
//                    Toast.makeText(getActivity(), "Enter phoneNo in fromat 05xxxxxxxx", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                // Get the dress city
                String dressCity = city.getText().toString().trim();
                if (dressCity.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a city", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap dressImage = null;
                Drawable drawable = dressPhoto.getDrawable();

                // Validate the dress image
                if (drawable instanceof VectorDrawable) {
                    Toast.makeText(getActivity(), "Please select a dress image", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    dressImage = ((BitmapDrawable) dressPhoto.getDrawable()).getBitmap();
                    if (dressImage == null || dressImage.getWidth() == 0 || dressImage.getHeight() == 0) {
                        Toast.makeText(getActivity(), "Please select a dress image", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                DressModel dressModel = new DressModel(-1, name, dressImage, dressDescription, dressPrice, dressSize, dressPhone, dressCity);

                // Add the DressModel to the database
                boolean success = databaseHelper.addOne(dressModel);

                if(success){
                    Toast.makeText(getActivity(), "Dress added successfully", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragmentActivity()).commit();
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


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