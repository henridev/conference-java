package com.capgemini.service;

import com.capgemini.model.Speaker;
import com.capgemini.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("speakerService")
public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository repository;

    public SpeakerServiceImpl() {
        System.out.println("SpeakerServiceImpl no args contructor");

    }

    @Autowired
    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        System.out.println("SpeakerServiceImpl repo contructor");
        this.repository = speakerRepository;
    }

    @PostConstruct
    private void initialize() {
        System.out.println("called after constructor");
    }

    public List<Speaker> findAll() {
        return repository.findAll();
    }


    public void setRepository(SpeakerRepository repository) {
        System.out.println("SpeakerService setter gets auto injected with speaker repository");
        this.repository = repository;
    }
}
