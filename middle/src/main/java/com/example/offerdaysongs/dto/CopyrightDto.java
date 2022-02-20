package com.example.offerdaysongs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CopyrightDto {
    long id;
    CompanyDto company;
    long validity;
    long price;
    RecordingDto recording;
}
