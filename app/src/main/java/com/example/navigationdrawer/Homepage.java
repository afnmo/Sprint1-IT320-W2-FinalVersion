package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends Fragment {

    DatabaseHelper databaseHelper;
    RecyclerView rvPrograms;
    DressAdaptor dressAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<DressModel> dressModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_homepage, container, false);

        databaseHelper = new DatabaseHelper(container.getContext());
        dressModelList = databaseHelper.getAll();
        rvPrograms = rootView.findViewById(R.id.rvPrograms);
        rvPrograms.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        rvPrograms.setLayoutManager(layoutManager);
        dressAdapter = new DressAdaptor(container.getContext(), dressModelList, rvPrograms);
        rvPrograms.setAdapter(dressAdapter);


        DatabaseHelper databaseHelper = new DatabaseHelper(container.getContext());
        List<DressModel> dressList = databaseHelper.getAll();

        return rootView;


    }
}