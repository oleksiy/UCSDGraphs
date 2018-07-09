package roadgraph;

import geography.GeographicPoint;

public class Node {
    public GeographicPoint location;
    public boolean visited;

    public Node() {
        this.location = null;
        this.visited = false;
    }

    public Node(GeographicPoint location) {
        this.location = location;
        this.visited = false;
    }

    public GeographicPoint getLocation() {
        return this.location;
    }

    public boolean isVisited() {
        return this.visited;
    }

    @Override
    public String toString(){
        return "Node:" + " (" + location.getX() + "," + location.getY() + ")";
    }

    /**
    public static void main(String[]args) {
        Node n = new Node(new GeographicPoint(4.5, 8.5));
        System.out.println(n.toString());
    }**/
}
