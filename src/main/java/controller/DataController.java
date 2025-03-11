package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/data")
    public String getData(@RequestParam(defaultValue = "0") int delay) throws InterruptedException {
        Thread.sleep(delay); // Simulating latency
        return "Data from " + System.getenv("NODE_NAME");
    }
}

