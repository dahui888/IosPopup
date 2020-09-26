package com.jkds.dialog.v3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jkds.dialog.R;
import com.jkds.dialog.interfaces.OnBackClickListener;
import com.jkds.dialog.interfaces.OnDialogButtonClickListener;
import com.jkds.dialog.interfaces.OnDismissListener;
import com.jkds.dialog.interfaces.OnMenuItemClickListener;
import com.jkds.dialog.interfaces.OnShowListener;
import com.jkds.dialog.util.BaseDialog;
import com.jkds.dialog.util.DialogSettings;
import com.jkds.dialog.util.TextInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.jkds.dialog.util.DialogSettings.menuTitleInfo;


/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2019/4/12 18:33
 */
public class BottomMenu extends BaseDialog {

    private BaseAdapter customAdapter;      //允许用户自定义 Menu 的 Adapter

    private List<String> menuTextList;
    private String title;
    private String cancelButtonText = DialogSettings.defaultCancelButtonText;
    private boolean showCancelButton = true;
    protected OnMenuItemClickListener onMenuItemClickListener;
    protected OnDialogButtonClickListener onCancelButtonClickListener;

    private TextInfo menuTitleTextInfo;
    private TextInfo cancelButtonTextInfo;

    private LinearLayout boxRoot;
    private LinearLayout boxBody;
    private RelativeLayout boxList;
    private TextView txtTitle;
    private RelativeLayout boxCustom;
    private ImageView titleSplitLine;
    private ListView listMenu;
    private ViewGroup boxCancel;
    private TextView btnCancel;
    private TextInfo menuTextInfo;
    private ImageView imgTab;
    private ImageView imgSplit;

    private BottomMenu() {
    }

    public static BottomMenu build(@NonNull AppCompatActivity context) {
        synchronized (BottomMenu.class) {
            BottomMenu bottomMenu = new BottomMenu();
            bottomMenu.log("装载底部菜单: " + bottomMenu.toString());
            bottomMenu.context = new WeakReference<>(context);

            switch (bottomMenu.style) {
                case STYLE_IOS:
                    bottomMenu.build(bottomMenu, R.layout.bottom_menu_ios);
                    break;

            }
            return bottomMenu;
        }
    }

    public static BottomMenu show(@NonNull AppCompatActivity context, List<String> menuTextList, OnMenuItemClickListener onMenuItemClickListener) {
        BottomMenu bottomMenu = build(context);
        bottomMenu.menuTextList = menuTextList;
        bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
        bottomMenu.showDialog();
        return bottomMenu;
    }

    public static BottomMenu show(@NonNull AppCompatActivity context, BaseAdapter customAdapter, OnMenuItemClickListener onMenuItemClickListener) {
        BottomMenu bottomMenu = build(context);
        bottomMenu.customAdapter = customAdapter;
        bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
        bottomMenu.showDialog();
        return bottomMenu;
    }

    public static BottomMenu show(@NonNull AppCompatActivity context, String title, List<String> menuTextList, OnMenuItemClickListener onMenuItemClickListener) {
        BottomMenu bottomMenu = build(context);
        bottomMenu.menuTextList = menuTextList;
        bottomMenu.title = title;
        bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
        bottomMenu.showDialog();
        return bottomMenu;
    }

    public static BottomMenu show(@NonNull AppCompatActivity context, String title, BaseAdapter customAdapter, OnMenuItemClickListener onMenuItemClickListener) {
        BottomMenu bottomMenu = build(context);
        bottomMenu.customAdapter = customAdapter;
        bottomMenu.title = title;
        bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
        bottomMenu.showDialog();
        return bottomMenu;
    }

    public static BottomMenu show(@NonNull AppCompatActivity context, String[] menuTexts, OnMenuItemClickListener onMenuItemClickListener) {
        BottomMenu bottomMenu = build(context);
        List<String> list = new ArrayList<>();
        for (String s : menuTexts) {
            list.add(s);
        }
        bottomMenu.menuTextList = list;
        bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
        bottomMenu.showDialog();
        return bottomMenu;
    }

