package roadgraph;

import geography.GeographicPoint;

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

    public Node getStartNode() {
        return this.startNode;
    }

    public Node getDesitinationNode() {
        return this.destinationNode;
    }



    @Override
    public String toString() {
        return "Edge: " + new Node(this.start).toString() + " -> " + new Node(this.finish).toString() + " | " + this.roadType + " named: " + this.roadName +", "+ this.length + " km long.";
    }
}
