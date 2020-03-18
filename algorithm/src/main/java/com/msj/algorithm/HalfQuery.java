package com.msj.algorithm;

/**
 * @author Vincent.M
 * @mail mengshaojie@188.com
 * @date 2020-03-18
 * @copyright ©2020 孟少杰 All Rights Reserved
 * @desc 二分查找
 */
public class HalfQuery {

    public int halfQuery(int a[],int value){
        int low = 0;
        int high = a.length-1;

        while(low <= high){
            //防止low和high过大导致int溢出，所以不采用(high+low)/2
            int mid = (high+low)>>1;
            if(a[mid]== value){
                return a[mid];
            }else if(a[mid]<value){
                low = a[mid]+1;
            }else{
                high = a[mid]-1;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        System.out.println(10<<3);
    }
}
