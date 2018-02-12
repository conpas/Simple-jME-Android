package con.utk;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Node;



/**
 * @author conpas
 */
public abstract class ConpasAbstractState extends AbstractAppState   {

    protected Main app;
    private final Node rootNode = new Node( "Root Node" );

    ConpasAbstractState(Main app) {
        this.app = app;
        app.getStateManager().cleanup();
        app.getViewPort().attachScene( rootNode );
        app.getStateManager().attach( this );
        JmeFragment.registerCallback( this );

    }

    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public void update(float tpf) {
        super.update( tpf );
        rootNode.updateLogicalState( tpf );
        rootNode.updateGeometricState();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        app.getStateManager().detach( this );
        app.getViewPort().detachScene( rootNode );
    }

    public abstract void backButtonPressed();

    public void setProgress(int progress) {
        app.setProgress( progress );

    }

    public void registrActivity(MainActivity mainActivity) {
        app.mainActivity = mainActivity;
    }
}
