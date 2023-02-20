package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TreeService {
    void makeTree(List<String> values);
    void deleteKey(int val);
    List<Integer> getRoots();
}
