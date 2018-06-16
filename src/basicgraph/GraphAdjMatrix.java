package basicgraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A class that implements a directed graph. 
 * The graph may have self-loops, parallel edges. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges via an adjacency matrix.
 *
 * @author UCSD MOOC development team and YOU
 *
 */
public class GraphAdjMatrix extends Graph {

    private final int defaultNumVertices = 5;
    private int[][] adjMatrix;

    private enum SplitBy {
        ROWS, COLUMNS
    }

    /** Create a new empty Graph */
    public GraphAdjMatrix () {
        adjMatrix = new int[defaultNumVertices][defaultNumVertices];
    }

    /**
     * Implement the abstract method for adding a vertex.
     * If need to increase dimensions of matrix, double them
     * to amortize cost.
     */
    public void implementAddVertex() {
        int v = getNumVertices();
        if (v >= adjMatrix.length) {
            int[][] newAdjMatrix = new int[v*2][v*2];
            for (int i = 0; i < adjMatrix.length; i ++) {
                for (int j = 0; j < adjMatrix.length; j ++) {
                    newAdjMatrix[i][j] = adjMatrix[i][j];
                }
            }
            adjMatrix = newAdjMatrix;
        }
    }

    /**
     * Implement the abstract method for adding an edge.
     * Allows for multiple edges between two points:
     * the entry at row v, column w stores the number of such edges.
     * @param v the index of the start point for the edge.
     * @param w the index of the end point for the edge.
     */
    public void implementAddEdge(int v, int w) {
        adjMatrix[v][w] += 1;
    }

    /**
     * Implement the abstract method for finding all
     * out-neighbors of a vertex.
     * If there are multiple edges between the vertex
     * and one of its out-neighbors, this neighbor
     * appears once in the list for each of these edges.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */
    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<Integer>();
        for (int i = 0; i < getNumVertices(); i ++) {
            for (int j=0; j< adjMatrix[v][i]; j ++) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    /**
     * Implement the abstract method for finding all
     * in-neighbors of a vertex.
     * If there are multiple edges from another vertex
     * to this one, the neighbor
     * appears once in the list for each of these edges.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */
    public List<Integer> getInNeighbors(int v) {
        List<Integer> inNeighbors = new ArrayList<Integer>();
        for (int i = 0; i < getNumVertices(); i ++) {
            for (int j=0; j< adjMatrix[i][v]; j++) {
                inNeighbors.add(i);
            }
        }
        return inNeighbors;
    }

    /**
     * Implement the abstract method for finding all
     * vertices reachable by two hops from v.
     * Use matrix multiplication to record length 2 paths.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */
    public List<Integer> getDistance2(int v) {
        List<Integer> twoHopNeighbors = new ArrayList<>();
        List<int[]> rows = splitMatrix(SplitBy.ROWS);
        List<int[]> columns = splitMatrix(SplitBy.COLUMNS);
        int[][] squaredMatrix = squareAdjancencyMatrix(rows, columns);
        //System.out.println("Squared Matrix is:");
        //System.out.println(printMatrix(squaredMatrix));


        for (int node = 0; node < squaredMatrix[v].length; node++) {
            if(squaredMatrix[v][node] > 0) {
                //System.out.println(node +  " is a neighbor of " + v + " " +  squaredMatrix[v][node] + " times");
                for(int i = 0; i < squaredMatrix[v][node]; i++) {
                    twoHopNeighbors.add(node);
                }
            }
        }
        //System.out.println();
        return twoHopNeighbors;
    }

    public String printList(List<int[]> list){
        StringBuffer sb = new StringBuffer();
        for(int[] array: list){
            sb.append("ENTRY: ");
            for(int i=0; i < array.length; i++){
                if(i == array.length-1) {
                    sb.append(array[i]);
                } else {
                    sb.append(array[i]);
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int dotProduct(int[] row, int[] column) {
        int result = 0;
        int counter = 0;
        for (int r: row) {
            result += r * column[counter];
            counter++;
        }
        return result;
    }

    public ArrayList<int[]> splitMatrix(SplitBy splitMethod){
        ArrayList<int[]> listToReturn = new ArrayList<>();
        if(splitMethod == SplitBy.ROWS) {
            for (int row = 0; row < adjMatrix.length; row++) {
                int[] rowArray = Arrays.copyOfRange(this.adjMatrix[row], 0, this.adjMatrix[row].length);
                listToReturn.add(rowArray);
            }
        } else {
            for (int row = 0; row < this.adjMatrix.length; row++) {
                int[] column = new int[this.adjMatrix[row].length];
                for (int col = 0; col < this.adjMatrix[row].length; col++) {
                    //you want to reverse row and column because you want to go (0,0) (1,0) (2,0)...etc, then (1,0) (1,1)
                    column[col] = this.adjMatrix[col][row];
                }
                listToReturn.add(column);
            }
        }
        return listToReturn;
    }

    public int[][] squareAdjancencyMatrix(List<int[]>rows, List<int[]>columns){
        int [][] squaredMatrix = new int[rows.size()][columns.size()];
        for(int row_index=0; row_index < rows.size(); row_index++){
            for(int col_index=0; col_index < columns.size(); col_index++){
                squaredMatrix[row_index][col_index] = dotProduct(rows.get(row_index), columns.get(col_index));
            }
        }
        return squaredMatrix;
    }

    public String adjacencyString() {
        int dim = getNumVertices();
        String s = "Adjacency matrix";
        s += " (size " + dim + "x" + dim + " = " + dim* dim + " integers):";
        for (int i = 0; i < dim; i ++) {
            s += "\n\t"+i+": ";
            for (int j = 0; j < dim; j++) {
                s += adjMatrix[i][j] + ", ";
            }
        }
        return s;
    }

    public String printMatrix(int[][] matrix) {
        StringBuffer sb = new StringBuffer();
        for (int r = 0; r < matrix.length; r ++) {
            for (int c = 0; c < matrix[r].length; c++) {
                sb.append(matrix[r][c]+", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
