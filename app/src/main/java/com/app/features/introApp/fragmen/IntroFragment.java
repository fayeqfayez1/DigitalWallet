package com.app.features.introApp.fragmen;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.R;
import com.app.databinding.FragmentIntroBinding;
import com.app.network.firebase.model.SliderData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class IntroFragment extends Fragment {

    private FragmentIntroBinding binding;

    public IntroFragment() {
        // Required empty public constructor
    }

    private static final String FEATURE_BEAN = "featureBean";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private int mPage;
    private SliderData mFeatureBean;

    public static IntroFragment newInstance(SliderData featureBean, int page, int size) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putSerializable(FEATURE_BEAN, featureBean);
        b.putInt(PAGE, page);
        b.putInt(SIZE, size);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(FEATURE_BEAN))
            throw new RuntimeException("Fragment must contain a \"" + FEATURE_BEAN + "\" argument!");
        mFeatureBean = (SliderData) getArguments().getSerializable(FEATURE_BEAN);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);
        if (!getArguments().containsKey(SIZE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIntroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        view.setTag(mPage);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = "";
        String desc = "";
        title = mFeatureBean.getTitle();
        desc = mFeatureBean.getDescription();

        if (TextUtils.isEmpty(mFeatureBean.getImage())) {
            Glide.with(getContext()).load(R.drawable.image_placeholder).into(binding.ivImg);
            binding.progress.setVisibility(View.GONE);
        } else {
            Glide.with(getContext()).load(mFeatureBean.getImage()).placeholder(R.drawable.image_placeholder)
                    .centerCrop()
                    .listener(new RequestListener<Drawable>() {
                        @SuppressLint("CheckResult")
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(getContext()).load(R.drawable.image_placeholder);
                            binding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progress.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(binding.ivImg);
        }
        binding.tvTitle.setText(title);
        binding.tvDescription.setText(desc);
    }
}