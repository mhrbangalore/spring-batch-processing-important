package com.mohan.spring_batch_revision.service;

import com.mohan.spring_batch_revision.entity.FileInformation;
import com.mohan.spring_batch_revision.exception.FileNotPresent;
import com.mohan.spring_batch_revision.repository.FileInformationRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileInformationService {

    private final FileInformationRepository fileInformationRepository;

    public FileInformationService(FileInformationRepository fileInformationRepository) {
        this.fileInformationRepository = fileInformationRepository;
    }

    public FileInformation saveFileInfo(FileInformation fileInformation) {
        fileInformation.setCreatedAt(LocalDateTime.now());
        return fileInformationRepository.save(fileInformation);
    }

    public List<FileInformation> saveMultipleFileTypes(List<FileInformation> fileInformationList){
        fileInformationList.forEach(info -> info.setCreatedAt(LocalDateTime.now()));
        return fileInformationRepository.saveAll(fileInformationList);
    }

    public FileInformation getByFileNameAndType(String fileName, String fileType) {
        return fileInformationRepository.findByFileNameAndFileTypeOptional(fileName, fileType)
                .orElseThrow(() -> new FileNotPresent(
                        "File with name=" + fileName + " and type=" + fileType + " not found."
                ));
    }
}
