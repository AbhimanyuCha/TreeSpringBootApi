package com.example.demo;

public class TreeNode{
    TreeNode left, right;
    int val;
    TreeNode(){}
    void setVal(int newVal){
        this.val = newVal;
        left = new TreeNode();
        right = new TreeNode();
    }
    public int getVal(){
        return this.val;
    }
}