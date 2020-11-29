package com.example.creatinglists;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.creatinglists.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private com.example.creatinglists.databinding.FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        binding.btnRecyclerExample.setOnClickListener(v1->{
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_FirstFragment);
        });

        binding.btnUploader.setOnClickListener(v2->{
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_uploadImageFragment);
        });
    }
}