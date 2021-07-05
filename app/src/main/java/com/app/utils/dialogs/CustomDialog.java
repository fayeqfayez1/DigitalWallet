package com.app.utils.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.app.R;
import com.app.databinding.FragmentCustomDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomDialog extends BottomSheetDialogFragment {
    private String title;
    private String msg;
    private String okMsg;
    private String cancelMsg;
    private Boolean isCancelable = true;
    private onClickListener listener;
    private OnCloseClick onCloseClick;
    private FragmentCustomDialogBinding binding;

    public CustomDialog() {
        // Required empty public constructor
    }

    public static CustomDialog newInstance(String title, String msg, String okMsg, String cancelMsg, Boolean isCancelable) {
        CustomDialog fragment = new CustomDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", msg);
        args.putString("okMsg", okMsg);
        args.putString("cancelMsg", cancelMsg);
        if (isCancelable == null) isCancelable = true;
        args.putBoolean("isCancelable", isCancelable);
        fragment.setArguments(args);
        return fragment;
    }

    public static CustomDialog newInstance(String title, String msg, String okMsg, String cancelMsg) {
        CustomDialog fragment = new CustomDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", msg);
        args.putString("okMsg", okMsg);
        args.putString("cancelMsg", cancelMsg);
        args.putBoolean("isCancelable", TextUtils.isEmpty(cancelMsg));
        fragment.setArguments(args);
        return fragment;
    }

    private void getArgumentsData() {
        this.title = getArguments().getString("title");
        this.msg = getArguments().getString("msg");
        this.okMsg = getArguments().getString("okMsg");
        this.cancelMsg = getArguments().getString("cancelMsg");
        this.isCancelable = getArguments().getBoolean("isCancelable");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getArgumentsData();

        setCancelable(isCancelable);

        if (TextUtils.isEmpty(title)) {
            binding.tvTitle.setVisibility(View.GONE);
        } else binding.tvTitle.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(okMsg)) {
            binding.btnOk.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(cancelMsg)) {
            binding.btnCancel.setVisibility(View.GONE);
        }

        binding.tvTitle.setText(title);
        binding.btnOk.setText(okMsg);
        binding.btnCancel.setText(cancelMsg);
        binding.tvMsg.setText(msg);

        binding.btnOk.setOnClickListener(view1 -> {
            dismiss();
            if (listener != null) listener.onOkClick();
            else if (onCloseClick != null) onCloseClick.onCloseClick();
        });

        binding.btnCancel.setOnClickListener(view1 -> {
            dismiss();
            listener.onCancelClick();
        });

        return view;

    }

    public interface onClickListener {
        void onOkClick();

        void onCancelClick();
    }

    public interface OnCloseClick {
        void onCloseClick();

    }

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public void setCloseListener(OnCloseClick listener) {
        this.onCloseClick = listener;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.width = (int) (getActivity().getWindowManager().getDefaultDisplay().getWidth() * 0.9);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                dismiss();
                return true;
            } else return false;
        });
    }
}
