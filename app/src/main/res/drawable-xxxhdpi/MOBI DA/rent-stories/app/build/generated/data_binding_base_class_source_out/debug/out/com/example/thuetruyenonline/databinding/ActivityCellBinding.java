// Generated by view binder compiler. Do not edit!
package com.example.thuetruyenonline.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.thuetruyenonline.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCellBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageView ivAnhd;

  @NonNull
  public final TextView tvNameStory;

  private ActivityCellBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardView,
      @NonNull ImageView ivAnhd, @NonNull TextView tvNameStory) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.ivAnhd = ivAnhd;
    this.tvNameStory = tvNameStory;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCellBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCellBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_cell, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCellBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.ivAnhd;
      ImageView ivAnhd = ViewBindings.findChildViewById(rootView, id);
      if (ivAnhd == null) {
        break missingId;
      }

      id = R.id.tvNameStory;
      TextView tvNameStory = ViewBindings.findChildViewById(rootView, id);
      if (tvNameStory == null) {
        break missingId;
      }

      return new ActivityCellBinding((ConstraintLayout) rootView, cardView, ivAnhd, tvNameStory);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
