package com.task.tracker.userimpl.mapper;

import com.task.tracker.commonlib.dto.AccountUpdateRequest;
import com.task.tracker.commonlib.dto.AccountUpdateResponse;
import com.task.tracker.userapi.dto.AccountDto;
import com.task.tracker.userimpl.entity.AccountInfo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountInfoMapper {

    @Mapping(source = "xp", target = "xpCount")
    AccountUpdateResponse toResponse(AccountInfo accountInfo);

    AccountDto toDto(AccountInfo accountInfo);
}