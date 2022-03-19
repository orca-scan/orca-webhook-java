package com.webhook;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Application {

    @RequestMapping(
        value = "/", 
        method = RequestMethod.POST)
    String index(@RequestBody Map<String, Object> payload)  throws Exception {

        // dubug purpose: show in console raw data received
        System.out.println(payload);

        // get the name of the action that triggered this request (add, update, delete, test)
        String action = payload.get("___orca_action").toString();

        // get the name of the sheet this action impacts
        String sheetName = payload.get("___orca_sheet_name").toString();

        // get the email of the user who preformed the action (empty if not HTTPS)
        String userEmail = payload.get("___orca_user_email").toString();

        // NOTE:
        // orca system fields start with ___
        // you can access the value of each field using the field name (data.Name, data.Barcode, data.Location)
        switch (action) {
            case "add":
                // TODO: do something when a row has been added
                break;
            case "update":
                // TODO: do something when a row has been updated
                break;
            case "delete":
                // TODO: do something when a row has been deleted
                break;
            case "test":
                // TODO: do something when the user in the web app hits the test button
                break;
        }
        return "ok";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}