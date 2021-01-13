package com.android.camera.features.mimoji2.widget.autoselectview;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.features.mimoji2.widget.autoselectview.SelectItemBean;

public class AutoSelectViewHolder<T extends SelectItemBean> extends RecyclerView.ViewHolder {
    private TextView textView;

    public AutoSelectViewHolder(@NonNull View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.tv_type);
    }

    public void setData(T t, final int i) {
        this.textView.setTextColor(this.itemView.getContext().getColor(t.getAlpha() == 1 ? R.color.mimoji_edit_type_text_selected : R.color.mimoji_edit_type_text_normal));
        this.textView.setText(t.getText());
        this.itemView.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectViewHolder.AnonymousClass1 */

            public void onClick(View view) {
                try {
                    ((AutoSelectHorizontalView) AutoSelectViewHolder.this.itemView.getParent()).moveToPosition(i);
                } catch (ClassCastException unused) {
                    Log.e(AnonymousClass1.class.getSimpleName(), "recyclerview 类型不正确");
                }
            }
        });
    }
}
