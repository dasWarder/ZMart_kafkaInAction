package com.example.zmart.patternsconsumer.dao;

import com.example.zmart.patternsconsumer.model.PatternsInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PatternRepository extends ElasticsearchRepository<PatternsInfo, String> {
}
