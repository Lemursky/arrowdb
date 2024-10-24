package com.example.arrowdb.services;

import com.example.arrowdb.entity.Quality;

import java.util.List;

public interface QualityService {
    List<Quality> getAllQualities();
    Quality getQualityById(Integer id);
}
