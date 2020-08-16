
/*
*
*                               排序
*
*
* */

import java.util.Arrays;
import java.util.Stack;

public class TestDemo {



    //插入排序

    //以升序为例
    public static void insertSort(int[] array){
        //有序区间:[0,bound]
        //无序区间:[bound,size]
        //每次选择无序区间的第一个元素，在有序区间中选择合理的位置插入
        //bound也可以从0开始 但是更方便的就从1下标开始 和零号下标进行比较
        for (int bound = 1; bound < array.length ; bound++) {
            //先定义一个临时变量tmp 保存bound
            int tmp = array[bound];
            //再和boumd-1处的值进行比较
            int cur = bound - 1;
            for ( ; cur >= 0; cur--) {
                if (array[cur] > tmp){
                    //如果cur大于bound下标的值则把cur下标的值往后挪一位
                    array[cur + 1] = array[cur];
                }else{
                    break;
                }
            }
            array[cur + 1] = tmp;
        }
    }




    //希尔排序
    public static void shellSort(int[] array){
        //分组选取gap数 size/2 ， size/4 ， size/8，...1
        int gap = array.length / 2;
        while (gap > 1 ){
            insertSortGap(array,gap);
            gap = gap / 2;
        }
        insertSortGap(array,1);
    }

    //分组情况下，每组相邻元素下标差gap
    //当gap=1时，与插排代码一样
    private static void insertSortGap(int[] array,int gap) {
        //从每组的[1]号元素开始
        for (int bound = gap; bound < array.length; bound++) {
            int tmp = array[bound];
            int cur = bound - gap;//bound位置中相邻的前一个元素
            for (; cur >= 0 ; cur -= gap) {
                if (array[cur] > tmp){
                    //把cur位置元素放到cur + gap位置
                    array[cur + gap] = array[cur];
                }else {
                    break;
                }
            }
            array[cur + gap] = tmp;
        }
    }






