package com.cosmos.service.impl;

import com.cosmos.entity.*;
import com.cosmos.entity.keyID.CTPhieuNhap_ID;
import com.cosmos.model.ImportResponse;
import com.cosmos.model.OrderImportNote;
import com.cosmos.repository.ImportNoteDetailRepository;
import com.cosmos.repository.ImportNoteRepository;
import com.cosmos.repository.ProductRepository;
import com.cosmos.service.ImportingNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImportingNoteServiceImpl implements ImportingNoteService {

    private final ImportNoteRepository impRepo;
    private final ImportNoteDetailRepository impDetailRepo;
    private final ProductRepository productRepo;

    @Override
    @Transactional
    public void createImportingNote(List<OrderImportNote> importNoteList, Employee employee) {
        try {
            ImportNote impNote = new ImportNote();

            impNote.setEmployee(employee);

            Date now = new Date();

            impNote.setCreateday(now);
            float totalprice = 0;
            for (OrderImportNote order : importNoteList) {
                totalprice += order.getPrice() * order.getNumber();
            }
            impNote.setTotalprice(totalprice);
            System.out.println(impNote.toString());
            try {
                impNote.setStatus(1);
                impRepo.save(impNote);
                int id = -1;
                id = impRepo.maxID();
                for (OrderImportNote order : importNoteList) {
                    ImportNoteDetail detail = new ImportNoteDetail();
                    CTPhieuNhap_ID idImport = new CTPhieuNhap_ID(id, order.getProductid());
                    detail.setId(idImport);
                    detail.setNumber(order.getNumber());
                    Product product = new Product();
                    product.setId(order.getProductid());
                    detail.setProduct(product);
                    detail.setImportNote(impNote);
                    detail.setPrice(order.getPrice());
                    System.out.println(order.getPrice());
                    impDetailRepo.save(detail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
@Transactional
    @Override
    public void confirmImportingNote( Employee employee, Integer importNoteId) {
        ImportNote imp = impRepo.getById(importNoteId);
        imp.setStatus(2);
        imp.setEmployee(employee);

        List<ImportNoteDetail> detailList = impDetailRepo.findAllById_Importnoteid(importNoteId);

        for(ImportNoteDetail  detail : detailList){
            Product product = productRepo.getById(detail.getId().getProductid());
            float totalcostBefore = product.getCost()*product.getNumber();
            float totalpriceNewProduct      = detail.getPrice()*detail.getNumber();
            float totalcostAfter = (totalcostBefore+totalpriceNewProduct)/(product.getNumber()+detail.getNumber());
            int numberAfter = product.getNumber()+detail.getNumber();
            product.setCost(totalcostAfter);
            product.setNumber(numberAfter);
             productRepo.save(product);
        }
    impRepo.save(imp);
    }

   @Transactional
    @Override
    public void cancelImportingNote(Employee employee, Integer importNoteId) {
        ImportNote imp = impRepo.getById(importNoteId);
        imp.setStatus(0);
        imp.setEmployee(employee);

       impRepo.save(imp);
    }

    @Override
    public List<ImportResponse> ListImport() {
        List<ImportNote> Listimp = impRepo.findAll(Sort.by(Sort.Order.desc("createday")));
        List<ImportResponse> listImportResponse = new ArrayList<>();
        for (ImportNote imp : Listimp) {
            if (imp.getImportNoteDetails().size() != 0) {
                ImportResponse importResponse = new ImportResponse();
                importResponse.setImportId(imp.getId());

                importResponse.setNameManufacture(imp.getImportNoteDetails().get(0).getProduct().getManufacture().getName());
                importResponse.setNameEmployee(imp.getEmployee().getName());
                importResponse.setCreateday(imp.getCreateday());
                importResponse.setTotalprice(imp.getTotalprice());
                importResponse.setStatus(imp.getStatus());
                listImportResponse.add(importResponse);
            }
        }
        return listImportResponse;
    }

    @Override
    public ImportResponse getSingleImportNote(Integer importNoteId) {
        ImportNote imp = impRepo.getById(importNoteId);
        ImportResponse importResponse = new ImportResponse();
        if (imp.getImportNoteDetails().size() != 0) {
            importResponse.setImportId(imp.getId());
            importResponse.setNameManufacture(imp.getImportNoteDetails().get(0).getProduct().getManufacture().getName());
            importResponse.setNameEmployee(imp.getEmployee().getName());
            importResponse.setCreateday(imp.getCreateday());
            importResponse.setTotalprice(imp.getTotalprice());
            importResponse.setStatus(imp.getStatus());
        }
        return importResponse;
    }
}
