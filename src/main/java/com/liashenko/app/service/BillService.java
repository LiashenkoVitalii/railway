package com.liashenko.app.service;

import com.liashenko.app.service.dto.BillDto;

import java.util.Optional;

//Interface provides methods for rendering bills
public interface BillService {

    //Method returns bill for user based on entered data
    Optional<BillDto> showBill(Long routeId, Long trainId, Long fromStationId, Long toStationId, String trainName,
                               String firstName, String lastName, Integer vagonTypeId, String date);
}
