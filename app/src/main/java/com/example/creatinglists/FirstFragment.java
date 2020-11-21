package com.example.creatinglists;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private com.example.creatinglists.databinding.FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<ShopModel> shoplist = new ArrayList<>();
        shoplist.add(new ShopModel(
                "Big bazaar",
                "2nd floor,Saharaganj,lucknow,226001",
                4,
                0)
        );
        shoplist.add(new ShopModel(
                "Big bazaar 2",
                "2nd floor,Saharaganj,\nlucknow,226001",
                3,
                1)
        );
        shoplist.add(new ShopModel(
                "Big bazaar 3",
                "2nd floor,Saharaganj,\nlucknow,\n226001",
                2,
                2)
        );

        binding = FragmentFirstBinding.bind(view);
        binding.shoprecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShopAdapter adapter = new ShopAdapter(getActivity(), shoplist, R.layout.row_shop_layout);
        binding.shoprecyler.setAdapter(adapter);

        binding.btnAddShop.setOnClickListener(v3->{
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_FirstFragment_to_addShopFragment);
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
        }
    }
}