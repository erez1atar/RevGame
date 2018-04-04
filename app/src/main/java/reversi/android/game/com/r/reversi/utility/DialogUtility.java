package reversi.android.game.com.r.reversi.utility;

import android.app.Dialog;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import reversi.android.game.com.r.reversi.R;

/**
 * Created by Erez on 03/04/2018.
 */

public class DialogUtility {

    public static void setBackGround(Dialog dialog, Button[] btns) {
        if(dialog == null || dialog.getWindow() == null || btns == null || dialog.findViewById(R.id.dialog_head) == null)
        {
            return;
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if(App.getUserDefault().getUIString().equals("BLUE")) {

            dialog.findViewById(R.id.dialog_head).setBackgroundResource(R.drawable.dialog_shape_blue);
        }
        else {
            dialog.findViewById(R.id.dialog_head).setBackgroundResource(R.drawable.dialog_shape);
        }
        for(int i = 0 ; i < btns.length ; i++) {
            if(App.getUserDefault().getUIString().equals("BLUE")) {
                btns[i].setBackgroundResource(R.drawable.btn_rounded_backgorund_blue);
            }
            else {
                btns[i].setBackgroundResource(R.drawable.btn_rounded_backgorund);
            }

        }
    }
}
