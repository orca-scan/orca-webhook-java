# orca-webhook-java

Example of how to build an [Orca Scan WebHook](https://orcascan.com/docs/api/webhooks) endpoint in using [Java](https://www.java.com/) and [Spring boot](https://spring.io/projects/spring-boot) framework.

## Install

First ensure you have [Java](https://www.java.com/) and [Maven](https://maven.apache.org/install.html) installed.

Then execute the following:

```bash
# download this example code
git clone https://github.com/orca-scan/orca-webhook-java.git

# go into the new directory
cd orca-webhook-java
```

## Run

```bash
# start the project
mvn spring-boot:run
```

Your WebHook receiver will now be running on port 8080.

You can emulate an Orca Scan WebHook using [cURL](https://dev.to/ibmdeveloper/what-is-curl-and-why-is-it-all-over-api-docs-9mh) by running the following:

```bash
curl --location --request POST 'http://127.0.0.1:8080' \
--header 'Content-Type: application/json' \
--data-raw '{
    "___orca_action": "add",
    "___orca_sheet_name": "Vehicle Checks",
    "___orca_user_email": "hidden@requires.https",
    "___orca_row_id": "5cf5c1efc66a9681047a0f3d",
    "Barcode": "4S3BMHB68B3286050",
    "Make": "SUBARU",
    "Model": "Legacy",
    "Model Year": "2011",
    "Vehicle Type": "PASSENGER CAR",
    "Plant City": "Lafayette",
    "Trim": "Premium",
    "Location": "52.2034823, 0.1235817",
    "Notes": "Needs new tires"
}'
```

### Important things to note

1. Only Orca Scan system fields start with `___`
2. Properties in the JSON payload are an exact match to the  field names in your sheet _(case and space)_
3. WebHooks are never retried, regardless of the HTTP response

## How this example works

This [example](Application.java) uses the [srping boot](https://spring.io/projects/spring-boot) framework:

```java
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
```

## Troubleshooting

If you run into any issues not listed here, please [open a ticket](https://github.com/orca-scan/orca-webhook-python/issues).

## Examples in other langauges
* [orca-webhook-dotnet](https://github.com/orca-scan/orca-webhook-dotnet)
* [orca-webhook-python](https://github.com/orca-scan/orca-webhook-python)
* [orca-webhook-go](https://github.com/orca-scan/orca-webhook-go)

## History

For change-log, check [releases](https://github.com/orca-scan/orca-webhook-node/releases).

## License

&copy; Orca Scan, the [Barcode Scanner app for iOS and Android](https://orcascan.com).