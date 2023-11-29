package BoardingSystem;

import java.util.ArrayList;
import java.util.List;

class PriorityQueue {
    static class Node {
        Passenger traveller;
        Node(Passenger traveller) {
        this.traveller = traveller;
        }
    }
    List<Node> heap = new ArrayList<>();

    // Adds a passenger to the priority queue
    public void insert(Passenger traveller) {
        heap.add(new Node(traveller));
        int idx = heap.size() - 1;
        while (idx != 0) {
            int parentIdx = (idx - 1) / 2;
            if (heap.get(parentIdx).traveller.priority > heap.get(idx).traveller.priority) {
                swap(parentIdx, idx);
                idx = parentIdx;
            } else {break;}
        }
    }

    // Extracts the passenger info with the lowest priority value
    public Node extractMin() {
        Node minNode = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        int idx = 0;
        while (idx < heap.size()) {
            int leftChildIdx = idx * 2 + 1;
            int rightChildIdx = idx * 2 + 2;
            int largerChildIdx = leftChildIdx;

            if (rightChildIdx < heap.size() && 
                heap.get(rightChildIdx).traveller.priority < heap.get(leftChildIdx).traveller.priority) {
                largerChildIdx = rightChildIdx;
            }
            if (largerChildIdx < heap.size() && 
                heap.get(largerChildIdx).traveller.priority < heap.get(idx).traveller.priority) {
                swap(largerChildIdx, idx);
                idx = largerChildIdx;
            } else {break;}
        }
        return minNode;
    }

    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
