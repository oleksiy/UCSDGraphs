package roadgraph;

import geography.GeographicPoint;

public class Edge extends Node {

    public GeographicPoint start;
    public GeographicPoint finish;
    public double weight;

    public Edge() {
        this.start = null;
        this.finish = null;
        weight = 0.0;
    }

    public Edge(Node start, Node finish) {
        this.start = start.getLocation();
        this.finish = finish.getLocation();
        weight = 0.0;
    }

}
