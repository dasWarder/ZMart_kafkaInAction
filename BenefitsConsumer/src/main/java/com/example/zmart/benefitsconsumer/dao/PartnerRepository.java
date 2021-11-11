package com.example.zmart.benefitsconsumer.dao;

import com.example.zmart.benefitsconsumer.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartnerRepository extends PagingAndSortingRepository<Partner, Long> {
}
