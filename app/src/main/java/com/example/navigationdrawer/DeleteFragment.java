package com.example.navigationdrawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DeleteFragment extends Fragment {

    Button deleteButton;
    DatabaseHelper databaseHelper;
    RecyclerView deleteRecyclerView;


    DeletePageAdapter deletePageAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<DressModel> dressModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_delete, container, false);
//        deleteButton = rootView.findViewById(R.id.deleteButton);
        databaseHelper = new DatabaseHelper(container.getContext());
        dressModelList = databaseHelper.getAll();
        deleteRecyclerView = rootView.findViewById(R.id.deleteRecyclerView);
        deleteRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        deleteRecyclerView.setLayoutManager(layoutManager);
        deletePageAdapter = new DeletePageAdapter(container.getContext(), dressModelList);
        deleteRecyclerView.setAdapter(deletePageAdapter);
//        DatabaseHelper databaseHelper = new DatabaseHelper(container.getContext());
//        List<DressModel> dressList = databaseHelper.getAll();

//        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                customerModel ClickedStudent = (customerModel) adapterView.getItemAtPosition(i);
//                dataBaseHelper.DeleteOne(ClickedStudent);
//                ShowCustomersOnListView(dataBaseHelper);
//                Toast.makeText(MainActivity.this, "Deleted: " + ClickedStudent.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//        });

        return rootView;

    }


}