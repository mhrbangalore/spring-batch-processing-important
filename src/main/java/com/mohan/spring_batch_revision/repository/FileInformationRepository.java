package com.mohan.spring_batch_revision.repository;

import com.mohan.spring_batch_revision.entity.FileInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileInformationRepository extends JpaRepository<FileInformation, Long> {

    Optional<FileInformation> findByFileName(String fileName);

    Optional<FileInformation> findByFileNameAndFileType(String fileName, String fileType);

    @Query("SELECT f FROM FileInformation f " +
            "WHERE (:fileName IS NULL OR f.fileName = :fileName) " +
            "AND (:fileType IS NULL OR f.fileType = :fileType)")
    Optional<FileInformation> findByFileNameAndFileTypeOptional(
            @Param("fileName") String fileName,
            @Param("fileType") String fileType);

}
