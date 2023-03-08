package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TreeServiceTest {

    TreeService treeService;

    @BeforeEach
    public void setup() {
        treeService = new TreeServiceImpl();
    }

    @Test
    public void makeTreeTest() {
        treeService.makeTree(getNodesList());
        assertTrue(treeService.getRoots().size() == 1);
    }

    @Test
    public void deleteKey() {
        treeService.makeTree(getNodesList());
        treeService.deleteKey(1);
        assertTrue(treeService.getRoots().size() == 2);
        assertTrue(treeService.getRoots().get(0) == 2);
        assertTrue(treeService.getRoots().get(1) == 3);
    }

    private List<String> getNodesList() {
        return List.of("1",
                "2",
                "3",
                "4",
                "5",
                "null",
                "6",
                "null",
                "null",
                "null",
                "null",
                "7",
                "null",
                "null",
                "null");
    }
}
