package roadgraph;

import geography.GeographicPoint;

public class Node {
    private GeographicPoint location;
    private boolean visited;

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

    public GeographicPoint getLocation() {
        return this.location;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean setting) { this.visited = setting; }

    public boolean equals(Node b) {
        if(this.location.equals(b.location)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "Node:" + " (" + location.getX() + "," + location.getY() + "). Was visited? " + this.visited + ".";
    }

    /**
    public static void main(String[]args) {
        Node n = new Node(new GeographicPoint(4.5, 8.5));
        System.out.println(n.toString());
    }**/
}
