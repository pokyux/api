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
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

@RestController
public class Mail {

    HashMap<String, Long> history = new HashMap<>();
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

    @GetMapping("/mail/token")
    public Status sendMail(@RequestParam(value = "address", defaultValue = "") String mailAddress) {
        if (mailAddress.equals("")) return new Status(1, "Parameter missing.");

        Long time = history.get(mailAddress);
        if (time != null && (new Date().getTime() - time) < spacingInterval) return new Status(2, "Sent too frequently. You can send an email only one time in " + spacingInterval / 1000 + "s.");

        String token = String.format("%7d", Math.abs(new Random().nextInt() % 10000000));
        if (!sendEmail(mailAddress, "api", "验证码", "你的验证码为: " + token)) return new Status(3, "Mail system error.");
        tokenList.put(mailAddress, token);
        history.put(mailAddress, new Date().getTime());
        return new Status(0, "Sent email to " + mailAddress + ".");
    }

    @GetMapping("/mail/check-token")
    public Status checkToken(@RequestParam(value = "address", defaultValue = "") String address, @RequestParam(value = "token", defaultValue = "-1") String token) {
        if (address.equals("")) return new Status(2, "Mail address missing.");
        if (token.equals("-1")) return new Status(3, "Token missing.");
        String stdToken = tokenList.get(address);
        if (stdToken == null) return new Status(4, "No token sent for this mail.");
        if (!stdToken.equals(token)) return new Status(1, "Token doesnt match the recorded one.");
        return new Status(0, "Token matched.");
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
