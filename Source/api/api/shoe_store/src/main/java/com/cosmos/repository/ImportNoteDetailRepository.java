package com.cosmos.repository;

import com.cosmos.entity.keyID.CTPhieuNhap_ID;
import com.cosmos.entity.ImportNoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImportNoteDetailRepository extends JpaRepository<ImportNoteDetail, CTPhieuNhap_ID> {
    @Query(nativeQuery = true, value = "Select * " +
            "from Import_Note_Detail imp where  imp.importnoteid = ?1")
    List<ImportNoteDetail> impNoteByImportID(int id);

    List<ImportNoteDetail> findAllById_Importnoteid(Integer id);

}
