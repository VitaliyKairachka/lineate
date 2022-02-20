package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CompanyDto;
import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.SingerDto;
import com.example.offerdaysongs.dto.requests.CreateRecordingRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.service.RecordingService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recordings")
public class RecordingController {
    private static final String ID = "id";
    private final RecordingService recordingService;

    public RecordingController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @GetMapping("/")
    public List<RecordingDto> getAll() {
        return recordingService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public RecordingDto get(@PathVariable(ID) long id) {
        var recording = recordingService.getById(id);
        return convertToDto(recording);
    }

    @PostMapping("/")
    public RecordingDto create(@RequestBody CreateRecordingRequest request) {
        return convertToDto(recordingService.create(request));
    }

    private RecordingDto convertToDto(Recording recording) {
        var singer = recording.getSinger();
        return new RecordingDto(recording.getId(),
                recording.getTitle(),
                recording.getVersion(),
                recording.getReleaseTime(),
                singer != null ? new SingerDto(singer.getId(), singer.getName()) : null,
                convertListToDtoList(recording.getCopyrights()));
    }

    private List<CopyrightDto> convertListToDtoList(List<Copyright> copyrights) {
        List<CopyrightDto> copyrightDtos = new ArrayList<>();
        for (var copyright : copyrights) {
            var company = copyright.getCompany();
            var recording = copyright.getRecording();
            var singer = copyright.getRecording().getSinger();
            var copyrightDto = new CopyrightDto(
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
            copyrightDtos.add(copyrightDto);
        }
        return copyrightDtos;
    }
}
