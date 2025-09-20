package com.mohan.spring_batch_revision.utils;

import com.mohan.spring_batch_revision.service.FileInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileNamesSupplier {

    public static final String CUSTOMER_LOAD_FILE_NAME = "customers.csv";
    public static final String CUSTOMER_LOAD_FILE_TYPE = FileTypes.LOAD.toString();

    private final FileInformationService fileInformationService;

    public FileNamesSupplier(FileInformationService fileInformationService) {
        this.fileInformationService = fileInformationService;
    }

    public String getCustomerLoadFileName(){
        log.info("Searching for customer load file: {} | type: {} in resources folder", CUSTOMER_LOAD_FILE_NAME, CUSTOMER_LOAD_FILE_TYPE);
        return fileInformationService.getByFileNameAndType(CUSTOMER_LOAD_FILE_NAME, CUSTOMER_LOAD_FILE_TYPE).getFileName();
    }
}
