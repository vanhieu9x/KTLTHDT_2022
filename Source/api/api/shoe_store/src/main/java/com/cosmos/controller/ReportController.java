package com.cosmos.controller;

import com.cosmos.model.GetDateFromAndTo;
import com.cosmos.security.service.UserPrinciple;
import com.cosmos.service.ReportService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/report")
@RestController
public class ReportController {

  private final ReportService reportService;

  @PutMapping("/rank")
  public ResponseEntity<Void> testPDFRank(@Valid @RequestBody GetDateFromAndTo date) {
    UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    reportService.getRankReport(userPrinciple.getEmployee().getName(), date);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/revenue")
  public ResponseEntity<Void> testPDFRevenue(@Valid @RequestBody GetDateFromAndTo date) {
    UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    reportService.getDailyRevenueReport(userPrinciple.getEmployee().getName(), date);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/stock")
  public ResponseEntity<Void> testPDFStock() {
    UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    reportService.stockReport(userPrinciple.getEmployee().getName());
    return ResponseEntity.ok().build();
  }

  @PutMapping("/Profit")
  public ResponseEntity<Void> testPDFProfit() {
    UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    reportService.getProfitReport(userPrinciple.getEmployee().getName());
    return ResponseEntity.ok().build();
  }
}
