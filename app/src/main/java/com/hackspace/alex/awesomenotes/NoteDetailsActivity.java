package com.hackspace.alex.awesomenotes;

import java.io.IOException;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hackspace.alex.awesomenotes.activity.SideMenuActivity;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.presenter.NoteDetailsPresenter;
import com.hackspace.alex.awesomenotes.utils.INoteDetailsView;
import com.hackspace.alex.worklibrary.utils.CameraUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class NoteDetailsActivity extends SideMenuActivity implements INoteDetailsView {
    @BindView(R.id.title_edit_text)
    EditText mTitleEditText;
    @BindView(R.id.content_edit_text)
    EditText mContentEditText;
    @BindView(R.id.attached_photo_image_view)
    ImageView mAttachedPhotoImageView;
    private static final int GET_PHOTO_REQUEST_CODE = 1001;
    private static final int PERMISSION_REQUEST_CODE = 104;

    private NoteDetailsPresenter mNoteDetailsPresenter;

    @OnClick(R.id.add_photo_fab)
    void onPhotoAddClick() {
        if (alreadyHaveCameraPermissions()) {
            attachPhotoActions();
        } else requestForCameraPermissions();
    }

    private void attachPhotoActions() {
        CameraUtils.pickImage(this, GET_PHOTO_REQUEST_CODE);
    }

    private void requestForCameraPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_details_layout);
        setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNoteDetailsPresenter = new NoteDetailsPresenter(this);
        String noteId = getIntent().getStringExtra(Note.NOTE_ID);
        mNoteDetailsPresenter.onInitView(noteId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            String title = mTitleEditText.getText().toString();
            String content = mContentEditText.getText().toString();
            mNoteDetailsPresenter.onBackButtonPressed(title, content);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void displayNote(Note note) {
        mTitleEditText.setText(note.getTitle());
        mContentEditText.setText(note.getContent());
    }

    @Override
    public void navigateToNotesScreen() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case GET_PHOTO_REQUEST_CODE: {
                    Uri imageUri = data.getData();
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        mAttachedPhotoImageView.setImageBitmap(imageBitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean alreadyHaveCameraPermissions() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    attachPhotoActions();
                } else {
                    //not granted
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
