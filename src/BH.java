import java.io.IOException;
import java.util.Arrays;

public class BH {


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

        int source = 0;

        BinaryHeap pq = new BinaryHeap();

        pq.insert(new Node(source,0,source+""));

        boolean[] visited =  new boolean[graph.length];


        while (!pq.isEmpty()){

            Node rem = pq.extractMin();

            if (visited[rem.vertex] == true){
                continue;
            }

            visited[rem.vertex] = true;

            System.out.println(rem.vertex+" "+"via"+rem.psf+" @ "+rem.distance);



            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[rem.vertex][neighbor] != 0 && !visited[neighbor]) {
                    pq.insert(new Node(neighbor,rem.distance+graph[rem.vertex][neighbor], rem.psf + neighbor));
                }
            }
        }
    }
}