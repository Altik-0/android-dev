package cs.utah.edu.cs4962.fingerpaints;

import java.util.LinkedList;

/**
 * Stores all data used to represent the painting created by the user.
 *
 * Lines drawn by user are represented by an ordered list of Curves, which
 * each contain an ordered list of points denoting places the user has put
 * their finger while drawing the curve. A curve also has an associated
 * color and stroke width.
 */
public class PainterModel
{
    // Struct that represents curves
    public class Curve
    {
        // Made public to avoid copying -- don't abuse the privilege!
        public LinkedList<Vector2> points;
        public int color;
        public float width;

        public Curve(int _color, float _width)
        {
            color = _color;
            width = _width;

            points = new LinkedList<Vector2>();
        }

        public void AddPoint(Vector2 point)
        {
            points.addLast(point);
        }
    }

    // Made public to avoid copying -- don't abuse the privilege!
    public LinkedList<Curve> curves;

    public PainterModel()
    {
        curves = new LinkedList<Curve>();
    }

    public void AddPoint(Vector2 point)
    {
        curves.get(0).AddPoint(point);
    }

    public void BeginNewCurve(int color, float width)
    {
        curves.addFirst(new Curve(color, width));
    }
}
