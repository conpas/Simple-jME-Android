package con.utk;


        import com.jme3.app.Application;
        import com.jme3.app.state.AppStateManager;
        import com.jme3.asset.TextureKey;
        import com.jme3.bullet.BulletAppState;
        import com.jme3.bullet.collision.shapes.SphereCollisionShape;

        import com.jme3.bullet.control.RigidBodyControl;
        import com.jme3.material.Material;
        import com.jme3.math.Vector2f;
        import com.jme3.math.Vector3f;
        import com.jme3.scene.Geometry;
        import com.jme3.scene.Node;
        import com.jme3.app.state.AbstractAppState;
        import com.jme3.input.MouseInput;
        import com.jme3.input.controls.ActionListener;
        import com.jme3.input.controls.MouseButtonTrigger;
        import com.jme3.niftygui.NiftyJmeDisplay;
        import com.jme3.renderer.queue.RenderQueue;
        import com.jme3.scene.shape.Box;
        import com.jme3.scene.shape.Sphere;
        import com.jme3.texture.Texture;

        import de.lessvoid.nifty.Nifty;
        import de.lessvoid.nifty.NiftyEventSubscriber;
        import de.lessvoid.nifty.controls.ButtonClickedEvent;
        import de.lessvoid.nifty.elements.render.TextRenderer;
        import de.lessvoid.nifty.screen.Screen;
        import de.lessvoid.nifty.screen.ScreenController;

/**
 * Created by conpas on 2/9/18.
 */
public class GameState extends ConpasAbstractState {


    int bricksPerLayer = 8;
    int brickLayers = 30;

    static float brickWidth = .75f, brickHeight = .25f, brickDepth = .25f;
    float radius = 3f;
    float angle = 0;


    Material mat;
    Material mat2;
    Material mat3;
    private Sphere bullet;
    private Box brick;
    private SphereCollisionShape bulletCollisionShape;
    Geometry geom;
    BulletAppState bulletAppState;

    public GameState(Main app) {
        super(app);
    }


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", app.getAssetManager().loadTexture("Textures/Monkey.png"));
        geom.setMaterial(mat);
        getRootNode().attachChild(geom);

        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        //   bulletAppState.setEnabled(false);
        stateManager.attach(bulletAppState);
        bullet = new Sphere(32, 32, 0.4f, true, false);
        bullet.setTextureMode(Sphere.TextureMode.Projected);
        bulletCollisionShape = new SphereCollisionShape(0.4f);

        brick = new Box(brickWidth, brickHeight, brickDepth);
        brick.scaleTextureCoordinates(new Vector2f(1f, .5f));
        //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        initMaterial();
        initTower();
        initFloor();
        initCrossHairs();
        app.getCamera().setLocation(new Vector3f(0, 25f, 8f));
        app.getCamera().lookAt(Vector3f.ZERO, new Vector3f(0, 1, 0));
        app.getCamera().setFrustumFar(80);
        app.getInputManager().addMapping ("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        app.getInputManager().addListener(actionListener, "shoot");
        //getRootNode().setShadowMode( ShadowMode.Off);

    }
    public void initTower() {
        double tempX = 0;
        double tempY = 0;
        double tempZ = 0;
        angle = 0f;
        for (int i = 0; i < brickLayers; i++){
            // Increment rows
            if(i!=0)
                tempY+=brickHeight*2;
            else
                tempY=brickHeight;
            // Alternate brick seams
            angle = 360.0f / bricksPerLayer * i/2f;
            for (int j = 0; j < bricksPerLayer; j++){
                tempZ = Math.cos(Math.toRadians(angle))*radius;
                tempX = Math.sin(Math.toRadians(angle))*radius;
                System.out.println("x="+((float)(tempX))+" y="+((float)(tempY))+" z="+(float)(tempZ));
                Vector3f vt = new Vector3f((float)(tempX), (float)(tempY), (float)(tempZ));
                // Add crenelation
                if (i==brickLayers-1){
                    if (j%2 == 0){
                        addBrick(vt);
                    }
                }
                // Create main tower
                else {
                    addBrick(vt);
                }
                angle += 360.0/bricksPerLayer;
            }
        }

    }

    public void initFloor() {
        Box floorBox = new Box(10f, 0.1f, 5f);
        floorBox.scaleTextureCoordinates(new Vector2f(3, 6));

        Geometry floor = new Geometry("floor", floorBox);
        floor.setMaterial(mat3);
        // floor.setShadowMode(ShadowMode.Receive);
        floor.setLocalTranslation(0, 0, 0);
        floor.addControl(new RigidBodyControl(0));
        this.getRootNode().attachChild(floor);
        bulletAppState.getPhysicsSpace().add(floor);
    }

    public void initMaterial() {
        mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/BrickWall.jpg");
        key.setGenerateMips(true);
        Texture tex =app.getAssetManager().loadTexture(key);
        mat.setTexture("ColorMap", tex);

        mat2 = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = app.getAssetManager().loadTexture(key2);
        mat2.setTexture("ColorMap", tex2);

        mat3 = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key3 = new TextureKey("Textures/Pond.jpg");
        key3.setGenerateMips(true);
        Texture tex3 = app.getAssetManager().loadTexture(key3);
        tex3.setWrap(Texture.WrapMode.Repeat);
        mat3.setTexture("ColorMap", tex3);
    }

    public void addBrick(Vector3f ori) {
        Geometry reBoxg = new Geometry("brick", brick);
        reBoxg.setMaterial(mat);
        reBoxg.setLocalTranslation(ori);
        reBoxg.rotate(0f, (float)Math.toRadians(angle) , 0f );
        reBoxg.addControl(new RigidBodyControl(1.5f));
        //reBoxg.setShadowMode( ShadowMode.CastAndReceive);
        reBoxg.getControl(RigidBodyControl.class).setFriction(1.6f);
        this.getRootNode().attachChild(reBoxg);
        bulletAppState.getPhysicsSpace().add(reBoxg);
    }

    protected void initCrossHairs() {

    }

    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("shoot") && !keyPressed) {
                Geometry bulletg = new Geometry("bullet", bullet);
                bulletg.setMaterial(mat2);
                bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
                bulletg.setLocalTranslation(app.getCamera().getLocation());
                // RigidBodyControl bulletNode = new BombControl(app.getAssetManager(), bulletCollisionShape, 1);
                RigidBodyControl bulletNode = new RigidBodyControl(bulletCollisionShape, 10);
                bulletNode.setLinearVelocity(app.getCamera().getDirection().mult(25));
                bulletg.addControl(bulletNode);
                getRootNode().attachChild(bulletg);
                bulletAppState.getPhysicsSpace().add(bulletNode);
            }
        }
    };
    public  void backButtonPressed (){


    };
}