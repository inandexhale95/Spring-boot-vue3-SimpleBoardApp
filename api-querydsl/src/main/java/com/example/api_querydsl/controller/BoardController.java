package com.example.api_querydsl.controller;

import com.example.api_querydsl.dto.BoardAndReplyCount;
import com.example.api_querydsl.repository.BoardQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardQueryRepository boardQueryRepository;

    @GetMapping("/list")
    public ResponseEntity<List<BoardAndReplyCount>> list(@RequestParam String type, @RequestParam String keyword) {
        List<BoardAndReplyCount> boardAndReplyCountList = boardQueryRepository.getBoardAndReplyCountList(type, keyword);

        return new ResponseEntity<>(boardAndReplyCountList, HttpStatus.OK);
    }
}
