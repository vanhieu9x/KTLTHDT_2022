package com.cosmos.repository;

import com.cosmos.entity.ImportNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImportNoteRepository extends JpaRepository<ImportNote, Integer> {
    @Query(nativeQuery = true, value="Select * from Import_Note where employeeid= ?1")
    List<ImportNote> findImportNoteByEmployee(int id);

    @Query(value="SELECT MAX(id) FROM Import_Note", nativeQuery = true )
    int maxID();
}
