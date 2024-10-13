import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DijkstraAlgorithm {

    // Min binary heap implementation using ArrayList
    static class MinHeap {
        private final ArrayList<Node> heap;

        public MinHeap() {
            this.heap = new ArrayList<>();
        }

        public void insert(Node node) {
            heap.add(node);
            int currentIndex = heap.size() - 1;
            while (currentIndex > 0) {
                int parentIndex = (currentIndex - 1) / 2;
                if (heap.get(currentIndex).distance >= heap.get(parentIndex).distance) {
                    break;
                }
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            }
        }

        public Node extractMin() {
            if (heap.isEmpty()) return null;
            Node minNode = heap.get(0);
            Node lastNode = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, lastNode);
                minHeapify(0);
            }
            return minNode;
        }

        public void decreaseKey(int index, int newDistance) {
            heap.get(index).distance = newDistance;
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                if (heap.get(index).distance >= heap.get(parentIndex).distance) {
                    break;
                }
                swap(index, parentIndex);
                index = parentIndex;
            }
        }

        public boolean isEmpty() {
            return heap.isEmpty();
        }

        public int getIndex(Node node) {
            return heap.indexOf(node);
        }

        private void minHeapify(int index) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < heap.size() && heap.get(leftChild).distance < heap.get(smallest).distance) {
                smallest = leftChild;
            }
            if (rightChild < heap.size() && heap.get(rightChild).distance < heap.get(smallest).distance) {
                smallest = rightChild;
            }
            if (smallest != index) {
                swap(index, smallest);
                minHeapify(smallest);
            }
        }

        private void swap(int i, int j) {
            Node temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    static class Node {
        int vertex;
        int distance;

        public Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static int[] dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] distances = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        MinHeap minHeap = new MinHeap();
        minHeap.insert(new Node(source, 0));

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.extractMin();
            int currentVertex = currentNode.vertex;

            if (visited[currentVertex]) continue;
            visited[currentVertex] = true;

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (graph[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    int newDist = distances[currentVertex] + graph[currentVertex][neighbor];
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        int index = minHeap.getIndex(new Node(neighbor, distances[neighbor]));
                        if (index != -1) {
                            minHeap.decreaseKey(index, newDist);
                        } else {
                            minHeap.insert(new Node(neighbor, newDist));
                        }
                    }
                }
            }
        }
        return distances;
    }

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
        int[] distances = dijkstra(graph, 0); // Starting from vertex 0

        System.out.println("Vertex Distance from Source:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("Vertex " + i + " : " + distances[i]);
        }
    }

    private static int[][] readFromFile() throws IOException {
        String savedGameFile = "/Users/piyush/TestMat1.txt";

        BufferedReader reader;
        reader = new BufferedReader(new FileReader(savedGameFile));

        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        reader = new BufferedReader(new FileReader(savedGameFile));

        int[][] graph = new int[lines][lines];


        String line = "";
        int row = 0;
        while((line = reader.readLine()) != null)
        {
            String[] cols = line.split(","); //note that if you have used space as separator you have to split on " "
            int col = 0;
            for(String  c : cols)
            {
                graph[row][col] = Integer.parseInt(c);
                col++;
            }
            row++;
        }
        reader.close();

        return graph;
    }
}
