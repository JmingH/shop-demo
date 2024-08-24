package com.ming.shopdemo.useraccount.model.mapper;

import com.ming.shopdemo.useraccount.model.UserAccount;
import com.ming.shopdemo.useraccount.model.dto.RegisterRequest;
import com.ming.shopdemo.useraccount.model.dto.UserAccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserAccountMapper {

    UserAccount toEntity(RegisterRequest request);

    UserAccountDto toDto(UserAccount userAccount);
}
