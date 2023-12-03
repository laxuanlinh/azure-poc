package com.example.mockaams.models;


import lombok.Data;

import java.util.List;

@Data
public class AccessDetails {
    private String functionId;
    private boolean hasAccess;
    private boolean global;
    private List<String> accessScopes;

}
