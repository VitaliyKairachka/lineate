package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyrightService {
    private final CopyrightRepository copyrightRepository;
    private final CompanyRepository companyRepository;
    private final RecordingRepository recordingRepository;
    private final SingerRepository singerRepository;

    public CopyrightService(CopyrightRepository copyrightRepository, CompanyRepository companyRepository, RecordingRepository recordingRepository, SingerRepository singerRepository) {
        this.copyrightRepository = copyrightRepository;
        this.companyRepository = companyRepository;
        this.recordingRepository = recordingRepository;
        this.singerRepository = singerRepository;
    }

    public List<Copyright> getAll() {
        return copyrightRepository.findAll();
    }

    public Copyright getById(long id) {
        return copyrightRepository.getById(id);
    }

    public Copyright create(CreateCopyrightRequest request) {
        var copyright = new Copyright();
        copyright.setCompany(request.getCompany());
        copyright.setValidity(request.getValidity());
        copyright.setPrice(request.getPrice());

        var companyDto = request.getCompany();
        if (companyDto != null) {
            var company = companyRepository.findById(companyDto.getId()).orElseGet(() -> {
                var temp = new Company();
                temp.setName(companyDto.getName());
                return companyRepository.save(temp);
            });
            copyright.setCompany(company);
        }

        var recordingDto = request.getRecording();
        if (recordingDto != null) {
            var recording = recordingRepository.findById(recordingDto.getId()).orElseGet(() -> {
                var temp = new Recording();
                var singer = recordingDto.getSinger();
                temp.setSinger(singerRepository.findById(singer.getId()).orElseGet(() -> {
                    var tempSinger = new Singer();
                    tempSinger.setName(singer.getName());
                    return singerRepository.save(tempSinger);
                }));
                temp.setTitle(recordingDto.getTitle());
                temp.setVersion(recordingDto.getVersion());
                temp.setReleaseTime(recordingDto.getReleaseTime());
                return recordingRepository.save(temp);
            });
            copyright.setRecording(recording);
        }

        return copyrightRepository.save(copyright);
    }

    public void delete(long id) {
        copyrightRepository.deleteById(id);
    }
}
