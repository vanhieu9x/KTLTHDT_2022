package com.cosmos.controller;

import com.cosmos.entity.ImportNote;
import com.cosmos.entity.ImportNoteDetail;
import com.cosmos.model.ImportResponse;
import com.cosmos.model.OrderImportNote;
import com.cosmos.repository.ImportNoteDetailRepository;
import com.cosmos.repository.ImportNoteRepository;
import com.cosmos.security.service.UserPrinciple;
import com.cosmos.service.ImportingNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ImportNoteController {

    private final ImportNoteRepository impRepo;
    private final ImportNoteDetailRepository impDetailRepo;
    private final ImportingNoteService importingNoteService;

    @CrossOrigin
    @GetMapping("/ImportNote")
    public List<ImportResponse> getAllImportNote() {
        List<ImportResponse> list = importingNoteService.ListImport();
        return  list;
    }

    @CrossOrigin
    @GetMapping("/ImportNote/employee={idEmployee}/")
    public List<ImportNote> getImportNoteByEmployee(@PathVariable int id) {
        List<ImportNote> impList = impRepo.findImportNoteByEmployee(id);
        return impList;
    }

    //    @CrossOrigin
//    @GetMapping("/ImportNote/{id}")
//    public Optional<ImportNote> getImportNoteById(@PathVariable int id) {
//        Optional<ImportNote> imp = impRepo.findById(id);
//        return imp;
//    }
    @CrossOrigin
    @GetMapping("/ImportNote/{id}")
    public ImportResponse getSingleImportNote(@PathVariable("id") Integer id) {
        ImportResponse imp = importingNoteService.getSingleImportNote(id);
        return imp;
    }

    @CrossOrigin
    @PostMapping("/ImportNote/insertImportNote")
    public ResponseEntity<Void> insertImportNote(@Validated @RequestBody List<OrderImportNote> listOrderDetail) {

        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        importingNoteService.createImportingNote(listOrderDetail, userPrinciple.getEmployee());
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping("/ImportNote/max")
    public ResponseEntity<?> maxID() {
        int id = impRepo.maxID();
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/ImportNote/Details")
    public ResponseEntity<?> getImportNoteDetail(@RequestParam int id) {
        List<ImportNoteDetail> detailList = impDetailRepo.impNoteByImportID(id);
        return new ResponseEntity<>(detailList, HttpStatus.OK);
    }

    @PutMapping("/ImportNote/update-status/{id}")
    public ResponseEntity<?> confirmImportNote(@PathVariable int id){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        importingNoteService.confirmImportingNote(userPrinciple.getEmployee(),id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ImportNote/cancelImportNote/{id}")
    public ResponseEntity<?> cancelImportNote(@PathVariable int id){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        importingNoteService.cancelImportingNote(userPrinciple.getEmployee(),id);
        return ResponseEntity.ok().build();
    }
}
