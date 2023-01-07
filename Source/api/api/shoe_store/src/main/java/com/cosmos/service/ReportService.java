package com.cosmos.service;

import com.cosmos.model.GetDateFromAndTo;

public interface ReportService {

  void getRankReport(String staffName, GetDateFromAndTo date);

  void getDailyRevenueReport(String staffName, GetDateFromAndTo date);

  void stockReport(String staffName);


    void getProfitReport(String name);
}