    //选择排序
    public static void selectSort(int[] array){
        for (int bound = 0; bound < array.length; bound++) {
            //此时借助bound把数组分为两个区间
            //[0，bound）为已排序区间
            //[bound，size）为待排序区间
            //接下来在待排序区间找最小值，放到bound位置上
            for (int cur = bound + 1; cur < array.length; cur++) {
                if (array[cur] < array[bound]){
                    //以bound位置元素座位擂台
                    //拿当前元素和擂台元素进行比较大小
                    //满足if条件句 当前已升序为例所以cur<bound则交换位置
                    swap(array,cur,bound);
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }






    //堆排序
    public static void heapSort(int[] array){
        //1.创建一个堆
        createHeap(array);
        int heapSize = array.length;
        for (int i = 0; i < array.length - 1; i++) {
            //交换堆顶元素和对重的最后一个元素
            swap(array,0,heapSize-1);
            //3.把堆的最后一个元素删掉
            heapSize--;
            //4.针对当前的堆 从根节点开始进行向下调整
            shiftDown(array,heapSize,0);
        }
    }
    //建堆
    private static void createHeap(int[] array) {
        for (int i = (array.length - 1 - 1)/2; i >= 0; i--) {
            shiftDown(array,array.length,i);
        }
    }
    //向下调整
    private static void shiftDown(int[] array, int size, int index) {
        int parent = index;
        int child = 2*parent + 1;
        while (child < size){
            if (child + 1 < size && array[child + 1] > array[child]){
                child = child + 1;
            }
            if (array[child] > array[parent]){
                int tmp = array[child];
                array[child] = array[parent];
                array[parent] = tmp;
            }else {
                break;
            }
            parent = child;
            child = 2*child + 1;
        }
    }






    //冒泡排序
    public static void bubbleSort(int[] array){
        //从后往前遍历，每次找最小的元素放前面
        //[0,bound)已排序区间
        //[bound,size)待排序区间
        for (int bound = 0; bound < array.length; bound++) {
            for (int cur = array.length - 1 ; cur > bound ; cur--) {
                if (array[cur - 1] > array[cur]) {
                    swap(array, cur, cur - 1);
                }
            }
        }
    }







    //快速排序
    public static void quickSort(int[] array){
        quickSortHelper(array,0,array.length-1);
    }

    //[left,riht]前闭后闭区间，针对当前范围进行快速排序
    private static void quickSortHelper(int[] array, int left, int right) {
        if (left >= right){
            //区间中有0个元素或者1个元素
            return;
        }
        //返回值表示整理之后，基准值所处在的位置
        int index = partition(array,left,right);
        //递归访问左右区间
        //[left,index-1]
        //[index+1,right]
        quickSortHelper(array,left,index-1);
        quickSortHelper(array,index+1,right);
    }


    private static int partition(int[] array, int left, int right) {
        int baseValue = array[right];
        int i = left;
        int j = right;
        while (i < j){
            //从左往右找到一个大于基准值baseValue的元素
            while (i < j && array[i] <= baseValue){
                i++;
            }
            //此时i指向的位置要么和j重合，要么就是一个比基准值大的元素

            //从右往左找到一个小于基准值baseValue的元素
            while (i < j && array[j] >= baseValue){
                j--;
            }
            //此时j指向的位置要么和i重合，要么就是一个比基准值小的元素

            //交换 i  j的值
            if (i < j ){
                swap(array,i,j);
            }
        }
        //循环结束交换重合位置元素和基准值元素
        swap(array,i,right);
        //返回的基准值所在位置
        return i;
    }




    //快速排序的非递归实现
    public static void quickSort2(int[] array){
        //栈中保存的元素相当于要进行partition操作的范围下标
        //非递归实现要借助栈
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(array.length - 1);
        while (!stack.isEmpty()){
            //先放入左再放入的右，栈取元素时相反，先取右，再取左
            int right = stack.pop();
            int left = stack.pop();
            if (left >= right){
                //区间中只有一个或者没有元素
                continue;
            }
            int index = partition(array,left,right);
            //把右子树入栈[index+1 , right]
            stack.push(index + 1);
            stack.push(right);
            //把左子树入栈[left,index - 1]
            stack.push(left);
            stack.push(index - 1);
        }
    }









    //归并排序
    public static void mergeSort(int[] array){
        //[0,length)
        mergeSortHelper(array,0,array.length);
    }

    private static void mergeSortHelper(int[] array, int left, int right) {
        if (right - left <= 1){
            //当前排序中有0个或者1个元素就不需要排序
            return;
        }
        //针对[针对left，right)区间，分成对等的两个区间
        int mid = (left + right) / 2;
        //两个区间
        //[left,mid)
        //[mid,right)
        mergeSortHelper(array,left,mid);
        mergeSortHelper(array,mid,right);
        //通过上面的递归，认为这两个区间都被排好序了，接下来进行合并
        merge(array,left,mid,right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int cur1 = left;
        int cur2 = mid;
        int[] output = new int[right - left];
        int outputIndex = 0;
        while (cur1 < mid && cur2 < right) {
            //下面条件中如果是 < 就无法保证稳定性
            if (array[cur1] <= array[cur2]) {
                output[outputIndex] = array[cur1];
                cur1++;
                outputIndex++;
            } else {
                output[outputIndex] = array[cur2];
                cur2++;
                outputIndex++;
            }
        }
        while (cur1 < mid ){
            output[outputIndex] = array[cur1];
            cur1++;
            outputIndex++;
        }
        while (cur2 < right){
            output[outputIndex] = array[cur2];
            cur2++;
            outputIndex++;
        }
        for (int i = 0; i < right - left; i++) {
            array[left + i] = output[i];
        }
    }








//2.23.42
    //归并排序的非递归实现
    public static void mergeSort2(int[] array){
        for (int gap = 1; gap < array.length; gap *= 2) {
            for (int i = 0; i < array.length; i += 2*gap) {
                //每执行一次循环，相当于两个长度为gap的数组进行合并
                //[i,i+gap)
                //[i+gap ,i+2*gap)
                int left = i;
                int mid = i + gap;
                int right = i + 2 * gap;
                if (mid > array.length){
                    mid = array.length;
                }
                if (right > array.length){
                    right = array.length;
                }
                merge(array,left,mid,right);
            }
        }
    }



    public static void main(String[] args) {
        int[] array = {9,2,5,4,3,11};
        mergeSort2(array);
        System.out.println(Arrays.toString(array));
    }

}
