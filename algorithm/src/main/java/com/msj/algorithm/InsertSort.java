package com.msj.algorithm;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-14
 * @copyright ©2018 孟少杰 All Rights Reserved
 * @desc 插入排序
 *
 * 首先，我们将数组中的数据分为两个区间，已排序区间和未排序区间。初始已排序区间只有一个元素，就是数组的第一个元素。插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间数据一直有序。重复这个过程，直到未排序区间中元素为空，算法结束。
 */
public class InsertSort {

    public static void main(String[] args){
        insertSort(new int[]{4,5,6,1,3,2});
    }

    public static void insertSort(int[] a){
        // j代表已排序区间，i代表未排序区间
        int n = a.length;
        if(n<=1) return;
        for(int i=1;i<n;i++){
            int value = a[i];
            int j = i-1;
            for(;j>=0;j--){
                if(a[j]>value){
                    // 数据交换，与冒泡排序，插入排序交换数据只赋值一次，比冒泡排序简单，大规模时候，更加节省时间
                    a[j+1] = a[j];
                }else{
                    //break 则j循环体外值就是当前循环到的值
                    break;
                }
            }
            //正常循环结束，则j是条件后的下一个值即j=-1,如果中途循环break则j是当前值
            a[j+1] = value;
        }
        for(int i=0;i<n;i++)
        System.out.println(a[i]);
    }

}
