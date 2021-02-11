package top.shiftregister.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Scanner;

@RestController
public class Hello {

    String indexPage = "Kininaru's API server, powered by Spring. ";

    @GetMapping("/")
    public String getDefault() {
        return indexPage;
    }

    @GetMapping("/reload-index")
    public String reloadIndex() throws IOException {
        Scanner fileInput = new Scanner(new FileInputStream("./index.html"));
        StringBuilder buf = new StringBuilder();
        while (fileInput.hasNext()) buf.append(fileInput.nextLine());
        indexPage = buf.toString();
        fileInput.close();
        return "Success";
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello! This API server is powered by Spring!";
    }
}
