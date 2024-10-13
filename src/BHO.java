import java.io.IOException;
import java.util.Arrays;

public class BHO {


    public static void main(String[] args) throws IOException {
        // Example graph represented as an adjacency matrix
        int[][] graph = {
                {0, 7, 9, 0, 0, 14},
                {7, 0, 10, 15, 0, 0},
                {9, 10, 0, 11, 0, 2},
                {0, 15, 11, 0, 6, 0},
                {0, 0, 0, 6, 0, 9},
                {14, 0, 2, 0, 9, 0}
        };

        //int[][] graph = readFromFile();
//        int[] distances = dijkstra(graph, 0); // Starting from vertex 0
//
//        System.out.println("Vertex Distance from Source:");
//        for (int i = 0; i < distances.length; i++) {
//            System.out.println("Vertex " + i + " : " + distances[i]);
//        }

        long time_1 = System.currentTimeMillis();
        int source = 0;

        BinomialHeap pq = new BinomialHeap();

        pq.insert(source,source+"",0);

        boolean[] visited =  new boolean[graph.length];


        while (!pq.isEmpty()){

            BinomialHeapNode rem = pq.extractMin();

            if (visited[rem.vertex] == true){
                continue;
            }

            visited[rem.vertex] = true;

            System.out.println(rem.vertex+" "+"via"+rem.psf+" @ "+rem.key);



            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[rem.vertex][neighbor] != 0 && !visited[neighbor]) {
                   pq.insert(rem.key+graph[rem.vertex][neighbor], rem.psf + neighbor,neighbor);
                }
            }

            long time_2 = System.currentTimeMillis();
            long difference = time_2 - time_1;

            System.out.println("-------------Dijkstra Algorithm--------------");
            System.out.println("PriorityQueue: Binomial Min Heap");
            System.out.println("Vertices: "+graph.length);
            System.out.println("Execution time: " + difference+" milliseconds " +"( "+ difference/1000 + " seconds )" );
        }
    }
}