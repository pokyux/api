package top.shiftregister.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

@RestController
public class Login {

    final String serverAddr = Server.serverAddress;
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String, String> passList = new HashMap<>();

    @GetMapping("/login")
    public String login() {
        return "login api";
    }

    @GetMapping("/login/send-token")
    public Status sendToken(@RequestParam(value = "mail", defaultValue = "") String email) throws JsonProcessingException {
        if (email.equals("")) return new Status(1, "缺少参数");
        String ret = HttpRequest.get(serverAddr + "/mail/send-token?mail=" + email);
        if (ret.equals("")) return new Status(2, "验证服务器不在线");
        System.out.println(ret);
        return mapper.readValue(ret, Status.class);
    }

    @GetMapping("/login/check-token")
    public Status checkToken(@RequestParam(value = "mail", defaultValue = "") String email, @RequestParam(value = "token", defaultValue = "") String token) throws JsonProcessingException {
        if (email.equals("") || token.equals("")) return new Status(2, "缺少参数");
        String ret = HttpRequest.get(serverAddr + "/mail/check-token?mail=" + email + "&token=" + token);
        if (ret.equals("")) return new Status(3, "验证服务器出错");
        String pass = "" + Math.abs(new Random().nextInt());
        Status status = mapper.readValue(ret, Status.class);
        if (status.getStatus() == 1) return new Status(1, "验证码错误");
        if (status.getStatus() == 0) {
            passList.put(email, pass);
            return new Status(0, pass);
        }
        return new Status(3, "验证服务器出错");
    }

    @GetMapping("/login/check-pass")
    public Status checkPass(@RequestParam(value = "mail", defaultValue = "") String email, @RequestParam(value = "pass", defaultValue = "") String pass) {
        String stdPass = passList.get(email);
        if (stdPass == null) return new Status(2, "此邮箱没有登录成功过");
        if (!stdPass.equals(pass)) return new Status(1, "登录失效");
        return new Status(0, "验证成功");
    }
}
