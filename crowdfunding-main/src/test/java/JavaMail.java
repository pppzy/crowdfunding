import com.itpzy.crowdfunding.util.DESUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class JavaMail  {
    public static void main(String[] args)throws Exception {
        ApplicationContext application = new ClassPathXmlApplicationContext("spring/spring-mail.xml");

        // 邮件发送器，由Spring框架提供的
        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl)application.getBean("sendMail");

        javaMailSender.setDefaultEncoding("UTF-8");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("pzy@crowdfunding.com");
        helper.setTo("test@crowdfunding.com");
        helper.setSubject("邮件测试");
        String param = "love123";
        param = DESUtil.encrypt(param, "abcdefghijklmnopquvwxyz");
        StringBuilder content = new StringBuilder();
        content.append("<a href='http://www.crowdfunding.com/user/test.do?code="+param+"' >激活链接</a>");
        helper.setText(content.toString(),true);
        javaMailSender.send(mimeMessage);
    }


}
