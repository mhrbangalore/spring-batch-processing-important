package com.mohan.spring_batch_revision.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInformationDao {

    @JsonProperty(value = "file_id")
    private Long fileId;

    @JsonProperty(value = "file_name")
    @NotBlank(message = "File name cannot be null, field: file_name")
    private String fileName;

    @NotBlank(message = "File type cannot be null, field: file_type")
    @JsonProperty(value = "file_type")
    private String fileType;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

}


