package con.utk;

import com.jme3.app.AndroidHarnessFragment;
import com.jme3.input.event.TouchEvent;

public class JmeFragment extends AndroidHarnessFragment {

    private static final String TAG = JmeFragment.class.getName();

    private static ConpasAbstractState CallBack = null;

    public static void registerCallback(ConpasAbstractState callback) {
        CallBack = callback;

    }

    public JmeFragment() {
        appClass = "con.utk.Main";
        eglBitsPerPixel = 24;
        eglAlphaBits = 0;
        eglDepthBits = 16;
        eglSamples = 0;
        eglStencilBits = 0;
        frameRate = -1;
        maxResolutionDimension = -1;
        joystickEventsEnabled = false;
        keyEventsEnabled = true;
        mouseEventsEnabled = true;
        finishOnAppStop = true;
        handleExitHook = true;
        splashPicID = 0;
        //new  MyRunnable().run();
    }



    @Override
    public void onTouch(String name, TouchEvent evt, float tpf) {

        if (name.equals( "TouchEscape" )) {
            switch (evt.getType()) {
                case KEY_UP:
                    if (CallBack != null) CallBack.backButtonPressed();
                    // Main.getApp().backButtonPressed();
                    /**getActivity().runOnUiThread( new Runnable() {
                    @Override public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                    builder.setTitle( "jiijui" );
                    builder.setPositiveButton( "Yes", JmeFragment.this );
                    builder.setNegativeButton( "No", JmeFragment.this );
                    builder.setMessage( exitDialogMessage );

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    }
                    } );*/
                    break;
                default:

                    break;
            }
        }
    }

}
