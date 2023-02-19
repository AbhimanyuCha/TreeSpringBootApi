package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
public class TreeServiceImpl implements TreeService {
    static final int COUNT = 10;
    TreeNode root;
    List<TreeNode> freeRoots = new ArrayList<>();

    private void bfs(List<String> values){
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

    private void build(List<String> values){
        freeRoots.clear();
        bfs(values);
        freeRoots.add(root);
        print2DUtil(root, 0);
    }

    private void print2DUtil(TreeNode root, int space) {
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

    private TreeNode dfs(TreeNode it, int key){
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

    private void delete(int key){
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

    @Override
    public void makeTree(List<String> values){
        build(values);
    }

    @Override
    public void deleteKey(int val) {
        delete(val);
    }

    @Override
    public List<Integer> getRoots() {
        return freeRoots.stream().map(t -> t.getVal()).collect(Collectors.toList());
    }
}