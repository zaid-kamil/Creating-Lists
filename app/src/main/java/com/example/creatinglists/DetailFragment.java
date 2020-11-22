package com.example.creatinglists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.creatinglists.databinding.FragmentDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class DetailFragment extends BottomSheetDialogFragment {

    private com.example.creatinglists.databinding.FragmentDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDetailBinding.bind(view);
        DetailFragmentArgs bundle = DetailFragmentArgs.fromBundle(getArguments());
        String shopName = bundle.getShopname();
        binding.textView6.setText(shopName);
        binding.tvAddr2.setText(bundle.getAddr());
    }
}