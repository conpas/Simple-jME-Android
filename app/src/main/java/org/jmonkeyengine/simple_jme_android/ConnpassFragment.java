package org.jmonkeyengine.simple_jme_android.gamelogic;

import android.app.AlertDialog;

import com.jme3.app.AndroidHarnessFragment;
import com.jme3.input.event.TouchEvent;

/**
 * Created by conpas on 7/19/17.
 */

public class ConnpassFragment extends AndroidHarnessFragment {
    final private String ESCAPE_EVENT = "TouchEscape";
    @Override
    public void onTouch(String name, TouchEvent evt, float tpf) {
        if (name.equals(ESCAPE_EVENT)) {
            switch (evt.getType()) {
                case KEY_UP:
             /** getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("jiijui");
                            builder.setPositiveButton("Yes", ConnpassFragment.this);
                            builder.setNegativeButton("No",ConnpassFragment.this);
                            builder.setMessage(exitDialogMessage);

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
              });**/
                    break;
                default:
                    break;
            }
        }
    }

}
