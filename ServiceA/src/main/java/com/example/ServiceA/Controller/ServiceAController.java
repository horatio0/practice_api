package com.example.ServiceA.Controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class ServiceAController {
    private final WebClient webClient;

    @Autowired
    public ServiceAController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    @GetMapping("/")
    public String hello(Model model) {
        return "main";
    }

    @GetMapping("/echoA")
    @ResponseBody
    public String echoA(@RequestParam String msg){
        log.info("Echo A 수신 성공");
        return "Echo A 수신 성공 from Service A";
    }

    @PostMapping("echoB")
    @ResponseBody
    public RedirectView echoB(){
        String response = webClient.get().uri(uriBuilder -> uriBuilder.path("/echoB")
                .queryParam("msg", "echo B 발송").build()).retrieve().bodyToMono(String.class).block();
        log.info("{}", response);
        return new RedirectView("/");
    }
}
