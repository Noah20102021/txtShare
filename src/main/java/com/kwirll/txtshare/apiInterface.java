package com.kwirll.txtshare;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class apiInterface {

    @GetMapping("/push/{key}")
    public String push(@PathVariable("key") String key, @RequestParam("value") String value) {
        if (functions.push(key,value)) {
            return "Your string is now available at domain.com/get/"+key+" and will remain accessible for at least the next 5 minutes.";

        }else {
            return "Failed! Please Try another key or at another moment.";
        }
    }

    @GetMapping("/")
    public String home() {
        return "Hi, wellcome to txtShare!";
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key) {
        String value = functions.get(key);
        if (value != null) {
            return value;
        }else {
            return "Failed! Key doesn't exist or needs Password";
        }
    }

    @GetMapping(value = "/setup", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getSetup(@RequestHeader("User-Agent") String userAgent) {
        if (userAgent.contains("PowerShell")) {
            // Hier wurden $($k) und $($t) sowie die Anführungszeichen optimiert
            return "function txtPush($k,$t){ " +
                    "$v = [uri]::EscapeDataString($t); " +
                    "curl.exe -s \"http://localhost:8080/push/$($k)?value=$v\" }; " +
                    "function txtGet($k){ curl.exe -s \"http://localhost:8080/get/$($k)\" }; " +
                    "Write-Host '✅ txtshare (Windows) Ready! use txtPush KEY VALUE & txtGet KEY' -Fore Green";
        } else {
            // Linux/Mac Version (Python-Teil korrigiert für Query-Params)
            return "txtPush() { local e=$(python3 -c \"import urllib.parse; print(urllib.parse.quote_plus('$2'))\"); " +
                    "curl -s \"http://localhost:8080/push/$1?value=$e\"; }; " +
                    "txtGet() { curl -s \"http://localhost:8080/get/$1\"; }; " +
                    "echo '✅ txtshare (Linux/Mac) Ready! use txtPush KEY VALUE & txtGet KEY'";
        }
    }

}
