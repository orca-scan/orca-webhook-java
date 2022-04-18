package com.webhook;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableAutoConfiguration
public class Application {

    @RequestMapping(
        value = "/orca-webhook-out", 
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

    public static void webhook_in(){
        RestTemplate restTemplate = new RestTemplate();
        // The following example adds a new row to a sheet, setting the value of Barcode, Name, Quantity and Description
        // TODO: change url to https://api.orcascan.com/sheets/{id}
        String url = "https://httpbin.org/post";
        JSONObject json = new JSONObject();
        json.put("___orca_action", "add");
        json.put("Barcode", "0123456789");
        json.put("Name", "New 1");
        json.put("Quantity", 12);
        json.put("Description", "Add new row example");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(json.toJSONString(), headers);
        String answer = restTemplate.postForObject(url, entity, String.class);
        System.out.println(answer);
    }

    @RequestMapping(
        value = "/trigger-webhook-in", 
        method = RequestMethod.GET)
    String triggerWebhookIn() throws Exception {
        webhook_in();
        return "ok";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}