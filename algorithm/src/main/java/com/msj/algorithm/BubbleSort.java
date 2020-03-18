package com.msj.algorithm;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-14
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc 冒泡排序
 *
 * 冒泡排序只会操作相邻的两个数据。每次冒泡操作都会对相邻的两个元素进行比较，看是否满足大小关系要求。如果不满足就让它俩互换。一次冒泡会让至少一个元素移动到它应该在的位置，重复 n 次，就完成了 n 个数据的排序工作。
 */
public class BubbleSort {

    // 冒泡排序，a表示数组，n表示数组大小
    public void bubbleSort(int[] a, int n) {
        if (n <= 1) return;

        for(int i=0;i<n;i++){
            boolean flag = false;
            //每次排序都会排好一位，所以-i
            for(int j=0;j<n-i-1;j++){
                if(a[j]>a[j+1]){
                    int temp = a[j+1];
                    a[j+1] = a[j];
                    a[j] = temp;
                    // 表示有数据交换
                    flag = true;
                }
            }
            // 没有数据交换，提前退出
            if(!flag) break;
        }
    }
}
