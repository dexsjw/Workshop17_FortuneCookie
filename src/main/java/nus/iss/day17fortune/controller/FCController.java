package nus.iss.day17fortune.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.iss.day17fortune.service.FortuneCookie;

@RestController
@RequestMapping(path = "/cookies", produces = MediaType.APPLICATION_JSON_VALUE)
public class FCController {

    @Autowired
    public FortuneCookie cookieService;

    @GetMapping()
    public ResponseEntity<String> getCookie(@RequestParam(defaultValue = "1") Integer count) {
        if (count < 1 || count > 10) {

            // building JSON error message to return
            JsonObjectBuilder errBuilder = Json.createObjectBuilder();
            errBuilder.add("error", "count must be between 1 and 10 inclusive");
            JsonObject error = errBuilder.build();

            // creating error response
            // ResponseEntity<String> re = new ResponseEntity<>("count must be between 1 and 10 inclusive", HttpStatus.BAD_REQUEST);
            // ResponseEntity<String> re = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                //.body("count must be between 1 and 10 inclusive");
            ResponseEntity<String> re = ResponseEntity.badRequest().body(error.toString());
            return re;
        }

        // creating a list of fortune cookies
        List<String> cookies = cookieService.getCookies(count);

        // build JSON OK-200 response
        JsonArrayBuilder cookiesBuilder = Json.createArrayBuilder();
        for (String cookie: cookies) {
            cookiesBuilder.add(cookie);
        } 
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        responseBuilder.add("cookies", cookiesBuilder).add("timestamp", System.currentTimeMillis());
        JsonObject okResponse = responseBuilder.build();

        return ResponseEntity.ok(okResponse.toString());
    }
    
}
