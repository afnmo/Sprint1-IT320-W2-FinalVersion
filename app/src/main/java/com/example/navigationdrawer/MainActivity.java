package com.example.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    NavigationView navigationView;
    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    Menu menu;
//    MenuHelper menuHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();
//        menuHelper = new MenuHelper(menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homepage()).commit();
//        navigationView.setCheckedItem(R.id.nav_home);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homepage()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Check if the user is logged in
//        sharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE);
//        String currentUsername = sharedPreferences.getString("current_username", null);
//        if (currentUsername != null) {
//            sharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE);
//            Map<String, ?> preferencesMap = sharedPreferences.getAll();
//            for (Map.Entry<String, ?> entry : preferencesMap.entrySet()) {
//                System.out.println("SharedPreference\n" + entry.getKey() + ": " + entry.getValue().toString());
//            }
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Homepage()).commit();
//            navigationView.setCheckedItem(R.id.nav_home);
//
//        }
//        else {
//            Intent i = new Intent(getApplicationContext(), login.class);
//            startActivity(i);
//        }

//        if (savedInstanceState == null) {
//
//            Intent i = new Intent(getApplicationContext(), login.class);
//            startActivity(i);


//            Fragment fragment = new Login();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("menu", menuHelper);
//            fragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

//        }
//        else{
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homepage()).commit();
//            navigationView.setCheckedItem(R.id.nav_home);
//
//        }
//


    }

    public Menu getMenu(){
        return navigationView.getMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case  R.id.nav_login:
//
////                menu.getItem(1).setVisible(false);
////                menu.getItem(5).setVisible(true);
//
//                Intent i = new Intent(getApplicationContext(), login.class);
//                startActivity(i);
//                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homepage()).commit();
                break;
            case R.id.nav_remove:
//                not loged in yet
//                if(menu.getItem(1).isVisible()){
//                    Intent intent = new Intent(getApplicationContext(), login.class);
//                    startActivity(intent);
//                    Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
//                    menu.getItem(1).setVisible(false);
//                    menu.getItem(5).setVisible(true);
//                }
//                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeleteFragment()).commit();
//                }
                break;
            case R.id.nav_add:
//                not logged in yet
//                if(menu.getItem(1).isVisible()){
//                    Intent intent = new Intent(getApplicationContext(), login.class);
//                    startActivity(intent);
//                    Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
//                    menu.getItem(1).setVisible(false);
//                    menu.getItem(5).setVisible(true);
//                }
//                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragmentActivity()).commit();
//                }
                break;

            case R.id.nav_rentedItems:
//                not logged in yet
//                if(menu.getItem(1).isVisible()){
//                    Intent intent = new Intent(getApplicationContext(), login.class);
//                    startActivity(intent);
//                    Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
//                    menu.getItem(1).setVisible(false);
//                    menu.getItem(5).setVisible(true);
//                }
//                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewRentedItemsFragment()).commit();
//                }
                break;

            case R.id.nav_logout:
//                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                sharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("current_username");
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                Toast.makeText(this, "Loged out successfully", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}


