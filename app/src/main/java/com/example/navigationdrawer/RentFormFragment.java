package com.example.navigationdrawer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class RentFormFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    MenuHelper menuHelper;
    Spinner spinner;
    EditText occDate, pickDate, name, phone;
    Button rentButton;
    RentedItems rentedItems;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rent_form, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.rent_days);
        occDate = rootView.findViewById(R.id.rent_occ_date);
        pickDate = rootView.findViewById(R.id.rent_pickup_date);
        name = rootView.findViewById(R.id.rent_name);
        phone = rootView.findViewById(R.id.rent_phone);
        rentButton = rootView.findViewById(R.id.rent_button);
        databaseHelper = new DatabaseHelper(container.getContext());

//        to get requested rented dress id
        Bundle bundle = getArguments();
        int rentedItemID = bundle.getInt("clickedItemID");

        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    int days = Integer.parseInt(spinner.getSelectedItem().toString());
                    String occasionDate = occDate.getText().toString();
                    String pickupDate = pickDate.getText().toString();
                    String renterName = name.getText().toString();
                    String contact = phone.getText().toString();

//                public RentedItems(int id, int days, Date occDate, Date pickDate, String name, String phone) {
                    rentedItems = new RentedItems(-1, days, occasionDate, pickupDate, renterName, contact);


                    boolean success = databaseHelper.addRentedItem(rentedItems, rentedItemID);


                    if (success) {
                        databaseHelper.updateRentedColumnToTrue(rentedItemID);
                        Toast.makeText(getActivity(), "Dress rented successfully", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new ViewRentedItemsFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(), R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}