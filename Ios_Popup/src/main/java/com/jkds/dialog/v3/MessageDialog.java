package com.jkds.dialog.v3;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jkds.dialog.R;
import com.jkds.dialog.interfaces.OnBackClickListener;
import com.jkds.dialog.interfaces.OnDialogButtonClickListener;
import com.jkds.dialog.interfaces.OnDismissListener;
import com.jkds.dialog.interfaces.OnShowListener;
import com.jkds.dialog.util.BaseDialog;
import com.jkds.dialog.util.DialogSettings;
import com.jkds.dialog.util.TextInfo;
import com.jkds.dialog.util.view.MaxHeightLayout;

import java.lang.ref.WeakReference;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2019/3/29 16:43
 */
public class MessageDialog extends BaseDialog {

    protected int buttonOrientation;

    protected OnDialogButtonClickListener onOkButtonClickListener;
    protected OnDialogButtonClickListener onCancelButtonClickListener;
    protected OnDialogButtonClickListener onOtherButtonClickListener;

    protected Drawable okButtonDrawable;
    protected Drawable cancelButtonDrawable;
    protected Drawable otherButtonDrawable;

    protected String title = "提示";
    protected String message = "提示信息";
    protected String okButton = "确定";
    protected String cancelButton;
    protected String otherButton;


    protected RelativeLayout boxRoot;
    protected RelativeLayout bkg;
    protected TextView txtDialogTitle;
    protected TextView txtDialogTip;
    protected RelativeLayout boxCustom;
    protected EditText txtInput;
    protected MaxHeightLayout boxInput;
    protected ImageView splitHorizontal;
    protected LinearLayout boxButton;
    protected TextView btnSelectNegative;
    protected ImageView splitVertical1;
    protected TextView btnSelectOther;
    protected ImageView splitVertical2;
    protected TextView btnSelectPositive;

    protected MessageDialog() {
    }

    public static MessageDialog build(@NonNull AppCompatActivity context) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = new MessageDialog();
            messageDialog.log("装载对话框: " + messageDialog.toString());
            messageDialog.context = new WeakReference<>(context);

            messageDialog.okButtonDrawable = DialogSettings.okButtonDrawable;
            messageDialog.cancelButtonDrawable = DialogSettings.cancelButtonDrawable;
            messageDialog.otherButtonDrawable = DialogSettings.otherButtonDrawable;

