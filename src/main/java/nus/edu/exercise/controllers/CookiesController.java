package nus.edu.exercise.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import nus.edu.exercise.services.FortuneCookie;

@RestController
@RequestMapping(path = "/cookies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CookiesController {

    @Autowired
    private FortuneCookie fortuneCookie;

    @GetMapping
    public ResponseEntity<String> getCookie(@RequestParam(defaultValue = "1") Integer count) {
        if((count < 1) || (count > 10)) {

            JsonObject errorMsg = Json.createObjectBuilder()
                                    .add("error", "count must be between 1 and 10")
                                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg.toString());
        }

        List<String> cookies = this.fortuneCookie.getCookies(count);
        JsonArrayBuilder cookieMsg = Json.createArrayBuilder();
        for(String s : cookies) {
            cookieMsg.add(s);
        }

/*         cookies.stream()
            .forEach(v -> cookieMsg.add(v)); */


        JsonObject okMsg = Json.createObjectBuilder()
                            .add("cookies", cookieMsg)
                            .add("timestamp", System.currentTimeMillis())
                            .build();
        return ResponseEntity.ok(okMsg.toString());
    }

    
}
