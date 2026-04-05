package com.webhook;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class Server {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/orca-webhook-out")
    public ResponseEntity<String> webhookOut(@RequestBody(required = false) Map<String, Object> body) throws Exception {

        if (body == null) {
            return ResponseEntity.badRequest().body("Bad Request");
        }

        // debug: print incoming payload to stdout
        System.out.println("Received:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));

        // get the event that triggered this request (rows:add, rows:update, rows:delete, test)
        String event = (String) body.get("___orca_event");

        // NOTE:
        // orca system fields start with ___
        // you can access the value of each field using the field name (body.get("Name"), body.get("Barcode"))
        // body.get("___orca_sheet_name") - the sheet that triggered the event
        // body.get("___orca_user_email") - email of the user (HTTPS only)
        switch (event != null ? event : "") {
            case "rows:add":
                // TODO: do something when a row has been added
                break;
            case "rows:update":
                // TODO: do something when a row has been updated
                break;
            case "rows:delete":
                // TODO: do something when a row has been deleted
                break;
            case "test":
                // TODO: do something when the user in the web app hits the test button
                break;
        }

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/trigger-webhook-in")
    public ResponseEntity<String> triggerWebhookIn() {

        // The following example adds a new row to a sheet, setting the value of Barcode, Name, Quantity and Description
        // TODO: change url to https://api.orcascan.com/sheets/{id}
        Map<String, Object> data = new HashMap<>();
        data.put("___orca_action", "add");
        data.put("Barcode", "0123456789");
        data.put("Name", "New 1");
        data.put("Quantity", 12);
        data.put("Description", "Add new row example");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.postForObject("https://httpbin.org/post", new HttpEntity<>(data, headers), String.class);

        return ResponseEntity.ok("OK");
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}