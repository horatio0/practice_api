package com.example.ServiceB.Controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

@Controller
public class ServiceBController {
    private final WebClient webClient;
    private String message=null; //임시

    @Autowired
    public ServiceBController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", message);
        return "main";
    }

    @GetMapping("/echoB")
    public void echoB(@RequestParam String msg) {
        message = msg;
    }

    @PostMapping("/echoA")
    @ResponseBody
    public RedirectView echoA() {
        webClient.get().uri(uriBuilder -> uriBuilder.path("/echoA")
                .queryParam("msg", "echo A 발송").build()).retrieve().toBodilessEntity().subscribe();

        return new RedirectView("/");
    }
}
