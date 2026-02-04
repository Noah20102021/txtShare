package com.kwirll.txtshare;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class apiInterface {

    @GetMapping("/push/{key}")
    public String push(@PathVariable("key") String key, @RequestParam("value") String value, @RequestParam(value = "", required = false) String password) {
        if (functions.push(key,value,password)) {
            return "Your string is now available at "+config.host+"/get/"+key+" and will remain accessible for at least the next 5 minutes.";

        }else {
            return "Failed! Please Try another key or at another moment.";
        }
    }

    @GetMapping("/")
    public String home() {
        return "<html><body><script>window.location.href='/index.html';</script></body></html>";
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key, @RequestParam(value = "password", required = false) String password) {
        String value = functions.get(key, password);
        if (value != null) {
            return value;
        }else {
            return "Failed! Key doesn't exist or needs Password";
        }
    }

    @GetMapping("/config")
    public String getConfig() {
        return config.host;
    }

    @GetMapping(value = "/setup", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getSetup(@RequestHeader("User-Agent") String userAgent) {
        if (userAgent.contains("PowerShell")) {

            return "function txtPush($k,$t,$p){ " +
                    "  $u=\""+config.host+"/push/$($k)?value=$([uri]::EscapeDataString($t))\"; " +
                    "  if($p){$u+=\"&password=$([uri]::EscapeDataString($p))\"}; curl.exe -s $u; echo \"\" }; " +
                    "function txtGet($k,$p){ " +
                    "  $u=\""+config.host+"/get/$($k)\"; " +
                    "  if($p){$u+=\"?password=$([uri]::EscapeDataString($p))\"}; curl.exe -s $u; echo \"\" }; " +
                    "function txtHelp(){ " +
                    "  Write-Host '--- txtshare Help ---' -Fore Cyan; " +
                    "  Write-Host 'txtPush <key> '<text>' [password]  -> Save text'; " +
                    "  Write-Host 'txtGet  <key> [password]         -> Retrieve text'; " +
                    "  Write-Host 'txtHelp                          -> Show this help' }; " +
                    "Write-Host '--txtshare (Windows/PowerShell) Ready!-- \n use txtHelp for help'";
        } else {

            return "txtPush() { local v=$(python3 -c \"import urllib.parse; print(urllib.parse.quote_plus('$2'))\"); " +
                    "local u=\""+config.host+"/push/$1?value=$v\"; " +
                    "[ ! -z \"$3\" ] && u=\"$u&password=$(python3 -c \"import urllib.parse; print(urllib.parse.quote_plus('$3'))\")\"; " +
                    "curl -s \"$u\"; echo; }; " +
                    "txtGet() { local u=\""+config.host+"/get/$1\"; " +
                    "[ ! -z \"$2\" ] && u=\"$u?password=$(python3 -c \"import urllib.parse; print(urllib.parse.quote_plus('$2'))\")\"; " +
                    "curl -s \"$u\"; echo; }; " +
                    "txtHelp() { echo -e '\\033[0;36m--- txtshare Help ---\\033[0m'; " +
                    "echo 'txtPush <key> '<text>' [password]  -> Save text'; " +
                    "echo 'txtGet  <key> [password]         -> Retrieve text'; " +
                    "echo 'txtHelp                          -> Show this help'; }; " +
                    "echo '--txtshare (Linux/Mac) Ready!-- \n use txtHelp for help'";
        }
    }

}
