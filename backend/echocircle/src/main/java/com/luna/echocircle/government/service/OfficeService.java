package com.luna.echocircle.government.service;

import com.luna.echocircle.government.entity.Office;
import com.luna.echocircle.government.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OfficeService {


    private final OfficeRepository officeRepository;


    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }


}