    public static BottomMenu show(@NonNull AppCompatActivity context, String title, String[] menuTexts, OnMenuItemClickListener onMenuItemClickListener) {
        BottomMenu bottomMenu = build(context);
        List<String> list = new ArrayList<>();
        for (String s : menuTexts) {
            list.add(s);
        }
        bottomMenu.menuTextList = list;
        bottomMenu.title = title;
        bottomMenu.onMenuItemClickListener = onMenuItemClickListener;
        bottomMenu.showDialog();
        return bottomMenu;
    }

    private View rootView;

    @Override
    public void bindView(View rootView) {
        log("启动底部菜单 -> " + toString());
        this.rootView = rootView;
        if (boxCustom != null) {
            boxCustom.removeAllViews();
        }
        boxRoot = rootView.findViewById(R.id.box_root);
        boxBody = rootView.findViewById(R.id.box_body);
        boxList = rootView.findViewById(R.id.box_list);
        txtTitle = rootView.findViewById(R.id.txt_title);
        boxCustom = rootView.findViewById(R.id.box_custom);
        titleSplitLine = rootView.findViewById(R.id.title_split_line);
        listMenu = rootView.findViewById(R.id.list_menu);
        boxCancel = rootView.findViewById(R.id.box_cancel);
        btnCancel = rootView.findViewById(R.id.btn_cancel);

        switch (style) {
            case STYLE_IOS:
                final int bkgResId;
                if (theme == DialogSettings.THEME.LIGHT) {
                    bkgResId = R.drawable.rect_menu_bkg_ios;
                    btnCancel.setBackgroundResource(R.drawable.button_menu_ios_light);
                    listMenu.setDivider(new ColorDrawable(context.get().getResources().getColor(R.color.dialogSplitIOSLight)));
                    listMenu.setDividerHeight(1);
                    titleSplitLine.setBackgroundColor(context.get().getResources().getColor(R.color.dialogSplitIOSLight));
                } else {
                    bkgResId = R.drawable.rect_menu_bkg_ios;
                    btnCancel.setBackgroundResource(R.drawable.button_menu_ios_dark);
                    listMenu.setDivider(new ColorDrawable(context.get().getResources().getColor(R.color.dialogSplitIOSDark)));
                    listMenu.setDividerHeight(1);
                    titleSplitLine.setBackgroundColor(context.get().getResources().getColor(R.color.dialogSplitIOSDark));
                }

                boxList.setBackgroundResource(bkgResId);
                boxCancel.setBackgroundResource(bkgResId);
                break;
        }

        refreshView();
        if (onShowListener != null) {
            onShowListener.onShow(this);
        }
    }


    @Override
    public void refreshView() {
        if (cancelButtonTextInfo == null) {
            cancelButtonTextInfo = menuTextInfo;
        }
        if (menuTitleTextInfo == null) {
            menuTitleTextInfo = menuTitleInfo;
        }
        if (menuTextInfo == null) {
            menuTextInfo = DialogSettings.menuTextInfo;
        }
        if (cancelButtonText == null) {
            cancelButtonText = "取消";
        }

        if (rootView != null) {
            btnCancel.setText(cancelButtonText);

            ((ViewGroup) boxBody.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doDismiss();
                }
            });

