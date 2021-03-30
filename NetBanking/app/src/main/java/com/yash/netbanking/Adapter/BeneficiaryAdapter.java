package com.yash.netbanking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.netbanking.Beneficiary;
import com.yash.netbanking.R;
import com.yash.netbanking.RecyclerViewInterface;

import java.util.ArrayList;

public class BeneficiaryAdapter  extends RecyclerView.Adapter<BeneficiaryAdapter.MyViewHolder>  {

    ArrayList<Beneficiary> arrayList;
    RecyclerViewInterface anInterface;

    public ArrayList<Beneficiary> getArrayList() {
        return arrayList;
    }

    public BeneficiaryAdapter(ArrayList<Beneficiary> arrayList, RecyclerViewInterface anInterface) {
        this.arrayList = arrayList;
        this.anInterface = anInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item3, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Beneficiary b = arrayList.get(position);
        holder.bankName.setText(b.getBank_name());
        holder.name.setText(b.getBank_account_name());
        holder.bankBranch.setText(b.getBank_branch());
        holder.bankIFSCCode.setText(b.getBank_ifsc_code());
        holder.accountNo.setText(b.getBank_account_no());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder{

        EditText name, bankName, bankBranch, accountNo, bankIFSCCode;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.list_person_name);
            bankName = itemView.findViewById(R.id.list_bank_name);
            accountNo = itemView.findViewById(R.id.list_account_no);
            bankBranch = itemView.findViewById(R.id.list_branch_name);
            bankIFSCCode = itemView.findViewById(R.id.list_bank_IFSC_code);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anInterface.onBeneficiaryItemClick(getAdapterPosition());
                }
            });

        }
    }

}
