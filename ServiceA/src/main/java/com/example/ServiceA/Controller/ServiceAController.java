package com.example.ServiceA.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ServiceAController {
    private final WebClient webClient;
    private String message=null; //임시

    @Autowired
    public ServiceAController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message",message);
        return "main";
    }

    @GetMapping("/echoA")
    public void echoA(@RequestParam String msg){
        message=msg;
    }

    @PostMapping("echoB")
    @ResponseBody
    public RedirectView echoB(){
        webClient.get().uri(uriBuilder -> uriBuilder.path("/echoB")
                .queryParam("msg", "echo B 발송").build()).retrieve().toBodilessEntity().subscribe();

        return new RedirectView("/");
    }
}
