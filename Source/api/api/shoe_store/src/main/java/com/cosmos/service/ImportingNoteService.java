package com.cosmos.service;

import com.cosmos.entity.Employee;
import com.cosmos.model.ImportResponse;
import com.cosmos.model.OrderImportNote;

import java.util.List;

public interface ImportingNoteService {
    void createImportingNote(List<OrderImportNote> importNoteList, Employee employeeId);

    void confirmImportingNote(Employee employeeId, Integer importNoteId);

    void cancelImportingNote(Employee employeeId, Integer importNoteId);

    List<ImportResponse> ListImport();

    ImportResponse getSingleImportNote(Integer id);
}
