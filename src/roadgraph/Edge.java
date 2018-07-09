package roadgraph;

import geography.GeographicPoint;

public class Edge extends Node {

    public GeographicPoint start;
    public GeographicPoint finish;
    public String roadName, roadType;
    public double weight;

    public Edge() {
        this.start = null;
        this.finish = null;
        this.roadName = "";
        this.roadType = "";
        weight = 0.0;
    }

    public Edge(Node start, Node finish, String roadName, String roadType, double length) {
        if (start == null || finish == null || roadName == null || roadType == null || length <=0) {
            throw new IllegalArgumentException();
        }
        this.start = start.getLocation();
        this.finish = finish.getLocation();
        this.roadName = roadName;
        this.roadType = roadType;
        weight = length;
    }

    @Override
    public String toString() {
        return "Edge: " + new Node(this.start).toString() + " -> " + new Node(this.finish).toString() + " | " + this.roadType + " named: " + this.roadName +", "+ this.weight + " km long.";
    }
}
