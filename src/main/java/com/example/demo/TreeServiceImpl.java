package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class TreeServiceImpl implements TreeService{
    @Autowired
    TreeBuilder treeBuilder;

    @Override
    public void makeTree(List<String> values){
        treeBuilder.build(values);
    }

    @Override
    public void deleteKey(int val) {
        treeBuilder.delete(val);
    }

    @Override
    public List<Integer> getRoots() {
        return treeBuilder.freeRoots.stream().map(t -> t.getVal()).collect(Collectors.toList());
    }
}
