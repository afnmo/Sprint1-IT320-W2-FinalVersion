package com.example.navigationdrawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewRentedItemsFragment extends Fragment {

    DatabaseHelper databaseHelper;
    RecyclerView rentedItemsRecyclerView;
    DressAdaptor dressAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<DressModel> dressModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_rented_items, container, false);

        databaseHelper = new DatabaseHelper(container.getContext());
        dressModelList = databaseHelper.getAllRentedItems();
        rentedItemsRecyclerView = rootView.findViewById(R.id.rentedItemsRecyclerView);
        rentedItemsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        rentedItemsRecyclerView.setLayoutManager(layoutManager);
        dressAdapter = new DressAdaptor(container.getContext(), dressModelList, rentedItemsRecyclerView, this);

        rentedItemsRecyclerView.setAdapter(dressAdapter);


        return rootView;
    }
}