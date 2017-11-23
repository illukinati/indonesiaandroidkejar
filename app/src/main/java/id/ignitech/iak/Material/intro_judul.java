package id.ignitech.iak.Material;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by alenovan on 5/29/17.
 */

public class intro_judul extends android.support.v7.widget.AppCompatTextView {

    public intro_judul(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public intro_judul(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public intro_judul(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(tf);
        }
    }

}

