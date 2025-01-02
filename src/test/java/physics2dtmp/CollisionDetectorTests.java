package physics2dtmp;

import org.joml.Vector2f;
import org.junit.jupiter.api.Test;
import physics2dtmp.primitives.Box2D;
import physics2dtmp.primitives.Circle;
import physics2dtmp.rigidbody.IntersectionDetector2D;
import physics2dtmp.rigidbody.Rigidbody2D;
import renderer.Line2D;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A suite of JUnit tests verifying 2D intersection logic, such as lines, circles, and boxes.
 * <p>
 * Each test is intended to confirm correctness of IntersectionDetector2D for points lying on lines,
 * circles, or boxes, as well as negative test cases where geometry should not intersect.
 */
public class CollisionDetectorTests {

    private final float EPSILON = 1e-6f;

    // ---------------------------------------------------------
    // LINE INTERSECTION TESTS
    // ---------------------------------------------------------
    @Test
    public void pointOnLine_shouldReturnTrue_whenExactlyAtLineStart() {
        // line from (0, 0) to (12, 4)
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line),
                "Expected point (0, 0) to be on line start");
    }

    @Test
    public void pointOnLine_shouldReturnTrue_whenExactlyAtLineEnd() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line),
                "Expected point (12, 4) to be on line end");
    }

    @Test
    public void pointOnLine_shouldReturnTrue_onVerticalLine() {
        // vertical line from (0,0) to (0,10)
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line),
                "Expected point (0,5) on vertical line from (0,0) to (0,10)");
    }

    @Test
    public void pointOnLine_shouldReturnTrue_whenOnLineMidpoint() {
        // line from (0,0) to (12,4)
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(6, 2); // midpoint

        assertTrue(IntersectionDetector2D.pointOnLine(point, line),
                "Expected midpoint to be on line");
    }

    @Test
    public void pointOnLine_shouldReturnFalse_whenNotOnLineButBetweenEndpoints() {
        // line from (0,0) to (12,4)
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(4, 2); // Not exactly collinear

        assertFalse(IntersectionDetector2D.pointOnLine(point, line),
                "Should not lie on line if not collinear");
    }

    @Test
    public void pointOnLine_shouldReturnTrue_whenLineShifted() {
        // line from (10,10) to (22,14)
        Line2D line = new Line2D(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(16, 12); // the midpoint

        assertTrue(IntersectionDetector2D.pointOnLine(point, line),
                "Expected midpoint to be on shifted line");
    }

    @Test
    public void pointOnLine_shouldReturnFalse_whenCloseButNotCollinear() {
        // line from (10,10) to (22,14)
        Line2D line = new Line2D(new Vector2f(10, 10), new Vector2f(22, 14));
        Vector2f point = new Vector2f(14, 12); // near line but not collinear

        assertFalse(IntersectionDetector2D.pointOnLine(point, line),
                "Not exactly collinear => false");
    }

    // ---------------------------------------------------------
    // Tests for future line or ray functionality
    // (closestPoint, ray intersection, etc.) are commented out
    // ---------------------------------------------------------
    // e.g.:
    // @Test
    // public void closestPointOnLine_testCaseOne() { ... }

    // ---------------------------------------------------------
    // CIRCLE INTERSECTION TESTS
    // ---------------------------------------------------------
    @Test
    public void pointInCircle_shouldReturnTrue_forSimpleOriginCircle() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(3, -2);
        assertTrue(IntersectionDetector2D.pointInCircle(point, circle),
                "Point (3, -2) is inside circle of radius 5 at origin");
    }

    @Test
    public void pointInCircle_shouldReturnTrue_whenJustInsideRadius() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f, 0);
        assertTrue(IntersectionDetector2D.pointInCircle(point, circle),
                "Point just inside radius => true");
    }

    @Test
    public void pointInCircle_shouldReturnFalse_whenClearlyOutside() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-6, -6);
        assertFalse(IntersectionDetector2D.pointInCircle(point, circle),
                "Point (-6, -6) is definitely outside radius 5");
    }

    @Test
    public void pointInCircle_shouldReturnTrue_whenCircleIsTranslated() {
        // circle translated by (10,10)
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10)); // offset
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(3 + 10, -2 + 10);
        assertTrue(IntersectionDetector2D.pointInCircle(point, circle),
                "Point (13,8) inside circle centered at (10,10) radius=5 => true");
    }

    @Test
    public void pointInCircle_shouldReturnTrue_whenNearCircleOffsetEdge() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f + 10, 0 + 10);
        assertTrue(IntersectionDetector2D.pointInCircle(point, circle),
                "Just inside offset circle boundary => true");
    }

    @Test
    public void pointInCircle_shouldReturnFalse_whenOutsideTranslatedCircle() {
        Circle circle = new Circle();
        circle.setRadius(5f);
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        circle.setRigidbody(body);

        Vector2f point = new Vector2f(-6 + 10, -6 + 10);
        assertFalse(IntersectionDetector2D.pointInCircle(point, circle),
                "Point is outside the circle after offset => false");
    }

    // Additional circle tests like closestPointToCircle, etc. are commented out

    // ---------------------------------------------------------
    // BOX2D (AABB or OBB) INTERSECTION TESTS
    // ---------------------------------------------------------
    @Test
    public void pointInBox2D_shouldReturnTrue_whenInsideLocalBoxAtOrigin() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        box.setRigidbody(body);

        Vector2f point = new Vector2f(4, 4.3f);
        assertTrue(IntersectionDetector2D.pointInBox2D(point, box),
                "Point (4,4.3) is inside a box of half-size=5 => pass");
    }

    @Test
    public void pointInBox2D_shouldReturnTrue_nearNegativeEdge() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-4.9f, -4.9f);
        assertTrue(IntersectionDetector2D.pointInBox2D(point, box),
                "Close to negative corner => still inside");
    }

    @Test
    public void pointInBox2D_shouldReturnFalse_whenSlightlyOutsideTopEdge() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        box.setRigidbody(body);

        // top edge is y=5 => 5.1 => out
        Vector2f point = new Vector2f(0, 5.1f);
        assertFalse(IntersectionDetector2D.pointInBox2D(point, box),
                "Slightly above top => false");
    }

    @Test
    public void pointInBox2D_shouldReturnTrue_whenBoxIsTranslated() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10)); // move box center
        box.setRigidbody(body);

        Vector2f point = new Vector2f(4 + 10, 4.3f + 10);
        assertTrue(IntersectionDetector2D.pointInBox2D(point, box),
                "Inside box after translation => true");
    }

    @Test
    public void pointInBox2D_shouldReturnFalse_whenOutsideTranslatedBox() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10));
        box.setRigidbody(body);

        Vector2f point = new Vector2f(0 + 10, 5.1f + 10);
        assertFalse(IntersectionDetector2D.pointInBox2D(point, box),
                "Slightly outside upper boundary => false");
    }

    @Test
    public void pointInBox2D_shouldReturnTrue_whenRotated45AndPointInside() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        // rotation 45 deg
        body.setTransform(new Vector2f(0), 45f);
        box.setRigidbody(body);

        Vector2f point = new Vector2f(-1, -1);
        assertTrue(IntersectionDetector2D.pointInBox2D(point, box),
                "Expect point near center is inside 45-degree-rotated box => true");
    }

    @Test
    public void pointInBox2D_shouldReturnTrue_forCornerPointWithRotation() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(0), 45f);
        box.setRigidbody(body);

        // e.g. a corner about sqrt(2)/2 * halfsize from center
        Vector2f point = new Vector2f(-3.43553390593f, 3.43553390593f);
        assertTrue(IntersectionDetector2D.pointInBox2D(point, box),
                "Should be inside near corner => true");
    }

    @Test
    public void pointInBox2D_shouldReturnFalse_forPointOutsideWhenBoxRotatedAndTranslated() {
        Box2D box = new Box2D();
        box.setSize(new Vector2f(10));
        Rigidbody2D body = new Rigidbody2D();
        body.setTransform(new Vector2f(10), 45f);
        box.setRigidbody(body);

        // Slightly outside
        Vector2f point = new Vector2f(-3.63553390593f + 10, 3.63553390593f + 10);
        assertFalse(IntersectionDetector2D.pointInBox2D(point, box),
                "Outside bounding region => false");
    }

    // Additional box tests like closestPointToBox2D, etc. are commented out

}
