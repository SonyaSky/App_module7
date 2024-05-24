// Generated by view binder compiler. Do not edit!
package com.example.module7.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.module7.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.RangeSlider;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMaskingBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RangeSlider effectSeekBar;

  @NonNull
  public final LinearLayout fragmentRotate;

  @NonNull
  public final MaterialButton maskingApply;

  @NonNull
  public final RangeSlider radiusSeekBar;

  @NonNull
  public final TextView text1;

  @NonNull
  public final TextView text2;

  @NonNull
  public final TextView text3;

  @NonNull
  public final RangeSlider thresholdSeekBar;

  private FragmentMaskingBinding(@NonNull LinearLayout rootView, @NonNull RangeSlider effectSeekBar,
      @NonNull LinearLayout fragmentRotate, @NonNull MaterialButton maskingApply,
      @NonNull RangeSlider radiusSeekBar, @NonNull TextView text1, @NonNull TextView text2,
      @NonNull TextView text3, @NonNull RangeSlider thresholdSeekBar) {
    this.rootView = rootView;
    this.effectSeekBar = effectSeekBar;
    this.fragmentRotate = fragmentRotate;
    this.maskingApply = maskingApply;
    this.radiusSeekBar = radiusSeekBar;
    this.text1 = text1;
    this.text2 = text2;
    this.text3 = text3;
    this.thresholdSeekBar = thresholdSeekBar;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMaskingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMaskingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_masking, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMaskingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.effect_seek_bar;
      RangeSlider effectSeekBar = ViewBindings.findChildViewById(rootView, id);
      if (effectSeekBar == null) {
        break missingId;
      }

      LinearLayout fragmentRotate = (LinearLayout) rootView;

      id = R.id.masking_apply;
      MaterialButton maskingApply = ViewBindings.findChildViewById(rootView, id);
      if (maskingApply == null) {
        break missingId;
      }

      id = R.id.radius_seek_bar;
      RangeSlider radiusSeekBar = ViewBindings.findChildViewById(rootView, id);
      if (radiusSeekBar == null) {
        break missingId;
      }

      id = R.id.text1;
      TextView text1 = ViewBindings.findChildViewById(rootView, id);
      if (text1 == null) {
        break missingId;
      }

      id = R.id.text2;
      TextView text2 = ViewBindings.findChildViewById(rootView, id);
      if (text2 == null) {
        break missingId;
      }

      id = R.id.text3;
      TextView text3 = ViewBindings.findChildViewById(rootView, id);
      if (text3 == null) {
        break missingId;
      }

      id = R.id.threshold_seek_bar;
      RangeSlider thresholdSeekBar = ViewBindings.findChildViewById(rootView, id);
      if (thresholdSeekBar == null) {
        break missingId;
      }

      return new FragmentMaskingBinding((LinearLayout) rootView, effectSeekBar, fragmentRotate,
          maskingApply, radiusSeekBar, text1, text2, text3, thresholdSeekBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}