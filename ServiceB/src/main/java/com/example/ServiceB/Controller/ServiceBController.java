package com.example.ServiceB.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class ServiceBController {
    private final WebClient webClient; //임시

    @Autowired
    public ServiceBController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/")
    public String hello(Model model) {
        return "main";
    }

    @GetMapping("/echoB")
    @ResponseBody
    public String echoB(@RequestParam String msg) {
        log.info("Echo B 수신 성공");
        return "Echo B 수신 성공 from Service B";
    }

    @PostMapping("/echoA")
    @ResponseBody
    public RedirectView echoA() {
        String response = webClient.get().uri(uriBuilder -> uriBuilder.path("/echoA")
                .queryParam("msg", "echo A 발송").build()).retrieve().bodyToMono(String.class).block();
        log.info("{}", response);
        return new RedirectView("/");
    }
}
