package com.example.mockaams.models;

import lombok.Data;

import java.util.List;

@Data
public class RoleUserResponse {

    private String httpCode;
    private String httpMessage;
    private List<String> userList;

}
