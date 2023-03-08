package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class TreeAppTest {

    @Mock
    TreeService treeService;

    private static final String badRequestBody = "Please Enter some values";
    private static final int BAD_REQUEST_STATUS_CODE = 400;
    private static final int OK_STATUS_CODE = 200;

    TreeAppApplication treeAppApplication;

    @BeforeEach
    public void setup() {
        initMocks(this);
        treeAppApplication = new TreeAppApplication(treeService);
    }

    @Test
    public void makeTreeControllerTest_withNullValuesPassed_shouldReturnBadRequest() {
        ResponseEntity response = treeAppApplication.makeTreeController(Request.builder().values(null).build());
        assertTrue(response.getStatusCode().equals(HttpStatusCode.valueOf(BAD_REQUEST_STATUS_CODE)));
        assertTrue(response.getBody().equals(badRequestBody));
    }

    @Test
    public void makeTreeControllerTest_withAlphaNumericValues_shouldThrowsNumberFormatException() {
        doThrow(new NumberFormatException()).when(treeService).makeTree(any());
        ResponseEntity response = treeAppApplication
                .makeTreeController(Request
                        .builder()
                        .values(List.of("abc", "1", "2"))
                        .build());
        verify(treeService).makeTree(any());
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(BAD_REQUEST_STATUS_CODE));
    }

    @Test
    public void makeTreeController_withValidValues_shouldReturnSuccess() {
        ResponseEntity response = treeAppApplication
                .makeTreeController(Request
                        .builder()
                        .values(List.of("1", "2", "3"))
                        .build());
        verify(treeService).makeTree(any());
        assertTrue(response.getStatusCode().equals(HttpStatusCode.valueOf(OK_STATUS_CODE)));
    }

    @Test
    public void deleteKeyController_withValidValue_shouldReturnSuccess() {
        doNothing().when(treeService).makeTree(any(List.class));
        treeAppApplication
                .makeTreeController(Request
                        .builder()
                        .values(List.of("1", "2", "3", "null", "null", "null", "null"))
                        .build());

        verify(treeService).makeTree(any(List.class));

        when(treeService.getRoots()).thenReturn(List.of(2, 3));
        ResponseEntity response = treeAppApplication.deleteNodeController("1");
        assertTrue(response.getStatusCode().equals(HttpStatusCode.valueOf(OK_STATUS_CODE)));
        assertEquals(response.getBody(), List.of(2, 3));
    }
}
