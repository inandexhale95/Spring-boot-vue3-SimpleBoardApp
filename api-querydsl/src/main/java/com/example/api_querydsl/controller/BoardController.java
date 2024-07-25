package com.example.api_querydsl.controller;

import com.example.api_querydsl.BoardService;
import com.example.api_querydsl.dto.BoardAndReplyCount;
import com.example.api_querydsl.dto.PageRequestDTO;
import com.example.api_querydsl.dto.PageResultDTO;
import com.example.api_querydsl.repository.BoardQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardQueryRepository boardQueryRepository;
    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BoardAndReplyCount>> list(PageRequestDTO pageRequestDTO) {
        log.info("{}", pageRequestDTO);

        PageResultDTO<BoardAndReplyCount> result = boardService.getList(pageRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
