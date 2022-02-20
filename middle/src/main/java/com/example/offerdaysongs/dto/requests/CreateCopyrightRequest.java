package com.example.offerdaysongs.dto.requests;

import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Recording;
import lombok.Data;

@Data
public class CreateCopyrightRequest {
    Company company;
    long validity;
    long price;
    Recording recording;
}
