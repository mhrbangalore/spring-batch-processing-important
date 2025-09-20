package com.mohan.spring_batch_revision.controller;

import com.mohan.spring_batch_revision.dao.FileInformationDao;
import com.mohan.spring_batch_revision.entity.FileInformation;
import com.mohan.spring_batch_revision.mapper.impl.FileInfoMapper;
import com.mohan.spring_batch_revision.repository.FileInformationRepository;
import com.mohan.spring_batch_revision.service.FileInformationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileTypeController {

    private final FileInformationService fileInformationService;
    private final FileInfoMapper fileInfoMapper;

    public FileTypeController(FileInformationService fileInformationService, FileInfoMapper fileInfoMapper) {
        this.fileInformationService = fileInformationService;
        this.fileInfoMapper = fileInfoMapper;
    }

    @PostMapping
    public ResponseEntity<FileInformationDao> addFileType(@Valid @RequestBody FileInformationDao fileInformationDao){
        FileInformation fileCreated = fileInformationService.saveFileInfo(fileInfoMapper.mapFrom(fileInformationDao));
        return new ResponseEntity<>(fileInfoMapper.mapTo(fileCreated), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<List<FileInformationDao>> addMultipleFileTypes(@Valid @RequestBody List<FileInformationDao> fileInformationDaoList){
        List<FileInformation> savedFiles = fileInformationService.saveMultipleFileTypes(fileInformationDaoList.stream()
                .map(fileInfoMapper::mapFrom).toList());
        return new ResponseEntity<>(savedFiles.stream()
                .map(fileInfoMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FileInformationDao> getFileByFileName(@RequestParam(name = "file name", required = true) String fileName,
                                                                @RequestParam(name = "file type", required = true) String fileType){
        return new ResponseEntity<>(fileInfoMapper.mapTo(fileInformationService.getByFileNameAndType(fileName, fileType)), HttpStatus.OK);
    }
























}
