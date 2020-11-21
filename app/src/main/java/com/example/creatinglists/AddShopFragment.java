package com.example.creatinglists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.creatinglists.databinding.FragmentAddShopBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddShopFragment extends Fragment {

    private com.example.creatinglists.databinding.FragmentAddShopBinding binding;
    private FirebaseFirestore fb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddShopBinding.bind(view);
        fb = FirebaseFirestore.getInstance();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);
        binding.fabShop.setOnClickListener(v1 -> {
            String sName = binding.tvShopName.getText().toString();
            String sAddr = binding.tvShopAddr.getText().toString();
            float rating = binding.shopRating.getRating();
            int category = binding.categorySpinner.getSelectedItemPosition();
            if (sName.length() > 2) {
                if (sAddr.length() > 10) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    ShopModel model = new ShopModel(sName, sAddr, (int) rating, category);
                    fb.collection("shops").add(model).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getId();
                            updateUI(id);
                        } else {
                            String error = task.getException().getMessage();
                            Snackbar.make(binding.fabShop, error, BaseTransientBottomBar.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    });
                } else {
                    // TODO: 11/21/2020 add error msg for shop addr
                }
            } else {
                // TODO: 11/21/2020  add error msg for name
            }
        });
    }

    private void updateUI(String id) {
        binding.progressBar.setVisibility(View.GONE);
        if (id != null) {
            binding.tvShopName.setText("");
            binding.tvShopAddr.setText("");
            binding.shopRating.setRating(3);
            binding.categorySpinner.setSelection(0);
        }
    }
}