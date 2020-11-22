package com.example.creatinglists;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creatinglists.databinding.FragmentFirstBinding;
import com.example.creatinglists.databinding.RowShopLayoutBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private com.example.creatinglists.databinding.FragmentFirstBinding binding;
    private FirebaseFirestore fb;
    private ArrayList<ShopModel> shoplist;
    private ShopAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        shoplist = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fb = FirebaseFirestore.getInstance();


        binding = FragmentFirstBinding.bind(view);
        binding.shoprecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        get_data_from_fireStore();
        updateUI();
        binding.btnAddShop.setOnClickListener(v3->{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_FirstFragment_to_addShopFragment);
        });

    }

    private void updateUI() {
        adapter = new ShopAdapter(getActivity(), shoplist, R.layout.row_shop_layout);
        binding.shoprecyler.setAdapter(adapter);


    }

    public void get_data_from_fireStore(){
        shoplist.clear();
        fb.collection("shops").get().addOnCompleteListener(task->{
            if (task.isSuccessful()) {
                QuerySnapshot result = task.getResult();
                for (DocumentSnapshot doc : result.getDocuments()) {
                    shoplist.add(doc.toObject(ShopModel.class));
                    adapter.notifyDataSetChanged();
                }
            }else{
                String error = task.getException().getMessage();
                Snackbar.make(binding.textView,error, BaseTransientBottomBar.LENGTH_LONG).show();
            }
            binding.pb.setVisibility(View.GONE);

        });


    }


    class ShopAdapter extends RecyclerView.Adapter<Holder> {

        Context c;
        LayoutInflater inflater;
        List<ShopModel> shops;
        int layout;

        public ShopAdapter(Context c, List<ShopModel> shops, int layout) {
            this.c = c;
            this.layout = layout;
            this.shops = shops;
            inflater = LayoutInflater.from(c);
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = inflater.inflate(layout, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            ShopModel model = shops.get(position);
            holder.row.container.setTag(model);
            holder.row.tvShopName.setText(model.name);
            holder.row.tvAddr.setText(model.address);
            holder.row.tvRating.setText(String.valueOf(model.rating));
            int img = R.drawable.ic_baseline_account_balance_24;
            switch (model.category) {
                case 0:
                    img = R.drawable.ic_baseline_shopping_basket_24;
                    break;
                case 1:
                    img = R.drawable.ic_baseline_sports_handball_24;
                    break;
                case 2:
                    break;
            }
            holder.row.imgCategory.setImageResource(img);
        }


        @Override
        public int getItemCount() {
            return shops.size();
        }

    }

    class Holder extends RecyclerView.ViewHolder {

        public final com.example.creatinglists.databinding.RowShopLayoutBinding row;

        public Holder(@NonNull View itemView) {
            super(itemView);
            row = RowShopLayoutBinding.bind(itemView);
            row.container.setOnClickListener(view -> {
                ShopModel model = (ShopModel) view.getTag();
                FirstFragmentDirections.ActionFirstFragmentToDetailFragment nav = FirstFragmentDirections.actionFirstFragmentToDetailFragment(model.name,model.address);
                NavHostFragment.findNavController(FirstFragment.this).navigate(nav);
            });
        }
    }
}