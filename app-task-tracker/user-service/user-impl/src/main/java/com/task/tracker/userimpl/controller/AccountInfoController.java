package com.task.tracker.userimpl.controller;

import com.task.tracker.commonlib.dto.AccountUpdateRequest;
import com.task.tracker.commonlib.dto.AccountUpdateResponse;
import com.task.tracker.userapi.controller.UserApi;
import com.task.tracker.userapi.dto.AccountDto;
import com.task.tracker.userimpl.entity.AccountInfo;
import com.task.tracker.userimpl.service.AccountInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountInfoController implements UserApi {
    private final AccountInfoService accountInfoService;

    @Override
    public ResponseEntity<AccountDto> getAccountInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(accountInfoService.findAccountInfoById(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AccountDto>> getTopAccountInfo() {
        return ResponseEntity.ok(accountInfoService.findUsersAboveAverageXp());
    }

    @Override
    public ResponseEntity<AccountUpdateResponse> updateAccountInfo(
            @PathVariable UUID id,
            @RequestBody AccountUpdateRequest accountInfo
    ) {
        return ResponseEntity.ok(accountInfoService.update(id, accountInfo));
    }
}
