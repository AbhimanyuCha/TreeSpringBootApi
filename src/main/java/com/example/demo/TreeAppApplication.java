package com.example.demo;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class TreeAppApplication {
    public final TreeService treeService;

    @Autowired
    public TreeAppApplication(TreeService treeService) {
        this.treeService = treeService;
    }

    @PostMapping("/makeTree")
    public ResponseEntity makeTreeController(@RequestBody Request request) {
        List<String> values = request.getValues();
        if (values == null)
            return ResponseEntity.badRequest().body("Please Enter some values");
        try {
            treeService.makeTree(values);
        } catch (NumberFormatException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
        return ResponseEntity.ok().body("Tree Successfully made !");
    }

    /*
        Assumption :
        1. If the node is not present then it will return the list of nodes.
        2. Assuming that it will not throw any exception as the logic is not breaking.
     */
    @PostMapping("/delete/{value}")
    public ResponseEntity deleteNodeController(@PathVariable String value) {
        try {
            int val = Integer.parseInt(value);
            treeService.deleteKey(val);
            List<Integer> roots = treeService.getRoots();
            return ResponseEntity.ok(roots);
        } catch (NumberFormatException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    private void validate(@NonNull List<String> values) throws IllegalArgumentException {
        if (!Stream.of(values).distinct().toList().equals(values))
            throw new IllegalArgumentException("Values are not valid, they should be unique and non null !");
    }


    public static void main(String[] args) {
        SpringApplication.run(TreeAppApplication.class, args);
    }
}
