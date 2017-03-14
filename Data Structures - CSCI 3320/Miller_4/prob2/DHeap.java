import java.util.Scanner;
import java.util.Arrays;

public class DHeap
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int[] initial = new int[1000];
        int counter = 0;

        System.out.print("Enter heap elements: ");
        String sampleString = scan.nextLine();

        //convert input to array of numbers
        String[] stringArray = sampleString.split(" ");
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            String numberAsString = stringArray[i];
            intArray[i] = Integer.parseInt(numberAsString);
        }
        System.out.print("Enter d: ");
        int d = scan.nextInt();

        //create Dary Heap
        DHeapClass dh = new DHeapClass( d );
        for(int x =0; x<stringArray.length; x++)
            dh.insert(intArray[x]); 
        dh.printHeap();
        int choice = 0;

        do
        {
            System.out.println("Press 1) for insert, 2) for deleteMin, 3) for new d value, 4) to quit");

            System.out.print("Enter choice: ");
            choice = scan.nextInt();
            switch (choice)
            {
                case 1 : // insert element
                    System.out.print("Enter integer element to insert: ");
                    dh.insert( scan.nextInt() );
                    dh.printHeap();
                    break;
                case 2 : // delete element 0
                    dh.delete(0);
                    dh.printHeap();
                    break;
                case 3 : // change d
                    System.out.print("Enter d: ");
                    dh.changed(scan.nextInt());
                    dh.printHeap();
                    break;
                case 4 :
                    System.out.println("Program Terminated");
                    break;
                default :
                    System.out.println("Invalid choice");
                    break;
            }
        } while (choice!=4);
    }
}

class DHeapClass
{

    private int d;
    private int heapSize;
    private int[] heap;

    public DHeapClass(int numChild)
    {
        heapSize = 0;
        d = numChild;
        heap = new int[1000];
        Arrays.fill(heap, -1);
    }

    public void changed(int newd)
    {
        d = newd;
        percolateUp(heapSize - 1);
        percolateDown(0);
    }
 
    private int parent(int i)
    {
        return (i - 1)/d;
    }

    private int getIndex(int i, int k)
    {
        return d * i + k;
    }

    public void insert(int x)
    {
        heap[heapSize++] = x;
        percolateUp(heapSize - 1);
    }

    public int findMin( )
    {
        return heap[0];
    }

    public int delete(int ind)
    {
        int keyItem = heap[ind];
        heap[ind] = heap[heapSize - 1];
        heapSize--;
        percolateDown(ind);
        return keyItem;
    }

    private void percolateUp(int childInd)
    {
        int tmp = heap[childInd];
        while (childInd > 0 && tmp < heap[parent(childInd)])
        {
            heap[childInd] = heap[ parent(childInd) ];
            childInd = parent(childInd);
        }
        heap[childInd] = tmp;
    }

    private void percolateDown(int ind)
    {
        int child;
        int tmp = heap[ ind ];
        while (getIndex(ind, 1) < heapSize)
        {
            child = minChild(ind);
            if (heap[child] < tmp)
                heap[ind] = heap[child];
            else
                break;
            ind = child;
        }
        heap[ind] = tmp;
    }

    private int minChild(int ind)
    {
        int bChild = getIndex(ind, 1);
        int k = 2;
        int pos = getIndex(ind, k);
        while ((k <= d) && (pos < heapSize))
        {
            if (heap[pos] < heap[bChild])
                bChild = pos;
            pos = getIndex(ind, k++);
        }
        return bChild;
    }

    public void printHeap()
    {
        System.out.printf("Output: Heap (d=%d): ",d);
        for (int i = 0; i < heapSize; i++)
            System.out.print(heap[i] +" ");
        System.out.println();
    }
}

