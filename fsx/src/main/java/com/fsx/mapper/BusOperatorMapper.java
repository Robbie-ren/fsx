package com.fsx.mapper;

import com.fsx.dto.BusOperatorReqDto;
import com.fsx.dto.BusOperatorUserProfileRespDto;
import com.fsx.model.BusOperator;
import org.springframework.stereotype.Component;

@Component
public class BusOperatorMapper {

    public BusOperator mapToEntity(BusOperatorReqDto dto){
        BusOperator busOperator = new BusOperator();
        busOperator.setCompanyName(dto.companyName());
        busOperator.setOfficeAddress(dto.officeAddress());
        busOperator.setOwnerName(dto.ownerName());
        busOperator.setEmail(dto.email());
        busOperator.setPhoneNumber(dto.phoneNumber());
        return busOperator;
    }

    public BusOperatorUserProfileRespDto mapToDto(BusOperator busOperator){
        return new BusOperatorUserProfileRespDto(busOperator.getUser().getId(),
                busOperator.getId(),
                busOperator.getUser().getUsername(),
                busOperator.getCompanyName(),
                busOperator.getOwnerName(),
                busOperator.getOfficeAddress(),
                busOperator.getEmail(),
                busOperator.getPhoneNumber(),
                busOperator.getUser().getRole(),
                busOperator.getUser().getCreatedAt());
    }
}
