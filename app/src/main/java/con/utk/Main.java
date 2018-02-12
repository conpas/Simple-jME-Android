package con.utk;

import android.util.Log;

import com.jme3.app.LegacyApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends LegacyApplication   {
    MainActivity mainActivity;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void start(JmeContext.Type contextType) {
        Log.d(Main.class.getSimpleName(), "start");
        AppSettings settings = new AppSettings( true );
        //settings.setResolution(1024, 768);
        setSettings( settings );
        super.start( contextType );
        Log.d(Main.class.getSimpleName(), "start 2");
    }

    @Override
    public void initialize() {

        super.initialize();
        new MenuState( this );
    }

    @Override
    public void update() {
        super.update();

        // do some animation
        float tpf = timer.getTimePerFrame();

        stateManager.update( tpf );
        stateManager.render( renderManager );
        renderManager.render( tpf, context.isRenderable() );
    }

    public void game() {
        System.out.print( "game" );
        new GameState( this );

    }

    @Override
    public void destroy() {
        super.destroy();
    }




    public void setProgress(int progress) {
        if (mainActivity == null) return;
        if (progress > 100) progress = 100;
        else if (progress < 0) progress = 0;
        if (mainActivity != null) mainActivity.setProgress( progress );
        this.mainActivity.setProgress( progress );

    }
}
