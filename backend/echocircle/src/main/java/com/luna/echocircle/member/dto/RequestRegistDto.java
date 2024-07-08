package com.luna.echocircle.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegistDto {
    private String email;
    private String nickname;
    private String address;
    private String phone;
}
