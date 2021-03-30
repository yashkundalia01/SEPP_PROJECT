package com.yash.netbanking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yash.netbanking.Adapter.BeneficiaryAdapter;
import com.yash.netbanking.Beneficiary;
import com.yash.netbanking.Client;
import com.yash.netbanking.R;
import com.yash.netbanking.RecyclerViewInterface;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FavouriteTransactionActivity extends AppCompatActivity implements RecyclerViewInterface {

    Client client;
    ArrayList<Beneficiary> arrayList;
    BeneficiaryAdapter adapter;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ClientActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Client", client);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_transaction);

        client = (Client) getIntent().getSerializableExtra("CurrentUser");

        arrayList = client.getFavourite_transaction();

        RecyclerView view = findViewById(R.id.favourite_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        view.setLayoutManager(linearLayoutManager);
        view.setItemAnimator(new DefaultItemAnimator());

        if (arrayList != null && !arrayList.isEmpty()) {
            adapter = new BeneficiaryAdapter(arrayList, this);
            view.setAdapter(adapter);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final ArrayList<Beneficiary> list1 = adapter.getArrayList();
                final Beneficiary t = list1.get(position);
                list1.remove(position);
                final ArrayList<Beneficiary> temp = client.getFavourite_transaction();
                client.setFavourite_transaction(list1);
                adapter.notifyItemRemoved(position);
                ConstraintLayout layout = findViewById(R.id.main_layout1);

                final String id = client.getClientId();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference();
                myRef.child("client").child(id).setValue(client);

                Snackbar.make(layout, "Successfully Removed", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list1.add(position, t);
                        adapter.notifyItemInserted(position);
                        client.setFavourite_transaction(temp);
                        myRef.child("client").child(id).setValue(client);
                    }
                }).show();


            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(FavouriteTransactionActivity.this, R.color.delete))
                        .create()
                        .decorate();
            }
        }).attachToRecyclerView(view);


    }

    @Override
    public void onBeneficiaryItemClick(int position) {
        Beneficiary current = arrayList.get(position);
        Intent i = new Intent(FavouriteTransactionActivity.this, TransactionActivity.class);
        i.putExtra("beneficiary", current);
        i.putExtra("CurrentUser", client);
        startActivity(i);
    }
}