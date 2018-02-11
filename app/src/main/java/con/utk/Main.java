package con.utk;
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
public class Main extends LegacyApplication {

    public static void main(String[] args) {
        Main app = new Main();

        app.start();
    }

    @Override
    public void start(JmeContext.Type contextType) {
        AppSettings settings = new AppSettings(true);
        //settings.setResolution(1024, 768);


        setSettings(settings);
        super.start(contextType);
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("Initialize");
        new MenuState(this);


    }

    @Override
    public void update() {
        super.update();

        // do some animation
        float tpf = timer.getTimePerFrame();

        stateManager.update(tpf);
        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
    }

    public void game() {
        System.out.print("game");
        new GameState(this);

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
