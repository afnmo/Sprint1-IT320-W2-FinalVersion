package com.example.navigationdrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewFragment extends Fragment {

    Button rentButton;
    TextView name, description, price, size, contact, city;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);
        name = rootView.findViewById(R.id.view_name);
        image = rootView.findViewById(R.id.view_image);
        description = rootView.findViewById(R.id.view_description);
        price = rootView.findViewById(R.id.view_price);
        size = rootView.findViewById(R.id.view_size);
        contact = rootView.findViewById(R.id.view_contact);
        city = rootView.findViewById(R.id.view_city);
        rentButton = rootView.findViewById(R.id.rentBtn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            byte[] byteArray = bundle.getByteArray("image");
            Bitmap getImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            String getName = bundle.getString("name");
            String getDescripton = bundle.getString("description");
            int getPrice = bundle.getInt("price");
            String getSize = bundle.getString("size");
            String getContact = bundle.getString("contact");
            String getCity = bundle.getString("city");

//            int clickedItemID = bundle.getInt("clickedItemID");

            name.setText(getName);
            image.setImageBitmap(getImage);
            description.setText(getDescripton);
            price.setText(String.valueOf(getPrice));
            size.setText(getSize);
            contact.setText(getContact);
            city.setText(getCity);

        }

        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RentFormFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }
}