package com.mohan.spring_batch_revision.mapper.impl;

import com.mohan.spring_batch_revision.dto.FileInformationDto;
import com.mohan.spring_batch_revision.entity.FileInformation;
import com.mohan.spring_batch_revision.mapper.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FileInfoMapper implements IMapper<FileInformation, FileInformationDto> {

    private final ModelMapper modelMapper;

    public FileInfoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FileInformation mapFrom(FileInformationDto fileInformationDto) {
        return modelMapper.map(fileInformationDto, FileInformation.class);
    }

    @Override
    public FileInformationDto mapTo(FileInformation fileInformation) {
        return modelMapper.map(fileInformation, FileInformationDto.class);
    }
}
