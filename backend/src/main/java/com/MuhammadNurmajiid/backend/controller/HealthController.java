package com.MuhammadNurmajiid.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
        @GetMapping("/health")
        public Map<String,Object> healthCheck(){
                Map<String, Object> map = new HashMap<>();
                map.put("status", "UP");
                map.put("message", "Bullet Hell Shoot-'Em-Up Backend is running");
                map.put("timestamp", System.currentTimeMillis());
                return map;
        }

        @GetMapping("/info")
        public Map<String,Object> appInfo(){
                Map<String,Object> main = new HashMap<>();
                main.put("App Name","Backend CS5");
                main.put("Version", "Beta 1.0");
                main.put("Description", "Backend for Bullet Hell Shoot-'Em-Up");

                Map<String,String> endpoints = new HashMap<>();

                endpoints.put("score", "/scores");
                endpoints.put("leaderboard", "/leaderboard");
                endpoints.put("health", "/health");

                main.put("endpoints", endpoints);

                return main;
        }
}