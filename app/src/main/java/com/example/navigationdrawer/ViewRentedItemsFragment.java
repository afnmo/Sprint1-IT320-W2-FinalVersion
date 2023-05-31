package com.example.navigationdrawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewRentedItemsFragment extends Fragment {

    DatabaseHelper databaseHelper;
    RecyclerView rentedItemsRecyclerView;
    RentAdapter rentAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<DressModel> dressModelList = new ArrayList<>();
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_rented_items, container, false);
        databaseHelper = new DatabaseHelper(container.getContext());
        dressModelList = databaseHelper.getAllRentedItems();
        message = rootView.findViewById(R.id.no_rented_items_message);
        if(dressModelList == null){
            message.setVisibility(rootView.VISIBLE);
        }
        else {
            rentedItemsRecyclerView = rootView.findViewById(R.id.rentedItemsRecyclerView);
            rentedItemsRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(container.getContext());
            rentedItemsRecyclerView.setLayoutManager(layoutManager);
            rentAdapter = new RentAdapter(container.getContext(), dressModelList);
            rentedItemsRecyclerView.setAdapter(rentAdapter);
        }

        return rootView;
    }
}