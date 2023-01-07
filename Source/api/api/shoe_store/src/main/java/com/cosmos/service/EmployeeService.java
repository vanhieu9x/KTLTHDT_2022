package com.cosmos.service;

import com.cosmos.model.Register;
import com.cosmos.model.UpdateAccount;

public interface EmployeeService {
    void addToEmployee(Register register);
    void updateCustomer(UpdateAccount account, Integer employeeId);
}
