package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Homepage extends Fragment implements RecyclerViewInterface{

    MenuHelper menuHelper;
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
        dressAdapter = new DressAdaptor(container.getContext(), dressModelList, rvPrograms, this);
        rvPrograms.setAdapter(dressAdapter);
        menuHelper = new MenuHelper();
//
//
//        DatabaseHelper databaseHelper = new DatabaseHelper(container.getContext());
//        List<DressModel> dressList = databaseHelper.getAll();

        return rootView;

    }

    @Override
    public void onItemClick(int position) {

        Bitmap bitmap = dressModelList.get(position).getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

// I switch from new bundle to get arguments
        Bundle bundle = new Bundle();
        Bundle bundle1 = getArguments();

        menuHelper = bundle1.getParcelable("menu");
        bundle.putParcelable("menu", menuHelper);
// get arguments menu
        bundle.putString("name", dressModelList.get(position).getName());
        bundle.putByteArray("image", byteArray);
        bundle.putString("description", dressModelList.get(position).getDescription());
        bundle.putInt("price", dressModelList.get(position).getPrice());
        bundle.putString("size", dressModelList.get(position).getSize());
        bundle.putString("contact", dressModelList.get(position).getPhoneNo());
        bundle.putString("city", dressModelList.get(position).getCity());
        bundle.putInt("clickedItemID", dressModelList.get(position).getID());



        Fragment fragment = new ViewFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    }
}