            switch (style) {
                case STYLE_IOS:
                    if (showCancelButton) {
                        if (boxCancel != null) {
                            boxCancel.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (boxCancel != null) {
                            boxCancel.setVisibility(View.GONE);
                        }
                    }
                    if (customAdapter != null) {
                        menuArrayAdapter = customAdapter;
                    } else {
                        menuArrayAdapter = new IOSMenuArrayAdapter(context.get(), R.layout.item_bottom_menu_ios, menuTextList);
                    }
                    listMenu.setAdapter(menuArrayAdapter);
                    break;
            }
            if (customView != null) {
                boxCustom.removeAllViews();
                boxCustom.addView(customView);
                if (onBindView != null) {
                    onBindView.onBind(this, customView);
                }
                boxCustom.setVisibility(View.VISIBLE);
                if (titleSplitLine != null) {
                    titleSplitLine.setVisibility(View.VISIBLE);
                }
            } else {
                boxCustom.setVisibility(View.GONE);
            }

            if (!isNull(title)) {
                txtTitle.setText(title);
                txtTitle.setVisibility(View.VISIBLE);
                if (titleSplitLine != null) {
                    titleSplitLine.setVisibility(View.VISIBLE);
                }
            }

            listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onMenuItemClickListener != null) {
                        if (customAdapter != null) {
                            onMenuItemClickListener.onClick(customAdapter.getItem(position).toString(), position);
                        } else {
                            onMenuItemClickListener.onClick(menuTextList.get(position), position);
                        }
                    }
                    doDismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCancelButtonClickListener != null) {
                        if (!onCancelButtonClickListener.onClick(BottomMenu.this, btnCancel)) {
                            doDismiss();
                        }
                    } else {
                        doDismiss();
                    }
                }
            });
        }

        useTextInfo(txtTitle, menuTitleTextInfo);
        useTextInfo(btnCancel, cancelButtonTextInfo);
    }

    @Override
    public void show() {
        showDialog();
    }

    private BaseAdapter menuArrayAdapter;

    public class IOSMenuArrayAdapter extends NormalMenuArrayAdapter {

        public IOSMenuArrayAdapter(Context context, int resourceId, List<String> objects) {
            super(context, resourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(resoureId, null);
                viewHolder.textView = convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String text = objects.get(position);
            if (null != text) {
                viewHolder.textView.setText(text);

                useTextInfo(viewHolder.textView, menuTextInfo);

                if (objects.size() == 1) {
                    if (theme == DialogSettings.THEME.LIGHT) {
                        if (title != null && !title.trim().isEmpty()) {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom_light);
                        } else {
                            if (boxCustom.getVisibility() == View.VISIBLE) {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom_light);
                            } else {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_light);
                            }
                        }
                    } else {
                        if (title != null && !title.trim().isEmpty()) {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom_dark);
                        } else {
                            if (boxCustom.getVisibility() == View.VISIBLE) {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom_dark);
                            } else {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_dark);
                            }
                        }
                    }
                } else {
                    if (theme == DialogSettings.THEME.LIGHT) {
                        if (position == 0) {
                            if (title != null && !title.trim().isEmpty()) {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_center_light);
                            } else {
                                if (boxCustom.getVisibility() == View.VISIBLE) {
                                    viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_center_light);
                                } else {
                                    viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_top_light);
                                }
                            }
                        } else if (position == objects.size() - 1) {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom_light);
                        } else {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_center_light);
                        }
                    } else {
                        if (position == 0) {
                            if (title != null && !title.trim().isEmpty()) {
                                viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_center_dark);
                            } else {
                                if (boxCustom.getVisibility() == View.VISIBLE) {
                                    viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_center_dark);
                                } else {
                                    viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_top_dark);
                                }
                            }
                        } else if (position == objects.size() - 1) {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_bottom_dark);
                        } else {
                            viewHolder.textView.setBackgroundResource(R.drawable.button_menu_ios_center_dark);
                        }
                    }
                }
            }

            return convertView;
        }
    }

    public class NormalMenuArrayAdapter extends ArrayAdapter {
        public int resoureId;
        public List<String> objects;
        public Context context;

        public NormalMenuArrayAdapter(Context context, int resourceId, List<String> objects) {
            super(context, resourceId, objects);
            this.objects = objects;
            this.resoureId = resourceId;
            this.context = context;
        }

        public class ViewHolder {
            TextView textView;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public String getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(resoureId, null);
                viewHolder.textView = convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String text = objects.get(position);
            if (null != text) {
                viewHolder.textView.setText(text);

                switch (style) {
                    case STYLE_MATERIAL:
                        if (theme == DialogSettings.THEME.LIGHT) {
                            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.notificationTipTextColorMaterial));
                        } else {
                            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.materialDarkTextColor));
                        }
                        break;
                    case STYLE_KONGZUE:
                        if (theme == DialogSettings.THEME.LIGHT) {
                            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.dark));
                        } else {
                            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.materialDarkTextColor));
                        }
                        break;
                    case STYLE_MIUI:
                        if (theme == DialogSettings.THEME.LIGHT) {
                            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.dark));
                        } else {
                            viewHolder.textView.setTextColor(Color.parseColor("#D3D3D3"));
                        }
                        break;
                }

                useTextInfo(viewHolder.textView, menuTextInfo);
            }

            return convertView;
        }
    }

    //其他设置
    public List<String> getMenuTextList() {
        return menuTextList;
    }

    public BottomMenu setMenuTextList(List<String> menuTextList) {
        this.menuTextList = menuTextList;
        refreshView();
        return this;
    }

    public BottomMenu setMenuTextList(String[] menuTexts) {
        List<String> list = new ArrayList<>();
        for (String s : menuTexts) {
            list.add(s);
        }
        this.menuTextList = list;
        refreshView();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BottomMenu setTitle(String title) {
        this.title = title;
        refreshView();
        return this;
    }

    public BottomMenu setTitle(int titleResId) {
        this.title = context.get().getString(titleResId);
        refreshView();
        return this;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }

    public BottomMenu setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
        refreshView();
        return this;
    }

    public BottomMenu setCancelButtonText(int cancelButtonTextResId) {
        this.cancelButtonText = context.get().getString(cancelButtonTextResId);
        refreshView();
        return this;
    }

    public boolean isShowCancelButton() {
        return showCancelButton;
    }

    public BottomMenu setShowCancelButton(boolean showCancelButton) {
        this.showCancelButton = showCancelButton;
        refreshView();
        return this;
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return onMenuItemClickListener;
    }

    public BottomMenu setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
        refreshView();
        return this;
    }

    public TextInfo getMenuTitleTextInfo() {
        return menuTitleTextInfo;
    }

    public BottomMenu setMenuTitleTextInfo(TextInfo menuTitleTextInfo) {
        this.menuTitleTextInfo = menuTitleTextInfo;
        refreshView();
        return this;
    }

    public TextInfo getMenuTextInfo() {
        return menuTextInfo;
    }

    public BottomMenu setMenuTextInfo(TextInfo menuTextInfo) {
        this.menuTextInfo = menuTextInfo;
        refreshView();
        return this;
    }

    public TextInfo getCancelButtonTextInfo() {
        return cancelButtonTextInfo;
    }

    public BottomMenu setCancelButtonTextInfo(TextInfo cancelButtonTextInfo) {
        this.cancelButtonTextInfo = cancelButtonTextInfo;
        refreshView();
        return this;
    }

    public OnDismissListener getOnDismissListener() {
        return onDismissListener == null ? new OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        } : onDismissListener;
    }

    public BottomMenu setOnDismissListener(OnDismissListener onDismissListener) {
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

    public BottomMenu setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

    public View getCustomView() {
        return customView;
    }

    public BottomMenu setCustomView(View customView) {
        this.customView = customView;
        refreshView();
        return this;
    }

    private OnBindView onBindView;

    public BottomMenu setCustomView(int customViewLayoutId, OnBindView onBindView) {
        customView = LayoutInflater.from(context.get()).inflate(customViewLayoutId, null);
        this.onBindView = onBindView;
        refreshView();
        return this;
    }

    public interface OnBindView {
        void onBind(BottomMenu bottomMenu, View v);
    }

    public DialogSettings.STYLE getStyle() {
        return style;
    }

    public OnDialogButtonClickListener getOnCancelButtonClickListener() {
        return onCancelButtonClickListener;
    }

    public BottomMenu setOnCancelButtonClickListener(OnDialogButtonClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        return this;
    }

    public BottomMenu setStyle(DialogSettings.STYLE style) {
        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setStyle(...) 来修改对话框主题或风格。");
            return this;
        }

        this.style = style;
        switch (this.style) {
            case STYLE_IOS:
                build(this, R.layout.bottom_menu_ios);
                break;
        }

        return this;
    }

    public DialogSettings.THEME getTheme() {
        return theme;
    }

    public BottomMenu setTheme(DialogSettings.THEME theme) {

        if (isAlreadyShown) {
            error("必须使用 build(...) 方法创建时，才可以使用 setTheme(...) 来修改对话框主题或风格。");
            return this;
        }

        this.theme = theme;
        refreshView();
        return this;
    }

    public BaseAdapter getCustomAdapter() {
        return customAdapter;
    }

    public BottomMenu setCustomAdapter(BaseAdapter customAdapter) {
        this.customAdapter = customAdapter;
        return this;
    }

    private float boxBodyOldY;
    private int step;
    private boolean isTouchDown;
    private float touchDownY;

    private View.OnTouchListener listViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isTouchDown = true;
                    touchDownY = event.getY();
                    boxBodyOldY = boxBody.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isTouchDown) {
                        float deltaY = event.getY() - touchDownY;
                        float aimY = boxBody.getY() + deltaY;
                        if (aimY < 0) {
                            aimY = 0;
                        }
                        if (isListViewOnTop() && aimY != 0) {
                            boxBody.setY(aimY);
                            return true;
                        } else {
                            touchDownY = event.getY();
                            boxBody.setY(0);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (isTouchDown) {
                        float deltaY = boxBody.getY() - boxBodyOldY;

                        if (deltaY >= -dip2px(50) && deltaY <= dip2px(50)) {
                            //几乎没动，回到原来位置
                            boxBody.animate().setDuration(300).translationY(boxBodyOldY);
                            step = 0;
                        } else {
                            if (deltaY > dip2px(150)) {
                                //向下(重)
                                boxBody.animate().setDuration(300).translationY(boxBody.getHeight()).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        doDismiss();
                                    }
                                });
                                v.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0));       //释放点击事件
                                return true;
                            } else if (deltaY > dip2px(50)) {
                                //向下(轻)
                                switch (step) {
                                    case 0:
                                        boxBody.animate().setDuration(300).translationY(boxBody.getHeight()).withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                doDismiss();
                                            }
                                        });
                                        break;
                                    case 1:
                                        boxBody.animate().setDuration(300).translationY(boxBody.getHeight() / 2);
                                        step = 0;
                                        break;
                                }
                                v.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0));       //释放点击事件
                                return true;
                            } else {
                                //向上
                                boxBody.animate().setDuration(300).translationY(0);
                                step = 1;
                                v.onTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0));       //释放点击事件
                                return true;
                            }
                        }
                    }
                    isTouchDown = false;
                    break;
            }
            if (v instanceof ListView) {
                return false;
            } else {
                return true;
            }
        }
    };

    private boolean isListViewOnTop() {
        View c = listMenu.getChildAt(0);
        if (c == null) {
            return false;
        }
        int firstVisiblePosition = listMenu.getFirstVisiblePosition();
        if (firstVisiblePosition != 0) {
            return false;
        }
        int top = c.getTop();
        return top == 0;
    }

    public BottomMenu setCustomDialogStyleId(int customDialogStyleId) {
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

    public BottomMenu setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
        return this;
    }

    public boolean getCancelable() {
        return cancelable == BOOLEAN.TRUE;
    }

    public BottomMenu setCancelable(boolean enable) {
        this.cancelable = enable ? BOOLEAN.TRUE : BOOLEAN.FALSE;
        if (dialog != null) {
            dialog.get().setCancelable(cancelable == BOOLEAN.TRUE);
        }
        return this;
    }

    public ALIGN getAlign() {
        return align;
    }

    public BottomMenu setAlign(ALIGN align) {
        this.align = align;
        return this;
    }
}