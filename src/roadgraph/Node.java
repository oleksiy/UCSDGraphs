package roadgraph;

import geography.GeographicPoint;

import java.util.HashMap;
import java.util.Objects;

public class Node {
    private GeographicPoint location;
    private boolean visited;
    private int id;

    public Node() {
        this.location = null;
        this.visited = false;
    }

    public Node(GeographicPoint location) {
        this.location = location;
        this.visited = false;
    }

    public Node(double lat, double lon) {
        GeographicPoint location = new GeographicPoint(lat, lon);
        this.location = location;
        this.visited = false;
    }

    public Node(Node toCopy) {
        this.visited = toCopy.isVisited();
        this.location = toCopy.getLocation();
    }

    public GeographicPoint getLocation() {
        return this.location;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean setting) { this.visited = setting; }

    @Override
    public boolean equals(Object b) {
        if(this == b) return true;
        if(b == null) return false;
        if(this.getClass() != b.getClass()) return false;
        Node n = (Node)b;
        if((n.getLocation().getX() == this.getLocation().getX()) && (n.getLocation().getY() == this.getLocation().getY())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Node:" + " (" + location.getX() + "," + location.getY() + "). Was visited? " + this.visited + ".";
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getLocation().getY(), this.getLocation().getX());
    }

    /**
    public static void main(String[]args) {
        Node n = new Node(new GeographicPoint(4.5, 8.5));
        System.out.println(n.toString());
    }**/
}
