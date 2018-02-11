package con.utk;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.app.state.AbstractAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Created by conpas on 2/9/18.
 */
public class MenuState extends ConpasAbstractState implements ScreenController {



    public MenuState(Main app) {
        super(app);
    }
    NiftyJmeDisplay niftyDisplay;
    Nifty nifty = null;

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
    }

    @Override
    public void cleanup() {
        app.getGuiViewPort().removeProcessor(niftyDisplay);
        super.cleanup();
    }

    @Override
    public void backButtonPressed() {
        nifty.gotoScreen("end");
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", app.getAssetManager().loadTexture("Textures/Monkey.png"));
        geom.setMaterial(mat);
        niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                app.getAssetManager(),
                app.getInputManager(),
                app.getAudioRenderer(),
                app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MenuGui.xml", "start", this);
        app.getGuiViewPort().addProcessor(niftyDisplay);
        //// getRootNode().attachChild(geom);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

    }

    public void menu() {
        nifty.gotoScreen("menu");
        System.out.print("menu");
    }

    public void start() {
        nifty.getScreen("start").findElementByName("ocki").getRenderer(TextRenderer.class).setText("" + System.nanoTime());
        nifty.gotoScreen("start");

    }

    public void game() {
        new GameState( app);
        System.out.print("game");
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

}
