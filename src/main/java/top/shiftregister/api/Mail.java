package top.shiftregister.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

@RestController
public class Mail {

    HashMap<String, Long> history = new HashMap<>();
    HashMap<String, Long> requestHistory = new HashMap<>();
    HashMap<String, String> tokenList = new HashMap<>();
    final long spacingInterval = 60000;

    @GetMapping("/mail")
    public String mailPage() {
        return "邮件API";
    }
    @GetMapping("/mail/")
    public String mailPage1() {
        return "邮件API";
    }

    boolean checkAndFreshRequestHistory(String address) {
        Long last = requestHistory.get(address);
        if (last == null) {
            requestHistory.put(address, new Date().getTime());
            return true;
        }
        long now = new Date().getTime();
        if (now - last < spacingInterval) return false;
        requestHistory.put(address, now);
        return true;
    }

    @GetMapping("/mail/send-token")
    public Status sendMail(@RequestParam(value = "mail", defaultValue = "") String mailAddress, HttpServletRequest request) {
        if (!checkAndFreshRequestHistory(request.getRemoteAddr())) return new Status(2, "发送过于频繁. 在" + spacingInterval / 1000 + "秒内你只能发送一次验证码.");
        if (mailAddress.equals("")) return new Status(1, "缺少参数");
        Long time = history.get(mailAddress);
        if (time != null && (new Date().getTime() - time) < spacingInterval) return new Status(2, "发送过于频繁. 在" + spacingInterval / 1000 + "秒内你只能发送一次验证码.");
        String token = "" + Math.abs(new Random().nextInt() % 10000000);
        if (!sendEmail(mailAddress, "api", "验证码", "你的验证码为: " + token + ". 如果你没有发送类似请求, 请忽略.")) return new Status(3, "邮箱有误, 或者邮件系统出错.");
        tokenList.put(mailAddress, token);
        history.put(mailAddress, new Date().getTime());
        return new Status(0, "已向" + mailAddress + "发送邮件");
    }

    @GetMapping("/mail/check-token")
    public Status checkToken(@RequestParam(value = "mail", defaultValue = "") String address, @RequestParam(value = "token", defaultValue = "") String token) {
        if (address.equals("") || token.equals("")) return new Status(2, "缺少参数");
        String stdToken = tokenList.get(address);
        if (stdToken == null) return new Status(3, "未发送验证码至此邮箱");
        if (!stdToken.equals(token)) return new Status(1, "验证码错误");
        tokenList.remove(address);
        return new Status(0, "验证成功");
    }

    boolean sendEmail(String to, String from, String title, String message) {
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(title);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
