package com.example.creatinglists;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.creatinglists.databinding.FragmentUploadImageBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class UploadImageFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private com.example.creatinglists.databinding.FragmentUploadImageBinding binding;
    boolean isPermitted = false;
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private AlertDialog dialog;
    private FirebaseStorage storage;
    static final int REQUEST_IMAGE_OPEN = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentUploadImageBinding.bind(view);

        storage = FirebaseStorage.getInstance();

        binding.fabUpload.setOnClickListener(v2 -> {
            isPermitted = EasyPermissions.hasPermissions(getActivity(), permissions);
            if (isPermitted) {
                openFileBrowser();
            } else {
                getPermissions();
            }
        });
    }

    private void getPermissions() {
        EasyPermissions.requestPermissions(this, "file upload permission", 3, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        openFileBrowser();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setMessage("Please provide permission to access folders for the app");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Retry", (dialogInterface, i) -> {
            getPermissions();
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Exit", (dialogInterface, i) -> {
            Toast.makeText(getActivity(), "exiting", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        });
        dialog.setTitle("Warning");
        dialog.show();
    }

    private void openFileBrowser() {
        selectImage();
    }
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Snackbar.make(binding.fabUpload,fullPhotoUri.toString(), BaseTransientBottomBar.LENGTH_INDEFINITE).show();
        }
    }


}