package com.rdavepatient.soft.Community.Ui;

import android.content.Context;
import android.util.AttributeSet;

public class FontButton extends android.support.v7.widget.AppCompatButton {
    private Context context;

    public FontButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
       // init();
    }

    public FontButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        //init();
    }

    public FontButton(Context context) {
        super(context);
        this.context = context;
       // init();
    }

//    private void init() {
//        setTypeface(Typeface.createFromAsset(this.context.getAssets(), "RobotoCondensedRegular.ttf"));
//    }
}
