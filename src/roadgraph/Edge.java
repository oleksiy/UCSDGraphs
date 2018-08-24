package roadgraph;

import geography.GeographicPoint;

import java.util.Objects;

public class Edge extends Node {

    public GeographicPoint start;
    public GeographicPoint finish;
    public Node startNode;
    public Node destinationNode;
    public String roadName, roadType;
    public double length;

    public Edge() {
        this.start = null;
        this.finish = null;
        this.roadName = "";
        this.roadType = "";
        this.length = 0.0;
        this.startNode = null;
        this.destinationNode = null;
    }

    public Edge(Node start, Node finish, String roadName, String roadType, double length) {
        if (start == null || finish == null || roadName == null || roadType == null || length <=0) {
            throw new IllegalArgumentException();
        }
        this.start = start.getLocation();
        this.finish = finish.getLocation();
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
        this.startNode = start;
        this.destinationNode = finish;
    }

    public Edge(Node start, Node finish) {
        this.start = start.getLocation();
        this.finish = finish.getLocation();
        this.roadName = "";
        this.roadType = "";
        this.length = 0.0;
        this.startNode = start;
        this.destinationNode = finish;
    }

    public Node getStartNode() {
        return this.startNode;
    }

    public Node getDesitinationNode() {
        return this.destinationNode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStartNode().getLocation().getX(), this.getStartNode().getLocation().getY(), this.getDesitinationNode().getLocation().getX(), this.getDesitinationNode().getLocation().getY());
    }

    @Override
    public boolean equals(Object b) {
        if(this == b) return true;
        if(b == null) return false;
        if(this.getClass() != b.getClass()) return false;
        Edge e = (Edge)b;
        if((e.getStartNode().getLocation().getX() == this.getStartNode().getLocation().getX()) && (e.getDesitinationNode().getLocation().getY() == this.getDesitinationNode().getLocation().getY())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Edge: " + new Node(this.start).toString() + " -> " + new Node(this.finish).toString() + " | " + this.roadType + " named: " + this.roadName +", "+ this.length + " km long.";
    }
}