            switch (messageDialog.style) {
                case STYLE_IOS:
                    messageDialog.build(messageDialog, R.layout.dialog_select_ios);
                    break;
            }
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, String title, String message) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(context, title, message, null, null, null);
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, int titleResId, int messageResId) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(context,
                    context.getString(titleResId),
                    context.getString(messageResId),
                    null, null, null
            );
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, String title, String message, String okButton) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(context, title, message, okButton, null, null);
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, int titleResId, int messageResId, int okButtonResId) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(context,
                    context.getString(titleResId),
                    context.getString(messageResId),
                    context.getString(okButtonResId)
                    , null, null
            );
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, String title, String message, String okButton, String cancelButton) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(context, title, message, okButton, cancelButton, null);
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, int titleResId, int messageResId, int okButtonResId, int cancelButtonResId) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(
                    context,
                    context.getString(titleResId),
                    context.getString(messageResId),
                    context.getString(okButtonResId),
                    context.getString(cancelButtonResId),
                    null
            );
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, String title, String message, String okButton, String cancelButton, String otherButton) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = build(context);

            messageDialog.title = title;
            if (okButton != null) {
                messageDialog.okButton = okButton;
            }
            messageDialog.message = message;
            messageDialog.cancelButton = cancelButton;
            messageDialog.otherButton = otherButton;

            messageDialog.showDialog();
            return messageDialog;
        }
    }

    public static MessageDialog show(@NonNull AppCompatActivity context, int titleResId, int messageResId, int okButtonResId, int cancelButtonResId, int otherButtonResId) {
        synchronized (MessageDialog.class) {
            MessageDialog messageDialog = show(
                    context,
                    context.getString(titleResId),
                    context.getString(messageResId),
                    context.getString(okButtonResId),
                    context.getString(cancelButtonResId),
                    context.getString(otherButtonResId)
            );
            return messageDialog;
        }
    }

    protected AlertDialog materialAlertDialog;
    protected View rootView;

    @Override
    public void bindView(View rootView) {
        log("启动对话框 -> " + toString());
        if (boxCustom != null) {
            boxCustom.removeAllViews();
        }
//        if (style == DialogSettings.STYLE.STYLE_MATERIAL) {
//            materialAlertDialog = (AlertDialog) dialog.get().getDialog();
//        } else {
        if (rootView != null) {
            this.rootView = rootView;
            bkg = rootView.findViewById(R.id.bkg);
            boxRoot = rootView.findViewById(R.id.box_root);
            txtDialogTitle = rootView.findViewById(R.id.txt_dialog_title);
            txtDialogTip = rootView.findViewById(R.id.txt_dialog_tip);
            //设置textview可以滑动
            txtDialogTip.setMovementMethod(ScrollingMovementMethod.getInstance());
            boxCustom = rootView.findViewById(R.id.box_custom);
            txtInput = rootView.findViewById(R.id.txt_input);
            splitHorizontal = rootView.findViewById(R.id.split_horizontal);
            boxButton = rootView.findViewById(R.id.box_button);
            btnSelectNegative = rootView.findViewById(R.id.btn_selectNegative);
            splitVertical1 = rootView.findViewById(R.id.split_vertical1);
            btnSelectOther = rootView.findViewById(R.id.btn_selectOther);
            splitVertical2 = rootView.findViewById(R.id.split_vertical2);
            btnSelectPositive = rootView.findViewById(R.id.btn_selectPositive);
            boxInput = rootView.findViewById(R.id.box_input);
        }
//        }

        refreshView();
        if (onShowListener != null) {
            onShowListener.onShow(this);
        }
    }

    @Override
    public void refreshView() {
        if (txtDialogTitle != null) {
            if (title == null) {
                txtDialogTitle.setVisibility(View.GONE);
            } else {
                txtDialogTitle.setVisibility(View.VISIBLE);
                txtDialogTitle.setText(title);
            }
        }
        if (txtDialogTip != null) {
            if (message == null) {
                txtDialogTip.setVisibility(View.GONE);
            } else {
                txtDialogTip.setVisibility(View.VISIBLE);
                txtDialogTip.setText(message);
            }
        }

        if (rootView != null || materialAlertDialog != null) {
            final int bkgResId;
            switch (style) {
                case STYLE_IOS:
                    if (theme == DialogSettings.THEME.LIGHT) {
                        bkgResId = R.drawable.rect_selectdialog_ios_bkg_light;
                    } else {
                        bkgResId = R.drawable.rect_selectdialog_ios_bkg_dark;
                        txtDialogTitle.setTextColor(Color.WHITE);
                        txtDialogTip.setTextColor(Color.WHITE);
                        splitHorizontal.setBackgroundColor(context.get().getResources().getColor(R.color.dialogSplitIOSDark));
                        splitVertical1.setBackgroundColor(context.get().getResources().getColor(R.color.dialogSplitIOSDark));
                        splitVertical2.setBackgroundColor(context.get().getResources().getColor(R.color.dialogSplitIOSDark));
                        txtInput.setBackgroundResource(R.drawable.editbox_dialog_bkg_ios_dark);

                        btnSelectPositive.setBackgroundResource(R.drawable.button_dialog_ios_right_dark);
                        btnSelectOther.setBackgroundResource(R.drawable.button_menu_ios_center_dark);
                        btnSelectNegative.setBackgroundResource(R.drawable.button_dialog_ios_left_dark);
                    }
                    if (backgroundResId != -1) {
                        bkg.setBackgroundResource(backgroundResId);
                    } else {

                        bkg.setBackgroundResource(bkgResId);

                    }
                    if (customView != null) {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        boxCustom.removeAllViews();
                        boxCustom.addView(customView, lp);
                        if (onBindView != null) {
                            onBindView.onBind(this, customView);
                        }
                        boxCustom.setVisibility(View.VISIBLE);
                    } else {
                        boxCustom.setVisibility(View.GONE);
                    }
                    refreshTextViews();
                    break;

            }
        }

        if (btnSelectPositive != null) {
            btnSelectPositive.setText(okButton);
            if (okButtonDrawable != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btnSelectPositive.setBackground(okButtonDrawable);
                } else {
                    btnSelectPositive.setBackgroundDrawable(okButtonDrawable);
                }
            }

            btnSelectPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOkButtonClickListener != null) {
                        if (!onOkButtonClickListener.onClick(MessageDialog.this, v)) {
                            doDismiss();
                        }
                    } else {
                        doDismiss();
                    }
                }
            });
        }
        if (btnSelectNegative != null) {
            if (isNull(cancelButton)) {
                //单按钮情况
                btnSelectNegative.setVisibility(View.GONE);
                if (style == DialogSettings.STYLE.STYLE_IOS) {
                    splitVertical2.setVisibility(View.GONE);
                    if (theme == DialogSettings.THEME.LIGHT) {
                        btnSelectPositive.setBackgroundResource(R.drawable.button_dialog_ios_bottom_light);
                    } else {
                        btnSelectPositive.setBackgroundResource(R.drawable.button_dialog_ios_bottom_dark);
                    }
                }
            } else {
                btnSelectNegative.setText(cancelButton);
                if (cancelButtonDrawable != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btnSelectNegative.setBackground(cancelButtonDrawable);
                    } else {
                        btnSelectNegative.setBackgroundDrawable(cancelButtonDrawable);
                    }
                }

                btnSelectNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCancelButtonClickListener != null) {
                            if (!onCancelButtonClickListener.onClick(MessageDialog.this, v)) {
                                doDismiss();
                            }
                        } else {
                            doDismiss();
                        }
                    }
                });
            }
        }
        if (btnSelectOther != null) {
            if (!isNull(otherButton)) {
                if (splitVertical1 != null) {
                    splitVertical1.setVisibility(View.VISIBLE);
                }
                btnSelectOther.setVisibility(View.VISIBLE);
                btnSelectOther.setText(otherButton);
            }
            if (otherButtonDrawable != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btnSelectOther.setBackground(otherButtonDrawable);
                } else {
                    btnSelectOther.setBackgroundDrawable(otherButtonDrawable);
                }
            }

            btnSelectOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOtherButtonClickListener != null) {
                        if (!onOtherButtonClickListener.onClick(MessageDialog.this, v)) {
                            doDismiss();
                        }
                    } else {
                        doDismiss();
                    }
                }
            });
        }
        if (boxButton != null) {
            boxButton.setOrientation(buttonOrientation);
            if (buttonOrientation == LinearLayout.VERTICAL) {
                //竖排排列的情况下
                boxButton.removeAllViews();

                if (style == DialogSettings.STYLE.STYLE_IOS) {
                    boxButton.addView(btnSelectPositive);
                    boxButton.addView(splitVertical2);
                    boxButton.addView(btnSelectNegative);
                    boxButton.addView(splitVertical1);
                    boxButton.addView(btnSelectOther);

                    if (okButtonDrawable == null && cancelButtonDrawable == null && otherButtonDrawable == null) {
                        if (theme == DialogSettings.THEME.LIGHT) {
                            btnSelectPositive.setBackgroundResource(R.drawable.button_menu_ios_center_light);
                            if (btnSelectOther.getVisibility() == View.GONE) {
                                btnSelectNegative.setBackgroundResource(R.drawable.button_dialog_ios_bottom_light);
                            } else {
                                btnSelectNegative.setBackgroundResource(R.drawable.button_menu_ios_center_light);
                                btnSelectOther.setBackgroundResource(R.drawable.button_dialog_ios_bottom_light);
                            }
                        } else {
                            btnSelectPositive.setBackgroundResource(R.drawable.button_menu_ios_center_dark);
                            if (btnSelectOther.getVisibility() == View.GONE) {
                                btnSelectNegative.setBackgroundResource(R.drawable.button_dialog_ios_bottom_dark);
                            } else {
                                btnSelectNegative.setBackgroundResource(R.drawable.button_menu_ios_center_dark);
                                btnSelectOther.setBackgroundResource(R.drawable.button_dialog_ios_bottom_dark);
                            }
                        }
                    }

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    splitVertical1.setLayoutParams(lp);
                    splitVertical2.setLayoutParams(lp);
                } else {
                    boxButton.addView(btnSelectPositive);
                    boxButton.addView(btnSelectNegative);
                    boxButton.addView(btnSelectOther);

//                    if (style == DialogSettings.STYLE.STYLE_MIUI) {
//                        if (okButtonDrawable == null && cancelButtonDrawable == null && otherButtonDrawable == null && theme == DialogSettings.THEME.LIGHT) {
//                            btnSelectPositive.setBackgroundResource(R.drawable.button_selectdialog_miui_blue);
//                            btnSelectNegative.setBackgroundResource(R.drawable.button_selectdialog_miui_gray);
//                            btnSelectOther.setBackgroundResource(R.drawable.button_selectdialog_miui_gray);
//                        }
//
//                        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btnSelectOther.getLayoutParams();
//                        lp.setMargins(0, dip2px(10), 0, 0);
//                        btnSelectOther.setLayoutParams(lp);
//                        btnSelectNegative.setLayoutParams(lp);
//                        btnSelectPositive.setLayoutParams(lp);
//                    } else {
                    if (okButtonDrawable == null && cancelButtonDrawable == null && otherButtonDrawable == null && theme == DialogSettings.THEME.LIGHT) {
                        btnSelectPositive.setBackgroundResource(R.drawable.button_selectdialog_kongzue_white);
                        btnSelectNegative.setBackgroundResource(R.drawable.button_selectdialog_kongzue_white);
                        btnSelectOther.setBackgroundResource(R.drawable.button_selectdialog_kongzue_white);
                    }

                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btnSelectOther.getLayoutParams();
                    lp.setMargins(0, 1, 0, 0);
                    btnSelectOther.setLayoutParams(lp);
                    btnSelectNegative.setLayoutParams(lp);
                    btnSelectPositive.setLayoutParams(lp);
//                    }
                }

            }
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener blurViewRefreshLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (!isShow) {
                if (bkg != null) {
                    bkg.getViewTreeObserver().removeOnGlobalLayoutListener(blurViewRefreshLayoutListener);
                }

            }
        }
    };

    @Override
    public void show() {
        showDialog();
    }

    protected void refreshTextViews() {
        useTextInfo(txtDialogTitle, titleTextInfo);
        useTextInfo(txtDialogTip, messageTextInfo);
        useTextInfo(btnSelectNegative, buttonTextInfo);
        useTextInfo(btnSelectOther, buttonTextInfo);
        useTextInfo(btnSelectPositive, buttonTextInfo);
        useTextInfo(btnSelectPositive, buttonPositiveTextInfo);
    }

    @Override
    protected void showDialog() {
        if (style == DialogSettings.STYLE.STYLE_IOS) {
            super.showDialog();
        }
//        else if (style == DialogSettings.STYLE.STYLE_MATERIAL) {
//            if (theme == DialogSettings.THEME.LIGHT) {
//                super.showDialog(R.style.LightDialogWithShadow);
//            } else {
//                super.showDialog(R.style.DarkDialogWithShadow);
//            }
//        }
        else {
            super.showDialog(R.style.LightDialogWithShadow);
        }
    }

    public String getTitle() {
        return title;
    }

    public MessageDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MessageDialog setTitle(int titleResId) {
        this.title = context.get().getString(titleResId);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageDialog setMessage(String content) {
        this.message = content;
        return this;
    }

    public MessageDialog setMessage(int contentResId) {
        this.message = context.get().getString(contentResId);
        return this;
    }

    public String getOkButton() {
        return okButton;
    }

    public MessageDialog setOkButton(String okButton) {
        this.okButton = okButton;
        refreshView();
        return this;
    }

    public MessageDialog setOkButton(int okButtonResId) {
        setOkButton(context.get().getString(okButtonResId));
        return this;
    }

    public MessageDialog setOkButton(String okButton, OnDialogButtonClickListener onOkButtonClickListener) {
        this.okButton = okButton;
        this.onOkButtonClickListener = onOkButtonClickListener;
        refreshView();
        return this;
    }

    public MessageDialog setOkButton(int okButtonResId, OnDialogButtonClickListener onOkButtonClickListener) {
        setOkButton(context.get().getString(okButtonResId), onOkButtonClickListener);
        return this;
    }

    public MessageDialog setOkButton(OnDialogButtonClickListener onOkButtonClickListener) {
        this.onOkButtonClickListener = onOkButtonClickListener;
        refreshView();
        return this;
    }

    public String getCancelButton() {
        return cancelButton;
    }

    public MessageDialog setCancelButton(String cancelButton) {
        this.cancelButton = cancelButton;
        refreshView();
        return this;
    }

    public MessageDialog setCancelButton(int cancelButtonResId) {
        setCancelButton(context.get().getString(cancelButtonResId));
        return this;
    }

    public MessageDialog setCancelButton(String cancelButton, OnDialogButtonClickListener onCancelButtonClickListener) {
        this.cancelButton = cancelButton;
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        refreshView();
        return this;
    }

    public MessageDialog setCancelButton(int cancelButtonResId, OnDialogButtonClickListener onCancelButtonClickListener) {
        setCancelButton(context.get().getString(cancelButtonResId), onCancelButtonClickListener);
        return this;
    }

    public MessageDialog setCancelButton(OnDialogButtonClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        refreshView();
        return this;
    }

    public String getOtherButton() {
        return otherButton;
    }

    public MessageDialog setOtherButton(String otherButton) {
        this.otherButton = otherButton;
        refreshView();
        return this;
    }

    public MessageDialog setOtherButton(int otherButtonResId) {
        setOtherButton(context.get().getString(otherButtonResId));
        return this;
    }

    public MessageDialog setOtherButton(String otherButton, OnDialogButtonClickListener onOtherButtonClickListener) {
        this.otherButton = otherButton;
        this.onOtherButtonClickListener = onOtherButtonClickListener;
        refreshView();
        return this;
    }

    public MessageDialog setOtherButton(int otherButtonResId, OnDialogButtonClickListener onOtherButtonClickListener) {
        setOtherButton(context.get().getString(otherButtonResId), onOtherButtonClickListener);
        return this;
    }

    public MessageDialog setOtherButton(OnDialogButtonClickListener onOtherButtonClickListener) {
        this.onOtherButtonClickListener = onOtherButtonClickListener;
        refreshView();
        return this;
    }

    public OnDialogButtonClickListener getOnOkButtonClickListener() {
        return onOkButtonClickListener;
    }

    public MessageDialog setOnOkButtonClickListener(OnDialogButtonClickListener onOkButtonClickListener) {
        this.onOkButtonClickListener = onOkButtonClickListener;
        refreshView();
        return this;
    }

    public OnDialogButtonClickListener getOnCancelButtonClickListener() {
        return onCancelButtonClickListener;
    }

    public MessageDialog setOnCancelButtonClickListener(OnDialogButtonClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        refreshView();
        return this;
    }

    public OnDialogButtonClickListener getOnOtherButtonClickListener() {
        return onOtherButtonClickListener;
    }

    public MessageDialog setOnOtherButtonClickListener(OnDialogButtonClickListener onOtherButtonClickListener) {
        this.onOtherButtonClickListener = onOtherButtonClickListener;
        refreshView();
        return this;
    }

    public MessageDialog setOkButtonDrawable(@DrawableRes int okButtonDrawableResId) {
        this.okButtonDrawable = ContextCompat.getDrawable(context.get(), okButtonDrawableResId);
        refreshView();
        return this;
    }

    public MessageDialog setOkButtonDrawable(Drawable okButtonDrawable) {
        this.okButtonDrawable = okButtonDrawable;
        refreshView();
        return this;
    }

    public MessageDialog setCancelButtonDrawable(@DrawableRes int okButtonDrawableResId) {
        this.cancelButtonDrawable = ContextCompat.getDrawable(context.get(), okButtonDrawableResId);
        refreshView();
        return this;
    }

    public MessageDialog setCancelButtonDrawable(Drawable cancelButtonDrawable) {
        this.cancelButtonDrawable = cancelButtonDrawable;
        refreshView();
        return this;
    }

    public MessageDialog setOtherButtonDrawable(@DrawableRes int okButtonDrawableResId) {
        this.otherButtonDrawable = ContextCompat.getDrawable(context.get(), okButtonDrawableResId);
        refreshView();
        return this;
    }

    public MessageDialog setOtherButtonDrawable(Drawable otherButtonDrawable) {
        this.otherButtonDrawable = otherButtonDrawable;
        refreshView();
        return this;
    }

    public int getButtonOrientation() {
        return buttonOrientation;
    }

    public MessageDialog setButtonOrientation(int buttonOrientation) {
        this.buttonOrientation = buttonOrientation;
        refreshView();
        return this;
    }

    //其他
    public OnDismissListener getOnDismissListener() {
        return onDismissListener == null ? new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        } : onDismissListener;
    }

    public MessageDialog setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public OnShowListener getOnShowListener() {
        return onShowListener == null ? new OnShowListener() {
            @Override
            public void onShow(BaseDialog dialog) {

            }
        } : onShowListener;
    }

    public MessageDialog setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

    public DialogSettings.STYLE getStyle() {
        return style;
    }

    public MessageDialog setStyle(DialogSettings.STYLE style) {
        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setStyle(...) 来修改对话框主题或风格。");
            return this;
        }

        this.style = style;
        switch (this.style) {
            case STYLE_IOS:
                build(this, R.layout.dialog_select_ios);
                break;

        }

        return this;
    }

    public DialogSettings.THEME getTheme() {
        return theme;
    }

    public MessageDialog setTheme(DialogSettings.THEME theme) {

        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setTheme(...) 来修改对话框主题或风格。");
            return this;
        }

        this.theme = theme;
        refreshView();
        return this;
    }

    public boolean getCancelable() {
        return cancelable == BOOLEAN.TRUE;
    }

    public MessageDialog setCancelable(boolean enable) {
        this.cancelable = enable ? BOOLEAN.TRUE : BOOLEAN.FALSE;
        if (dialog != null) {
            dialog.get().setCancelable(cancelable == BOOLEAN.TRUE);
        }
        return this;
    }


    public TextInfo getTitleTextInfo() {
        return titleTextInfo;
    }

    public MessageDialog setTitleTextInfo(TextInfo titleTextInfo) {
        this.titleTextInfo = titleTextInfo;
        refreshView();
        return this;
    }

    public TextInfo getMessageTextInfo() {
        return messageTextInfo;
    }

    public MessageDialog setMessageTextInfo(TextInfo messageTextInfo) {
        this.messageTextInfo = messageTextInfo;
        refreshView();
        return this;
    }

    public TextInfo getButtonTextInfo() {
        return buttonTextInfo;
    }

    public MessageDialog setButtonTextInfo(TextInfo buttonTextInfo) {
        this.buttonTextInfo = buttonTextInfo;
        refreshView();
        return this;
    }

    public TextInfo getButtonPositiveTextInfo() {
        return buttonPositiveTextInfo;
    }

    public MessageDialog setButtonPositiveTextInfo(TextInfo buttonPositiveTextInfo) {
        this.buttonPositiveTextInfo = buttonPositiveTextInfo;
        refreshView();
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public MessageDialog setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        refreshView();
        return this;
    }

    public View getCustomView() {
        return customView;
    }

    public MessageDialog setCustomView(View customView) {
        this.customView = customView;
        refreshView();
        return this;
    }

    private OnBindView onBindView;

    public MessageDialog setCustomView(int customViewLayoutId, OnBindView onBindView) {
        customView = LayoutInflater.from(context.get()).inflate(customViewLayoutId, null);
        this.onBindView = onBindView;
        refreshView();
        return this;
    }

    public interface OnBindView {
        void onBind(MessageDialog dialog, View v);
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public MessageDialog setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
        refreshView();
        return this;
    }

    public MessageDialog setCustomDialogStyleId(int customDialogStyleId) {
        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setTheme(...) 来修改对话框主题或风格。");
            return this;
        }
        this.customDialogStyleId = customDialogStyleId;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    public OnBackClickListener getOnBackClickListener() {
        return onBackClickListener;
    }

    public MessageDialog setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
        return this;
    }

    public ALIGN getAlign() {
        return align;
    }

    public MessageDialog setAlign(ALIGN align) {
        this.align = align;
        return this;
    }
}