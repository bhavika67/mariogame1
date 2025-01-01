package physics2d.components;

import editor.JImGui;
import org.joml.Vector2f;
import renderer.DebugDraw;

public class Box2DCollider extends Collider {
    private Vector2f halfSize = new Vector2f(1);
    private Vector2f origin = new Vector2f();

    public Vector2f getHalfSize() {
        return halfSize;
    }

    public Vector2f getOrigin() {
        return this.origin;
    }

    @Override
    public void update(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position).add(offset.x, offset.y);
        DebugDraw.addBox2D(center, this.halfSize, this.gameObject.transform.rotation);
    }

    @Override
    public void imgui() {
        JImGui.drawVec2Control("Half Size", halfSize);
        JImGui.drawVec2Control("Origin", origin);
        JImGui.drawVec2Control("Offset", offset);
    }
}