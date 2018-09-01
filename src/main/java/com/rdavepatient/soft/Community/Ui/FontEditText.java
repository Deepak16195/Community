package com.rdavepatient.soft.Community.Ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class FontEditText extends android.support.v7.widget.AppCompatEditText {
    private Context context;

    public FontEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init();
    }

    public FontEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    public FontEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(this.context.getAssets(), "RobotoCondensedRegular.ttf"));
    }
}
