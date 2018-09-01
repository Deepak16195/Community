package com.rdavepatient.soft.Community.Acitvity;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class FontAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    private Context context;

    public FontAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init();
    }

    public FontAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    public FontAutoCompleteTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(this.context.getAssets(), "RobotoCondensedRegular.ttf"));
    }
}
