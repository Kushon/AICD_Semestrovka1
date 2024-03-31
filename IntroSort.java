
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

class IntroSort {

    private static int count = 0;

    public static void main(String[] args) {

        try {
            File file = new File("arrays.txt");
            Scanner in = new Scanner(file);
            ArrayList<int[]> arrays = new ArrayList<>();

            while (in.hasNextLine()) {
                String size = in.nextLine();
                String line = in.nextLine();
                String[] elements = line.split(" ");
                int[] array = new int[elements.length];

                for (int i = 0; i < elements.length; i++) {
                    array[i] = Integer.parseInt(elements[i]);
                }

                arrays.add(array);
            }

            in.close();

            for (int[] arr : arrays) {
                long startTime = System.nanoTime();
                count = 0;
                sort(arr);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000; // перевод в миллисекунды

                System.out.println("Sorted Array: " + Arrays.toString(arr));
                System.out.println("Number of Comparisons: " + count);
                System.out.println("Time taken: " + duration + " ms");

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }


//    Сортировка IntroSort

    private static void swap(int[] array, int index1, int index2)
    {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    private static int medianOfThree(int[] array, int a, int b, int c)
    {
        if (array[a] < array[b] && array[b] < array[c])
        {
            return b;
        }
        else if (c <= array[b] && array[b] <= array[a])
        {
            return b;
        }
        else if (array[a] < array[c] && array[c] <= array[b])
        {
            return c;
        }
        else if (array[b] < array[c] && array[c] <= array[a])
        {
            return c;
        }
        else if (array[b] <= array[a] && array[a] < array[c])
        {
            return a;
        }
        return a; // else if (c <= a && a < b)
    }

    private static int findLastParentNodeOfGivenMaxHeap(int lastHeapElementIndex)
    {
        return (lastHeapElementIndex > 2) ? (lastHeapElementIndex - 1) / 2 : 0;
    }

    private static void insert(int[] array, int elementToBeInserted)
    {
        int tempValueOfElement = array[elementToBeInserted];
        for (int i = elementToBeInserted; i > 0; i--)
        {
            count++;
            if (tempValueOfElement < array[i - 1])
            {
                array[i] = array[i - 1];
            }
            else
            {
                array[i] = tempValueOfElement;
                return;
            }
        }
        array[0] = tempValueOfElement;
    }

    private static void insertionSort(int[] array)
    {
        if (array.length > 1)
        {
            for (int i = 0; i < array.length - 1; i++)
            {
                count++;
                if (array[i + 1] <= array[i])
                {
                    insert(array, i + 1);
                }
            }
        }
    }

    private static void rebuildMaxHeapFromGivenParentNode(int[] array, int parentNodeIndex, int lastHeapElementIndex)
    {
        int leftChildIndex = 2 * parentNodeIndex + 1;
        int rightChildIndex = 2 * parentNodeIndex + 2;

        if (lastHeapElementIndex >= rightChildIndex)
        {
            count++;
            if (array[rightChildIndex] >= array[leftChildIndex])
            {
                count++;
                if (array[parentNodeIndex] < array[rightChildIndex])
                {
                    swap(array, parentNodeIndex, rightChildIndex);
                    rebuildMaxHeapFromGivenParentNode(array, rightChildIndex, lastHeapElementIndex);
                }
            }
            else
            {
                count++;
                if (array[parentNodeIndex] < array[leftChildIndex])
                {
                    swap(array, parentNodeIndex, leftChildIndex);
                    rebuildMaxHeapFromGivenParentNode(array, leftChildIndex, lastHeapElementIndex);
                }
            }
        }
        else
        {

            if (lastHeapElementIndex >= leftChildIndex)
            {
                count++;
                if (array[parentNodeIndex] < array[leftChildIndex])
                {
                    swap(array, parentNodeIndex, leftChildIndex);
                    rebuildMaxHeapFromGivenParentNode(array, leftChildIndex, lastHeapElementIndex);
                }
            }
        }
    }

    private static void buildMaxHeap(int[] array, int lastHeapElementIndex)
    {
        for (int i = findLastParentNodeOfGivenMaxHeap(lastHeapElementIndex); i >= 0; i--)
        {
            rebuildMaxHeapFromGivenParentNode(array, i, lastHeapElementIndex);
        }
    }

    private static void heapSort(int[] array)
    {
        buildMaxHeap(array, array.length - 1);
        for (int i = array.length - 1; i > 0; i--)
        {
            swap(array, 0, i);
            rebuildMaxHeapFromGivenParentNode(array, 0, i - 1);
        }
    }



    private static int quicksort(int[] array, int start, int end)
    {
        int pivot = array[end];
        int i = start;

        for (int j = start; j < end; j++)
        {
            count++;
            if (array[j] <= pivot)
            {
                swap(array, i++, j);
            }
        }
        swap(array, i++, end);
        return i;
    }

    private static void introSort(int[] array, int start, int end, int depthLimit)
    {
        if ((end - start) < 16)
        {
            insertionSort(array);
            return;
        }
        if (0 == depthLimit)
        {
            heapSort(array);
            return;
        }
        int pivot = medianOfThree(array, start, (end - start) / 2, end);
        swap(array, pivot, end);

        int partitionIndexAfterQuickSort = quicksort(array, start, end);
        introSort(array, start, partitionIndexAfterQuickSort - 1, depthLimit - 1);
        introSort(array, partitionIndexAfterQuickSort + 1, end, depthLimit);
    }

    private static void sort(int[] array)
    {
        introSort(array, 0, array.length - 1, (int) Math.log(array.length));
    }
}



