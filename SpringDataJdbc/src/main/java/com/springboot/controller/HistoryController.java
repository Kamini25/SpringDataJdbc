package com.springboot.controller;

import com.springboot.entity.TransactionEntity;
import com.springboot.model.History;
import com.springboot.repository.HistoryRepository;
import com.springboot.services.HistoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class HistoryController {
    HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }
    @RequestMapping(value="/getPassbook/{phoneNo}",method = RequestMethod.GET)
    public Set<TransactionEntity> getPassbook(@PathVariable("phoneNo") long phoneNo) throws Exception {
        return historyService.getPassbookDetails(phoneNo);

    }
}
