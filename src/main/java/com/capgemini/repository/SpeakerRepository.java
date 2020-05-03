package com.capgemini.repository;

import com.capgemini.model.Speaker;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SpeakerRepository {
    List<Speaker> findAll();
}
