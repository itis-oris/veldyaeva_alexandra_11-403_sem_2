package com.task.tracker.userapi.controller;

import com.task.tracker.commonlib.dto.AccountUpdateRequest;
import com.task.tracker.commonlib.dto.AccountUpdateResponse;
import com.task.tracker.userapi.dto.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Tag(name = "Account Info", description = "API для информации об аккаунте пользователя")
@RequestMapping("/api/account-info")
public interface UserApi {

    @Operation(summary = "Получить информацию об аккаунте по ID")
    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getAccountInfo(@PathVariable UUID id);

    @Operation(summary = "Получить пользователей с XP выше среднего (только ADMIN)")
    @GetMapping("/top")
    ResponseEntity<List<AccountDto>> getTopAccountInfo();

    @Operation(summary = "Обновить информацию аккаунта")
    @PostMapping("/update/{id}")
    ResponseEntity<AccountUpdateResponse> updateAccountInfo(
            @PathVariable UUID id,
            @RequestBody AccountUpdateRequest accountInfo
    );
}