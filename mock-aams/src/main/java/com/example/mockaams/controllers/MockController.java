package com.example.mockaams.controllers;

import com.example.mockaams.models.AccessDetails;
import com.example.mockaams.models.AccessRequest;
import com.example.mockaams.models.AccessResponse;
import com.example.mockaams.models.RoleUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MockController {

    @PostMapping("/pd07/intAccess/v1/access")
    public ResponseEntity<AccessResponse> getAccess(@RequestBody AccessRequest accessRequest){
        AccessResponse accessResponse = new AccessResponse();
        accessResponse.setHttpCode("200");
        accessResponse.setHttpMessage("Ok");
        accessResponse.setAccessDetails(new ArrayList<>());
//        for (String id : accessRequest.getFunction()){
//            AccessDetails details = new AccessDetails();
//            details.setFunctionId(id);
//            details.setHasAccess(true);
//            details.setGlobal(false);
//            details.setAccessScopes(List.of("ALL"));
//            accessResponse.getAccessDetails().add(details);
//        }

        if ("39fade9f-af0b-49fa-828f-fef17fcfc9dc".equals(accessRequest.getUserId())){
            List.of("SY68000101", "SY68000201").stream().forEach(id -> {
                AccessDetails details = new AccessDetails();
                details.setFunctionId(id);
                details.setHasAccess(true);
                details.setGlobal(false);
                accessResponse.getAccessDetails().add(details);
            });
        } else if ("e8862a37-0e85-4c45-8d21-9281e4abeba0".equals(accessRequest.getUserId())){
            List.of("SY68000202").stream().forEach(id -> {
                AccessDetails details = new AccessDetails();
                details.setFunctionId(id);
                details.setHasAccess(true);
                details.setGlobal(false);
                accessResponse.getAccessDetails().add(details);
            });
        }

        return new ResponseEntity<>(accessResponse, HttpStatus.OK);
    }

    @PostMapping("/pd07/intAccess/v1/role-user")
    public ResponseEntity<RoleUserResponse> getRoles(){
        RoleUserResponse roleUserResponse = new RoleUserResponse();
        roleUserResponse.setHttpCode("200");
        roleUserResponse.setHttpMessage("Ok");
        roleUserResponse.setUserList(List.of("39fade9f-af0b-49fa-828f-fef17fcfc9dc", "e8862a37-0e85-4c45-8d21-9281e4abeba0"));
        return new ResponseEntity<>(roleUserResponse, HttpStatus.OK);
    }

    @PostMapping("/pd07/intAccess/v1/last-access")
    public ResponseEntity<Map<String, String>> lastAccess(){
        Map<String, String> map = new HashMap<>();
        map.put("httpCode", "200");
        map.put("httpMessage", "Ok");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/pd07/intAccess/v1/authorisation-log")
    public ResponseEntity<Map<String, String>> authorizationLog(){
        Map<String, String> map = new HashMap<>();
        map.put("httpCode", "200");
        map.put("httpMessage", "Ok");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
