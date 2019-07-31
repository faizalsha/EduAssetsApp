package com.example.krishbhatia.eduassets.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.POJO.UserPOJO;
import com.example.krishbhatia.eduassets.utils.SharedPreferenceImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.ViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomePageActivity";
    private String userName;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView navName;
    private TextView navEmail;
    private View headerView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private Context context;
    private UserPOJO userPOJO;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth=FirebaseAuth.getInstance();
        context = HomePageActivity.this;
        getDatabase();
        getSyllabus();
        initializingComponents();



    }

    private void getDatabase() {
        Gson gson = new Gson();
        userPOJO = gson.fromJson(SharedPreferenceImpl.getInstance().get(Constants.USERPOJO, this), UserPOJO.class);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String s="https://firebasestorage.googleapis.com/v0/b/expandingcard.appspot.com/o/ShadabFaizal_InternshalaResume.pdf?alt=media&token=f6eca077-3a08-4049-aa69-ee3f0233d8f4";

        if (id == R.id.yourSyllabus) {
            Intent intent=new Intent(HomePageActivity.this,PdfViewerActivity.class);
            intent.putExtra(Constants.URL,SharedPreferenceImpl.getInstance().get(Constants.SYLLABUS,context));
            startActivity(intent);
        }


   else if (id == R.id.nav_logout) {
            mAuth.signOut();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut();
            SharedPreferenceImpl.getInstance().clearAll(context);
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
        else if(id== R.id.nav_header_image_view){

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getDatabase();
        navEmail.setText(userPOJO.getEmail());
        navName.setText(userPOJO.getName());

    }


  private void getSyllabus(){
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.SYLLABUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String syllabusUrl=(String) dataSnapshot.child(String.valueOf(userPOJO.getCourseId())).child("url").getValue();
//                String syllabusUrl="https://firebasestorage.googleapis.com/v0/b/eduassets-63873.appspot.com/o/1.%20R.S%20Aggarwal%20Quantitative%20Aptitude%20(%20PDFDrive.com%20).pdf?alt=media&token=9d4ac881-847b-44b4-8582-f987ccb2ef5f";
                SharedPreferenceImpl.getInstance().save(Constants.SYLLABUS,syllabusUrl,context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
  }

    private void initializingComponents() {

        toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);



        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mAuth = FirebaseAuth.getInstance();
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ProfileActivity.class));
            }
        });
        navName = headerView.findViewById(R.id.nav_header_username_text_view);

        navEmail = headerView.findViewById(R.id.nav_header_email_text_view);
        navEmail.setText(userPOJO.getEmail());
        navName.setText(userPOJO.getName());
    }
}
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                userName = dataSnapshot.child("name").getValue().toString();
//                navName = headerView.findViewById(R.id.name_nav_header);
//                navName.setText(userName);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                navName.setText("Error loading username");
//            }
//        });