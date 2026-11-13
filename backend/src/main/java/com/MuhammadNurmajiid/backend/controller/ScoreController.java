package com.MuhammadNurmajiid.backend.controller;

import com.MuhammadNurmajiid.backend.model.Score;
import com.MuhammadNurmajiid.backend.Service.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    // endpoint get
    // GET /api/scores
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> scores = scoreService.getAllScores();
        return ResponseEntity.ok(scores);
    }

    // GET /api/scores/{scoreId}
    @GetMapping("/{scoreId}")
    public ResponseEntity<?> getScoreById(@PathVariable UUID scoreId) {
        Optional<Score> score = scoreService.getScoreById(scoreId);
        if(score.isPresent()){
            return ResponseEntity.ok(score.get());
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"ID score tidak ditemukan: " + scoreId + "\"}");
        }

    }

    // GET /api/scores/leaderboard
    @GetMapping("/leaderboard")
    public  ResponseEntity<List<Score>> getLeaderboardByPoint(@RequestParam(defaultValue = "10") Integer
                                                                      limit){
        List<Score> scores = scoreService.getLeaderboard(limit);
        return ResponseEntity.ok(scores);
    }

    // GET /api/scores/above/{minValue}
    @GetMapping("/above/{minValue}")
    public ResponseEntity<List<Score>> getScoresAboveValue(@PathVariable Integer minValue){
        List<Score> scores = scoreService.getScoreAboveValue(minValue);
        return ResponseEntity.ok(scores);
    }

    // GET /api/scores/recent
    @GetMapping("/recent")
    public ResponseEntity<List<Score>> getRecentScores(){
        List<Score> scores = scoreService.getRecentScores();
        return ResponseEntity.ok(scores);
    }

    // endpoint post
    //POST /api/scores
    @PostMapping
    public ResponseEntity<?> createScore(@RequestBody Score score){
        try{
            Score newScore = scoreService.createScore(score);
            return ResponseEntity.status(HttpStatus.CREATED).body(newScore);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("{\"error\": \""  + e.getMessage() + "\"}");
        }
    }


    // endpoint delete
    // DELETE /api/scores/{scoreId}
    @DeleteMapping("/{scoreId}")
    public  ResponseEntity<?> deleteScore(@PathVariable UUID scoreId){
        try {
            scoreService.deleteScore(scoreId);
            return ResponseEntity.ok("Score berhasil dihapus");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \""  + e.getMessage()+ "\"}");
        }
    }

}
