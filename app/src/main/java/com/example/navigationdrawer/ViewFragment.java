package com.example.navigationdrawer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ViewFragment extends Fragment {

    MenuHelper menuHelper;
    Button rentButton;
    TextView name, description, price, size, contact, city;
    ImageView image;
    NavigationView navigationView;
    Menu menu;

//    Menu menu;
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
        menuHelper = new MenuHelper();

//
//        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//        View anotherLayout = layoutInflater.inflate(R.layout.activity_main, container, false);
//        navigationView = anotherLayout.findViewById(R.id.nav_view);
//        menu = navigationView.getMenu();

        Bundle bundle = getArguments();
//        Bundle bundle = arguments.getBundle("bundle");
//        Bundle bundle1 = arguments.getBundle("bundle1");
//        if(bundle1 == null){
//            System.out.println("bundle1 is null");
//        }
//        menuHelper = bundle1.getParcelable("menu");
//        if(menuHelper == null){
//            System.out.println("menuHelper is null");
//        }
//        Bundle bundle = getArguments();
        if (bundle != null) {
//            menuHelper = bundle.getParcelable("menu");
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
//                not loged in yet
//                here
//
//
//                if(menuHelper!= null && menuHelper.getMenu().getItem(1).isVisible()){
//                    Intent intent = new Intent(getContext().getApplicationContext(), login.class);
//                    startActivity(intent);
//                    Toast.makeText(getContext(), "Login First", Toast.LENGTH_SHORT).show();
//                    menuHelper.getMenu().getItem(1).setVisible(false);
//                    menuHelper.getMenu().getItem(5).setVisible(true);
//                }
                //SharedPreferences sharedPreferences = container.getContext().getSharedPreferences("my_app_preferences", MODE_PRIVATE);
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