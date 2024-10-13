import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class WeightedGraphGenerator {

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Input number of nodes, minimum weight, and maximum weight
        System.out.print("Enter number of nodes: ");
        int n = scanner.nextInt();
        System.out.print("Enter minimum weight: ");
        int minWeight = scanner.nextInt();
        System.out.print("Enter maximum weight: ");
        int maxWeight = scanner.nextInt();

        if (n <= 0 || minWeight >= maxWeight) {
            System.out.println("Invalid input. Please ensure that the number of nodes is positive and minWeight < maxWeight.");
            return;
        }
        long time_1 = System.currentTimeMillis();
        int[][] graph = generateConnectedGraph(n, minWeight, maxWeight);
        long time_2 = System.currentTimeMillis();
        long difference = time_2 - time_1;
        System.out.println( difference + " milliseconds" );
        System.out.println( difference/1000 + " seconds" );

        saveToFile(graph);
        System.out.println("--------PRINTING PRINTING PRINTING-------------------");
        // printGraph(graph);

        int[][] rgraph = readFromFile();
        for(int i = 0;i<n;i++){
            for (int j = 0;j<n;j++){
                if(graph[i][j] != rgraph[i][j]){
                    System.out.println(graph[i][j]);
                    System.out.print("--");
                    System.out.println(rgraph[i][j]);
                    System.out.println("Mismatch");
                    return;
                }

            }


        }

        System.out.println("Arrays are equal");

    }

    private static void saveToFile(int[][] graph) throws IOException {
        StringBuilder builder = new StringBuilder();
        //Date date = new Date();
        String date = "TestMat1";
        for(int i = 0; i < graph.length; i++)//for each row
        {
            for(int j = 0; j < graph.length; j++)//for each column
            {
                builder.append(graph[i][j]+"");//append to the output string
                if(j < graph.length - 1)//if this is not the last row element
                    builder.append(",");//then add comma (if you don't like commas you can use spaces)
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/piyush/" + date + ".txt"));
        writer.write(builder.toString());//save the string representation of the board
        writer.close();
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

        //  printGraph(graph);
        return graph;
    }
    private static int[][] generateConnectedGraph(int n, int minWeight, int maxWeight) {
        int[][] graph = new int[n][n];
        int maxEdges = (n * (n - 1)) / 2;
        int desiredEdges = (int) Math.round(0.75 * maxEdges);
        int edgeCount = 0;

        // Initialize the graph with no edges
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = 0;
            }
        }

        // Ensure the graph is connected by creating a spanning tree
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1;
            addEdge(graph, i, j, minWeight, maxWeight);
            edgeCount++;
        }

        // Add additional edges to reach the desired edge count
        while (edgeCount < desiredEdges) {
            int u = random.nextInt(n);
            int v = random.nextInt(n);
            if (u != v && graph[u][v] == 0) {
                addEdge(graph, u, v, minWeight, maxWeight);
                edgeCount++;
            }
        }

        return graph;
    }

    private static void addEdge(int[][] graph, int u, int v, int minWeight, int maxWeight) {
        int weight = random.nextInt(maxWeight - minWeight + 1) + minWeight;
        graph[u][v] = weight;
        graph[v][u] = weight;
    }

    private static void printGraph(int[][] graph) {
        int n = graph.length;
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(graph[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
