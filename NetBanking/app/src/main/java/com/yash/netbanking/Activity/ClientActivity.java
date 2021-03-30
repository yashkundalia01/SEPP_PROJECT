package com.yash.netbanking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yash.netbanking.Adapter.ItemAdapter;
import com.yash.netbanking.Client;
import com.yash.netbanking.Item;
import com.yash.netbanking.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Client currentClient , client1, client2;
    TextView mClientName, mClientMoney;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String time = simpleDateFormat.format(d);
        currentClient.setLastLogin(time);
        myRef.child(currentClient.getClientId()).setValue(currentClient);
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        currentClient = (Client) getIntent().getSerializableExtra("Client");
        mClientName = findViewById(R.id.name_client);
        mClientMoney = findViewById(R.id.amount_client);
        myRef = database.getReference("client");

//        mClientName.setText(currentClient.getFirstName() + " " + currentClient.getLastName());
        if (currentClient != null)
            mClientMoney.setText(currentClient.getBalance() + " ₹");
        try{
            client1 = (Client) getIntent().getSerializableExtra("Client1");
            client2 = (Client) getIntent().getSerializableExtra("Client2");
            mClientMoney.setText(client1.getBalance() + " ₹");
            currentClient = client1;
            if (client1 != null && client2 != null){
                myRef.child(client2.getClientId()).setValue(client2);
                myRef.child(client1.getClientId()).setValue(client1);
            }
        }catch (Exception e){

        }



        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavigationView.setNavigationItemSelectedListener(this);
        mToolbar.setTitle("Welcome");
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        View hView =  mNavigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.name_client);
        TextView nav_last_login = (TextView)hView.findViewById(R.id.last_login);
        if (currentClient != null)
            nav_user.setText((currentClient.getFirstName() + " " + currentClient.getLastName()));
        else
            nav_user.setText((client1.getFirstName() + " " + client1.getLastName()));
        if (currentClient.getLastLogin() != null && !currentClient.getLastLogin().isEmpty()){
            nav_last_login.setText("Last login: "+currentClient.getLastLogin());
        }else {
            nav_last_login.setText("");
        }

        ArrayList<Item> products = new ArrayList<>();
        products.add(new Item(R.drawable.cardcvv, "Credit Cards"));
        products.add(new Item(R.drawable.moneybag, "FD/RD"));
        products.add(new Item(R.drawable.handmoneyrupee, "Loans"));
        products.add(new Item(R.drawable.increasegraphprofit, "Investment"));
        products.add(new Item(R.drawable.moneycopy, "Mutual Funds"));

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        ItemAdapter adapter1 = new ItemAdapter(products);
        RecyclerView mRecyclerView1 = findViewById(R.id.rv1);
        mRecyclerView1.setLayoutManager(linearLayoutManager1);
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView1.setAdapter(adapter1);

        ArrayList<Item> applyNow = new ArrayList<>();
        applyNow.add(new Item(R.drawable.handmoneyrupee, "Pre-approved offers"));
        applyNow.add(new Item(R.drawable.cardcvv, "Credit Cards"));
        applyNow.add(new Item(R.drawable.handmoneyrupee, "Loans"));
        applyNow.add(new Item(R.drawable.increaseupprofit, "Top Performing Mutual funds"));
        applyNow.add(new Item(R.drawable.increasegraphprofit, "Open Access Blog"));
        applyNow.add(new Item(R.drawable.moneybag, "Express FD"));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        ItemAdapter adapter2 = new ItemAdapter(applyNow);
        RecyclerView mRecyclerView2 = findViewById(R.id.rv2);
        mRecyclerView2.setLayoutManager(linearLayoutManager2);
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView2.setAdapter(adapter2);

        ArrayList<Item> payment = new ArrayList<>();
        payment.add(new Item(R.drawable.moneycopy, "Fund Transfer"));
        payment.add(new Item(R.drawable.ic_baseline_history_24, "Transaction History"));
        payment.add(new Item(R.drawable.mobile, "Recharge"));
        payment.add(new Item(R.drawable.bhimapp, "UPI"));

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);

        ItemAdapter adapter3 = new ItemAdapter(payment);
        RecyclerView mRecyclerView3 = findViewById(R.id.rv3);
        mRecyclerView3.setLayoutManager(linearLayoutManager3);
        mRecyclerView3.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView3.setAdapter(adapter3);


        ArrayList<Item> services = new ArrayList<>();
        services.add(new Item(R.drawable.userprofile, "My Details"));
        services.add(new Item(R.drawable.servicessupport, "Contact RM"));
        services.add(new Item(R.drawable.cheque, "Cheques"));
        services.add(new Item(R.drawable.cardcvv, "Debit Card"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        ItemAdapter adapter4 = new ItemAdapter(services);
        RecyclerView mRecyclerView4 = findViewById(R.id.rv);
        mRecyclerView4.setLayoutManager(linearLayoutManager);
        mRecyclerView4.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView4.setAdapter(adapter4);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String time = simpleDateFormat.format(d);
        currentClient.setLastLogin(time);
        myRef.child(currentClient.getClientId()).setValue(currentClient);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

    }

    public void showHistory(View view){
        Intent i = new Intent(this, HistoryActivity.class);
        if (currentClient != null)
            i.putExtra("CurrentUser", currentClient);
        else
            i.putExtra("CurrentUser", client1);
        startActivity(i);
    }

    public void fundTransfer(View view){
//        Intent i = new Intent(this, TransactionActivity.class);
        Intent i = new Intent(this, BeneficiaryActivity.class);
        i.putExtra("CurrentUser", currentClient);
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.client_logout){
            if (currentUser != null){
                mAuth.signOut();
                Intent intent = new Intent(this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        else if (item.getItemId() == R.id.transaction_history_menu){
            Intent i = new Intent(this, HistoryActivity.class);
            if (currentClient != null)
                i.putExtra("CurrentUser", currentClient);
            else
                i.putExtra("CurrentUser", client1);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.fund_transfer_menu){
            Intent i = new Intent(this, BeneficiaryActivity.class);
            i.putExtra("CurrentUser", currentClient);
            startActivity(i);
        }else if (item.getItemId() == R.id.change_password_menu){
            Intent i = new Intent(this, ChangePasswordActivity.class);
            if (currentClient != null)
                i.putExtra("CurrentUser", currentClient);
            else
                i.putExtra("CurrentUser", client1);
            startActivity(i);
        } else if (item.getItemId() == R.id.favourite_transaction){
            Intent i = new Intent(this, FavouriteTransactionActivity.class);
            i.putExtra("CurrentUser", currentClient);
            startActivity(i);
        }
        return true;
    }


}