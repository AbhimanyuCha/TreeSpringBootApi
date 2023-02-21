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

    //can be improved, it will break on specially -1 value. [Needs to be fixed]
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

    /*
            1
         2     3
     nul nul nul nul
     */

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

    /*
            input validation points :
                1. We need to check if the values are of size (2 * x + 1)
                2. Integer check for the values in both delete and create (tick)
                3. Check if input is null (if the list is empty) (include in the business logic)
                    - For null it should return empty list.
                4. Check null in delete.

        SOLID Principles followed :
            1. Single Responsiblitiy : Each class should 1 reason to change (yes)
            2. Open Closed Principle : Open for extension and closed (no)
            3. Liskov Substitution (Yes)
            4. Interface Segregation
            5. Dependency of Inversion (Yes) class depend on interfaces rather than concrete classes.

     */

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
        return freeRoots.stream().filter(Objects::nonNull).map(t -> t.getVal()).collect(Collectors.toList());
    }
}