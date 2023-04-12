// Generated by view binder compiler. Do not edit!
package com.example.thuetruyenonline.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.thuetruyenonline.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDetailStoryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView addToLib;

  @NonNull
  public final TextView allChapters;

  @NonNull
  public final ImageView backgroundArt;

  @NonNull
  public final LinearLayout buttons;

  @NonNull
  public final RelativeLayout divider;

  @NonNull
  public final ImageView ivmangaArt;

  @NonNull
  public final LinearLayout mangaArtLayout;

  @NonNull
  public final AppCompatRatingBar rating;

  @NonNull
  public final TextView tagList;

  @NonNull
  public final RelativeLayout topSection;

  @NonNull
  public final TextView tvGioiThieu;

  @NonNull
  public final TextView tvTacGia;

  @NonNull
  public final TextView tvView;

  @NonNull
  public final ImageView tvig;

  @NonNull
  public final TextView tvtitle;

  private ActivityDetailStoryBinding(@NonNull ConstraintLayout rootView, @NonNull TextView addToLib,
      @NonNull TextView allChapters, @NonNull ImageView backgroundArt,
      @NonNull LinearLayout buttons, @NonNull RelativeLayout divider, @NonNull ImageView ivmangaArt,
      @NonNull LinearLayout mangaArtLayout, @NonNull AppCompatRatingBar rating,
      @NonNull TextView tagList, @NonNull RelativeLayout topSection, @NonNull TextView tvGioiThieu,
      @NonNull TextView tvTacGia, @NonNull TextView tvView, @NonNull ImageView tvig,
      @NonNull TextView tvtitle) {
    this.rootView = rootView;
    this.addToLib = addToLib;
    this.allChapters = allChapters;
    this.backgroundArt = backgroundArt;
    this.buttons = buttons;
    this.divider = divider;
    this.ivmangaArt = ivmangaArt;
    this.mangaArtLayout = mangaArtLayout;
    this.rating = rating;
    this.tagList = tagList;
    this.topSection = topSection;
    this.tvGioiThieu = tvGioiThieu;
    this.tvTacGia = tvTacGia;
    this.tvView = tvView;
    this.tvig = tvig;
    this.tvtitle = tvtitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDetailStoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDetailStoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_detail_story, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDetailStoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_to_lib;
      TextView addToLib = ViewBindings.findChildViewById(rootView, id);
      if (addToLib == null) {
        break missingId;
      }

      id = R.id.all_chapters;
      TextView allChapters = ViewBindings.findChildViewById(rootView, id);
      if (allChapters == null) {
        break missingId;
      }

      id = R.id.background_art;
      ImageView backgroundArt = ViewBindings.findChildViewById(rootView, id);
      if (backgroundArt == null) {
        break missingId;
      }

      id = R.id.buttons;
      LinearLayout buttons = ViewBindings.findChildViewById(rootView, id);
      if (buttons == null) {
        break missingId;
      }

      id = R.id.divider;
      RelativeLayout divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.ivmanga_art;
      ImageView ivmangaArt = ViewBindings.findChildViewById(rootView, id);
      if (ivmangaArt == null) {
        break missingId;
      }

      id = R.id.manga_art_layout;
      LinearLayout mangaArtLayout = ViewBindings.findChildViewById(rootView, id);
      if (mangaArtLayout == null) {
        break missingId;
      }

      id = R.id.rating;
      AppCompatRatingBar rating = ViewBindings.findChildViewById(rootView, id);
      if (rating == null) {
        break missingId;
      }

      id = R.id.tag_list;
      TextView tagList = ViewBindings.findChildViewById(rootView, id);
      if (tagList == null) {
        break missingId;
      }

      id = R.id.top_section;
      RelativeLayout topSection = ViewBindings.findChildViewById(rootView, id);
      if (topSection == null) {
        break missingId;
      }

      id = R.id.tvGioiThieu;
      TextView tvGioiThieu = ViewBindings.findChildViewById(rootView, id);
      if (tvGioiThieu == null) {
        break missingId;
      }

      id = R.id.tvTacGia;
      TextView tvTacGia = ViewBindings.findChildViewById(rootView, id);
      if (tvTacGia == null) {
        break missingId;
      }

      id = R.id.tvView;
      TextView tvView = ViewBindings.findChildViewById(rootView, id);
      if (tvView == null) {
        break missingId;
      }

      id = R.id.tvig;
      ImageView tvig = ViewBindings.findChildViewById(rootView, id);
      if (tvig == null) {
        break missingId;
      }

      id = R.id.tvtitle;
      TextView tvtitle = ViewBindings.findChildViewById(rootView, id);
      if (tvtitle == null) {
        break missingId;
      }

      return new ActivityDetailStoryBinding((ConstraintLayout) rootView, addToLib, allChapters,
          backgroundArt, buttons, divider, ivmangaArt, mangaArtLayout, rating, tagList, topSection,
          tvGioiThieu, tvTacGia, tvView, tvig, tvtitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}