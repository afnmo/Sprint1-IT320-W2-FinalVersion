package com.example.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    NavigationView navigationView;
    private DrawerLayout drawerLayout;

    Menu menu;

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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homepage()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    public Menu getMenu(){
        return navigationView.getMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.nav_login:

                menu.getItem(1).setVisible(false);
                menu.getItem(4).setVisible(true);

                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homepage()).commit();
                break;
            case R.id.nav_remove:
//                not loged in yet
                if(menu.getItem(1).isVisible()){
                    Intent intent = new Intent(getApplicationContext(), login.class);
                    startActivity(intent);
                    Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
                    menu.getItem(1).setVisible(false);
                    menu.getItem(4).setVisible(true);
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeleteFragment()).commit();
                }
                break;
            case R.id.nav_add:
//                not logged in yet
                if(menu.getItem(1).isVisible()){
                    Intent intent = new Intent(getApplicationContext(), login.class);
                    startActivity(intent);
                    Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
                    menu.getItem(1).setVisible(false);
                    menu.getItem(4).setVisible(true);
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragmentActivity()).commit();
                }
//                Intent i = new Intent(getApplicationContext(), AddFragmentActivity.class);
//                startActivity(i);
                break;
            case R.id.nav_logout:
//                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE);
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
}


