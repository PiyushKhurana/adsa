import java.util.ArrayList;


 class Node {
    int vertex;
    int distance;
    String psf;

    public Node(int vertex, int distance,String p) {
        this.vertex = vertex;
        this.distance = distance;
        this.psf = p;
    }
}


public class BinaryHeap {
    private final ArrayList<Node> heap;

    public BinaryHeap() {
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
