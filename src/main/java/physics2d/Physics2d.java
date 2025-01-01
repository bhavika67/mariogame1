package physics2d;

import jade.GameObject;
import jade.Transform;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import physics2d.components.Box2DCollider;
import physics2d.components.CircleCollider;
import physics2d.components.Rigidbody2D;
import util.Settings;

public class Physics2D {
    private Vec2 gravity;
    private World world;
    private float physicsTime = 0.0f;

    private boolean isPlaying;

    public Physics2D(boolean isPlaying) {
        this.isPlaying = isPlaying;
        this.gravity = new Vec2(0, -3f);
        this.world = new World(gravity);
        world.setDebugDraw(new DebugDrawJ2D());
    }

    public void addGameObject(GameObject go) {
        Rigidbody2D rb = go.getComponent(Rigidbody2D.class);
        if (rb != null && rb.getRawBody() == null) {
            Transform transform = go.getComponent(Transform.class);

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(transform.position.x, transform.position.y);
            bodyDef.angle = (float)Math.toRadians(transform.rotation);
            bodyDef.angularDamping = rb.getAngularDamping();
            bodyDef.linearDamping = rb.getLinearDamping();
            bodyDef.fixedRotation = rb.isFixedRotation();
            bodyDef.bullet = rb.isContinuousCollision();

            switch (rb.getBodyType()) {
                case Kinematic: bodyDef.type = org.jbox2d.dynamics.BodyType.KINEMATIC; break;
                case Static: bodyDef.type = org.jbox2d.dynamics.BodyType.STATIC; break;
                case Dynamic: bodyDef.type = org.jbox2d.dynamics.BodyType.DYNAMIC; break;
            }

            PolygonShape shape = new PolygonShape();
            CircleCollider circleCollider = null;
            Box2DCollider box2DCollider = null;
            if ((circleCollider = go.getComponent(CircleCollider.class)) != null) {
                shape.setRadius(circleCollider.getRadius());
                bodyDef.position.set(transform.position.x - circleCollider.getRadius(), transform.position.y - circleCollider.getRadius());
            } else if ((box2DCollider = go.getComponent(Box2DCollider.class)) != null) {
                Vector2f halfSize = new Vector2f(box2DCollider.getHalfSize()).mul(0.5f);
                Vector2f offset = box2DCollider.getOffset();
                Vector2f origin = new Vector2f(box2DCollider.getOrigin());
                shape.setAsBox(halfSize.x, halfSize.y, new Vec2(origin.x, origin.y), 0);

                Vec2 pos = bodyDef.position;
                float xPos = pos.x + offset.x;
                float yPos = pos.y + offset.y;
                bodyDef.position.set(xPos, yPos);
            }

            Body body = this.world.createBody(bodyDef);
            rb.setRawBody(body);
            body.createFixture(shape, rb.getMass());

            if (isPlaying) {
                rb.setIsPlaying(true);
            }
        }
    }

    public void destroyGameObject(GameObject go) {
        Rigidbody2D rb = go.getComponent(Rigidbody2D.class);
        if (rb != null) {
            if (rb.getRawBody() != null) {
                world.destroyBody(rb.getRawBody());
                rb.setRawBody(null);
            }
        }
    }

    public void update(float dt) {
        if (!isPlaying) return;

        physicsTime += dt;
        if (physicsTime >= 0.0f) {
            physicsTime -= Settings.PHYSICS_TIMESTEP;
            world.step(Settings.PHYSICS_TIMESTEP, Settings.PHYSICS_VELOCITY_ITERATIONS, Settings.PHYSICS_POSITION_ITERATIONS);
        }
    }

    public void debugDraw() {
        world.drawDebugData();
    }
}
