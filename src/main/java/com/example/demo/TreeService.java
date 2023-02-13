package com.example.demo;

import java.util.List;

public interface TreeService {
    void makeTree(List<String> values);
    void deleteKey(int val);
    List<Integer> getRoots();
}
