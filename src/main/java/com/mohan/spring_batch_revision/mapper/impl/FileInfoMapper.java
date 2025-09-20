package com.mohan.spring_batch_revision.mapper.impl;

import com.mohan.spring_batch_revision.dao.FileInformationDao;
import com.mohan.spring_batch_revision.entity.FileInformation;
import com.mohan.spring_batch_revision.mapper.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FileInfoMapper implements IMapper<FileInformation, FileInformationDao> {

    private final ModelMapper modelMapper;

    public FileInfoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FileInformation mapFrom(FileInformationDao fileInformationDao) {
        return modelMapper.map(fileInformationDao, FileInformation.class);
    }

    @Override
    public FileInformationDao mapTo(FileInformation fileInformation) {
        return modelMapper.map(fileInformation, FileInformationDao.class);
    }
}
