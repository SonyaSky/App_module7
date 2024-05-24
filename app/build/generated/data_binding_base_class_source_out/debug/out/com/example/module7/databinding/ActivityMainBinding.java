// Generated by view binder compiler. Do not edit!
package com.example.module7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.module7.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final MaterialButton approveBtn;

  @NonNull
  public final MaterialButton backBtn;

  @NonNull
  public final LinearLayout btnsForFilters;

  @NonNull
  public final MaterialButton drawingBtn;

  @NonNull
  public final MaterialButton faceBtn;

  @NonNull
  public final MaterialButton filterBtn;

  @NonNull
  public final FragmentContainerView fragmentCont;

  @NonNull
  public final MaterialButton maskingBtn;

  @NonNull
  public final MaterialButton pickImageBtn;

  @NonNull
  public final MaterialButton retouchBtn;

  @NonNull
  public final MaterialButton rotateBtn;

  @NonNull
  public final MaterialButton saveImageBtn;

  @NonNull
  public final MaterialButton scaleBtn;

  @NonNull
  public final ImageView selectedImage;

  @NonNull
  public final MaterialButton takePhotoBtn;

  @NonNull
  public final MaterialButton vectorBtn;

  private ActivityMainBinding(@NonNull LinearLayout rootView, @NonNull MaterialButton approveBtn,
      @NonNull MaterialButton backBtn, @NonNull LinearLayout btnsForFilters,
      @NonNull MaterialButton drawingBtn, @NonNull MaterialButton faceBtn,
      @NonNull MaterialButton filterBtn, @NonNull FragmentContainerView fragmentCont,
      @NonNull MaterialButton maskingBtn, @NonNull MaterialButton pickImageBtn,
      @NonNull MaterialButton retouchBtn, @NonNull MaterialButton rotateBtn,
      @NonNull MaterialButton saveImageBtn, @NonNull MaterialButton scaleBtn,
      @NonNull ImageView selectedImage, @NonNull MaterialButton takePhotoBtn,
      @NonNull MaterialButton vectorBtn) {
    this.rootView = rootView;
    this.approveBtn = approveBtn;
    this.backBtn = backBtn;
    this.btnsForFilters = btnsForFilters;
    this.drawingBtn = drawingBtn;
    this.faceBtn = faceBtn;
    this.filterBtn = filterBtn;
    this.fragmentCont = fragmentCont;
    this.maskingBtn = maskingBtn;
    this.pickImageBtn = pickImageBtn;
    this.retouchBtn = retouchBtn;
    this.rotateBtn = rotateBtn;
    this.saveImageBtn = saveImageBtn;
    this.scaleBtn = scaleBtn;
    this.selectedImage = selectedImage;
    this.takePhotoBtn = takePhotoBtn;
    this.vectorBtn = vectorBtn;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.approve_btn;
      MaterialButton approveBtn = ViewBindings.findChildViewById(rootView, id);
      if (approveBtn == null) {
        break missingId;
      }

      id = R.id.back_btn;
      MaterialButton backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.btns_for_filters;
      LinearLayout btnsForFilters = ViewBindings.findChildViewById(rootView, id);
      if (btnsForFilters == null) {
        break missingId;
      }

      id = R.id.drawing_btn;
      MaterialButton drawingBtn = ViewBindings.findChildViewById(rootView, id);
      if (drawingBtn == null) {
        break missingId;
      }

      id = R.id.face_btn;
      MaterialButton faceBtn = ViewBindings.findChildViewById(rootView, id);
      if (faceBtn == null) {
        break missingId;
      }

      id = R.id.filter_btn;
      MaterialButton filterBtn = ViewBindings.findChildViewById(rootView, id);
      if (filterBtn == null) {
        break missingId;
      }

      id = R.id.fragment_cont;
      FragmentContainerView fragmentCont = ViewBindings.findChildViewById(rootView, id);
      if (fragmentCont == null) {
        break missingId;
      }

      id = R.id.masking_btn;
      MaterialButton maskingBtn = ViewBindings.findChildViewById(rootView, id);
      if (maskingBtn == null) {
        break missingId;
      }

      id = R.id.pick_image_btn;
      MaterialButton pickImageBtn = ViewBindings.findChildViewById(rootView, id);
      if (pickImageBtn == null) {
        break missingId;
      }

      id = R.id.retouch_btn;
      MaterialButton retouchBtn = ViewBindings.findChildViewById(rootView, id);
      if (retouchBtn == null) {
        break missingId;
      }

      id = R.id.rotate_btn;
      MaterialButton rotateBtn = ViewBindings.findChildViewById(rootView, id);
      if (rotateBtn == null) {
        break missingId;
      }

      id = R.id.save_image_btn;
      MaterialButton saveImageBtn = ViewBindings.findChildViewById(rootView, id);
      if (saveImageBtn == null) {
        break missingId;
      }

      id = R.id.scale_btn;
      MaterialButton scaleBtn = ViewBindings.findChildViewById(rootView, id);
      if (scaleBtn == null) {
        break missingId;
      }

      id = R.id.selected_image;
      ImageView selectedImage = ViewBindings.findChildViewById(rootView, id);
      if (selectedImage == null) {
        break missingId;
      }

      id = R.id.take_photo_btn;
      MaterialButton takePhotoBtn = ViewBindings.findChildViewById(rootView, id);
      if (takePhotoBtn == null) {
        break missingId;
      }

      id = R.id.vector_btn;
      MaterialButton vectorBtn = ViewBindings.findChildViewById(rootView, id);
      if (vectorBtn == null) {
        break missingId;
      }

      return new ActivityMainBinding((LinearLayout) rootView, approveBtn, backBtn, btnsForFilters,
          drawingBtn, faceBtn, filterBtn, fragmentCont, maskingBtn, pickImageBtn, retouchBtn,
          rotateBtn, saveImageBtn, scaleBtn, selectedImage, takePhotoBtn, vectorBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}