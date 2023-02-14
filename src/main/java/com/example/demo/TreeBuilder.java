package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TreeBuilder {
    static final int COUNT = 10;
    TreeNode root;
    List<TreeNode> freeRoots = new ArrayList<>();
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

    void bfs(List<String> values){
        Queue<TreeNode> q = new LinkedList<>();
        root = new TreeNode();
        q.offer(root);
        for(String sval : values) {
            TreeNode node = q.poll();
            if (sval.equals("null")) {
                node.setVal(-1);
                continue;
            }
            int val = Integer.parseInt(sval);
            node.setVal(val);
            node.left = new TreeNode();
            node.right = new TreeNode();
            q.offer(node.left);
            q.offer(node.right);
        }

        q.clear();
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode node = q.poll();
            if(node.left != null) {
                if (node.left.getVal() == -1)
                    node.left = null;
                else
                    q.offer(node.left);
            }
            if(node.right != null) {
                if (node.right.getVal() == -1)
                    node.right = null;
                else
                    q.offer(node.right);
            }
        }
    }

    void build(List<String> values){
        freeRoots.clear();
        bfs(values);
        freeRoots.add(root);
        print2DUtil(root, 0);
    }

    void print2DUtil(TreeNode root, int space) {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.getVal() + "\n");

        // Process left child
        print2DUtil(root.left, space);
    }

    TreeNode dfs(TreeNode it, int key){
        if(it == null)
            return null;
        if(it.getVal() == key){
            if(it.left != null)
                freeRoots.add(it.left);
            if(it.right != null)
                freeRoots.add(it.right);
            return null;
        }
        it.left = dfs(it.left, key);
        it.right = dfs(it.right, key);
        return it;
    }

    void delete(int key){
        for(int i = 0 ; i < freeRoots.size(); i++) {
            TreeNode root_it = freeRoots.get(i);
            if(root_it.getVal() == key) {// deleting a root
                freeRoots.set(i, null);
                if(root_it.left != null)
                    freeRoots.add(root_it.left);
                if(root_it.right != null)
                    freeRoots.add(root_it.right);
            }
            else
                dfs(root_it, key);
        }
        freeRoots = freeRoots.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}