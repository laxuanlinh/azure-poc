package com.example.mockaams.models;

import lombok.Data;

import java.util.List;

@Data
public class AccessRequest {
    private String systemId;
    private String moduleId;
    private String userId;
    private List<String> function;

}
