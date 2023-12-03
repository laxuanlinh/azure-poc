package com.example.mockaams.models;

import lombok.Data;

import java.util.List;

@Data
public class AccessResponse {

    private String httpCode;
    private String httpMessage;
    private List<AccessDetails> accessDetails;

}

