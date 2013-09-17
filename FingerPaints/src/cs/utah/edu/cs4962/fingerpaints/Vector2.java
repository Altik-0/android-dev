package cs.utah.edu.cs4962.fingerpaints;

/**
 * Represents a two dimensional floating point vector
 */
public class Vector2
{
    public float x;
    public float y;

    public Vector2(float _x, float _y)
    {
        x = _x;
        y = _y;
    }

    public static Vector2 add(Vector2 a, Vector2 b)
    {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 negate(Vector2 v)
    {
        return new Vector2(-v.x, -v.y);
    }

    public static float dotProd(Vector2 a, Vector2 b)
    {
        return (a.x * b.x) + (a.y * b.y);
    }
}