package Logic;


import java.util.Vector;

public class Dijkstra {
    private static final int NO_PARENT = -1;
    private static int [][] adjacencyMatrix;

    public Dijkstra(int[][] adjacencyMatrix) {
        Dijkstra.adjacencyMatrix = adjacencyMatrix;
    }

    public Vector<Integer> dijkstra(int startVertex, int goal) {
        Vector<Integer> solution = new Vector<>();
        int numVertices = adjacencyMatrix[0].length;
        int[] shortestDistances = new int[numVertices];
        boolean[] added = new boolean[numVertices];
        for(int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
        shortestDistances[startVertex] = 0;
        int[] parents = new int[numVertices];
        parents[startVertex] = NO_PARENT;
        for(int i = 0; i < numVertices; i++) {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for(int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
                if(!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            added[nearestVertex] = true;
            for(int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++){
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                if(edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
        get_solution(goal, parents, solution);
        return solution;
    }
    private static void get_solution(int goal, int[] parents, Vector<Integer> solution) {
        if(goal == NO_PARENT) {
            return;
        }
        get_solution(parents[goal], parents, solution);
        solution.add(goal);
    }
}
