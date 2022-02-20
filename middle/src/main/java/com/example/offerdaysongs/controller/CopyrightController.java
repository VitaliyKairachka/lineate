package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CompanyDto;
import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.SingerDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import com.example.offerdaysongs.service.RecordingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/copyrights")
public class CopyrightController {
    private static final String ID = "id";
    private final CopyrightService copyrightService;
    private final RecordingService recordingService;

    public CopyrightController(CopyrightService copyrightService, RecordingService recordingService) {
        this.copyrightService = copyrightService;
        this.recordingService = recordingService;
    }

    @GetMapping("/")
    public List<CopyrightDto> getAll() {
        return copyrightService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public CopyrightDto get(@PathVariable(ID) long id) {
        var copyright = copyrightService.getById(id);
        return convertToDto(copyright);
    }

    @PostMapping("/")
    public CopyrightDto create(@RequestBody CreateCopyrightRequest request) {
        return convertToDto(copyrightService.create(request));
    }

    @PutMapping("/")
    public CopyrightDto update(@RequestBody CreateCopyrightRequest request) {
        return convertToDto(copyrightService.create(request));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public void delete(@PathVariable(ID) long id) {
        copyrightService.delete(id);
    }

    private CopyrightDto convertToDto(Copyright copyright) {
        var company = copyright.getCompany();
        var recording = copyright.getRecording();
        var singer = copyright.getRecording().getSinger();
        return new CopyrightDto(
                copyright.getId(),
                company != null ? new CompanyDto(company.getId(), company.getName()) : null,
                copyright.getValidity(),
                copyright.getPrice(),
                recording != null ? new RecordingDto(
                        recording.getId(),
                        recording.getTitle(),
                        recording.getVersion(),
                        recording.getReleaseTime(),
                        singer != null ? new SingerDto(singer.getId(), singer.getName()) : null,
                        null
                ) : null
        );
    }
}
