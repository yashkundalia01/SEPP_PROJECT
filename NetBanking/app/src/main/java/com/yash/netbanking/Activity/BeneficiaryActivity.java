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

public class BeneficiaryActivity extends AppCompatActivity implements RecyclerViewInterface {

    Client client1, client2;
    ArrayList<Beneficiary> arrayList;
    BeneficiaryAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ClientActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Client", client1);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary);

        getSupportActionBar().setTitle("Beneficiary");

        client1 = (Client) getIntent().getSerializableExtra("CurrentUser");
        client2 = (Client) getIntent().getSerializableExtra("beneficiary") ;

        arrayList = client1.getBeneficiary();

        RecyclerView view = findViewById(R.id.beneficiary_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        view.setLayoutManager(linearLayoutManager);
        view.setItemAnimator(new DefaultItemAnimator());

        if (arrayList != null && !arrayList.isEmpty()) {
            adapter = new BeneficiaryAdapter(arrayList, this);
            view.setAdapter(adapter);
        }

        if (client2 != null){
            arrayList = client2.getBeneficiary();
            adapter = new BeneficiaryAdapter(arrayList, this);
            view.setAdapter(adapter);
            client1.setBeneficiary(arrayList);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    final int position = viewHolder.getAdapterPosition();
                    final ArrayList<Beneficiary> list1 = adapter.getArrayList();
                    final Beneficiary t = list1.get(position);
                    list1.remove(position);
                    final ArrayList<Beneficiary> temp = client1.getBeneficiary();
                    client1.setBeneficiary(list1);
                    adapter.notifyItemRemoved(position);
                    ConstraintLayout layout = findViewById(R.id.main_layout);

                    final String id = client1.getClientId();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference();
                    myRef.child("client").child(id).setValue(client1);

                    Snackbar.make(layout, "Successfully Deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list1.add(position, t);
                            adapter.notifyItemInserted(position);
                            client1.setBeneficiary(temp);
                            myRef.child("client").child(id).setValue(client1);
                        }
                    }).show();
                }else if (direction == ItemTouchHelper.RIGHT){
                    final int position = viewHolder.getAdapterPosition();
                    final ArrayList<Beneficiary> list1 = adapter.getArrayList();
                    final Beneficiary t = list1.get(position);
                    ArrayList<Beneficiary> temp1 = client1.getFavourite_transaction();
                    if (temp1 == null)
                        temp1 = new ArrayList<>();
                    temp1.add(t);
                    ConstraintLayout layout = findViewById(R.id.main_layout);
                    client1.setFavourite_transaction(temp1);
                    adapter.notifyDataSetChanged();

                    final String id = client1.getClientId();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference();
                    myRef.child("client").child(id).setValue(client1);

                    Snackbar.make(layout, "Successfully Added", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<Beneficiary> temp = client1.getFavourite_transaction();
                            temp.remove(position);
                            client1.setFavourite_transaction(temp);
                            myRef.child("client").child(id).setValue(client1);
                            adapter.notifyDataSetChanged();
                        }
                    }).show();
                }

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(BeneficiaryActivity.this, R.color.delete))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_star_24)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(BeneficiaryActivity.this, R.color.add))
                        .create()
                        .decorate();
            }
        }).attachToRecyclerView(view);



    }

    public void addBeneficiary(View v){
        Intent i = new Intent(this, AddBeneficiaryActivity.class);
        i.putExtra("CurrentUser", client1);
        startActivity(i);
    }

    @Override
    public void onBeneficiaryItemClick(int position) {
        Beneficiary current = arrayList.get(position);
        Intent i = new Intent(BeneficiaryActivity.this, TransactionActivity.class);
        i.putExtra("beneficiary", current);
        i.putExtra("CurrentUser", client1);
        startActivity(i);
    }
}