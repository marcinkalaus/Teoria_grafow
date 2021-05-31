import java.util.*;
import java.io.*;

public class FordFulkerson {

    static final int INFINITY = Integer.MAX_VALUE;

    static boolean bfs(int[][] graph, int source, int sink, int[] parentTracker) {

        int amountOfVertices = graph.length;
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[amountOfVertices];

        for (boolean i : visited) {
            i = false;
        }

        queue.addLast(source);
        visited[source] = true;
        parentTracker[source] = -1;

        while (queue.size() != 0){
            int u = queue.getFirst();
            queue.removeFirst();

            for (int v = 0; v < amountOfVertices; v++){
                if (!visited[v] && graph[u][v] > 0){
                    queue.addLast(v);
                    visited[v] = true;
                    parentTracker[v] = u;
                }
            }
        }
        return visited[sink];
    }

    int fordFulkersonAlgorithm(int[][] graph, int source, int sink){
        int u, v;
        int[][] residualGraph = graph.clone();
        int maxFlow = 0;
        int[] parentTracker = new int[graph.length];

        while (bfs(residualGraph, source, sink, parentTracker)){
            int pathFlow = INFINITY;
            v = sink;

            while (v != source) {
                u = parentTracker[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                v = parentTracker[v];
            }

            v = sink;

            while (v != source){
                u = parentTracker[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
                v = parentTracker[v];
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    public static void main(String[] args) {

        try {
            BufferedReader b = new BufferedReader(new FileReader("graf.txt"));
            String str = null;
            ArrayList<String> lines = new ArrayList<String>();
            while ((str = b.readLine()) != null) {
                lines.add(str);
            }
            String[] strArr = lines.toArray(new String[lines.size()]);
            b.close();

            int nRows = strArr.length;
            int nCols = (strArr[0].length() - strArr[0].replace(" ", "").length()) + 1;

            int[][] data = new int[nRows][nCols];

            String[] split = new String[nCols];
            for (int r = 0; r < nRows; r++) {
                split = strArr[r].split(" ");
                for (int c = 0; c < nCols; c++) {
                    data[r][c] = Integer.parseInt(split[c]);
                }
            }
            b.close();
            FordFulkerson f = new FordFulkerson();
            System.out.println("Maksymalny przepływ dla zadanego grafu wynosi: " + f.fordFulkersonAlgorithm(data, 0, data.length-1));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}