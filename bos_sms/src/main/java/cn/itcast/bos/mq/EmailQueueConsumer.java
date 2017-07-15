package cn.itcast.bos.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.stereotype.Component;
import cn.itcast.bos.util.MailUtils;

/**
 * 通过消息队列发送邮件
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月14日  下午8:45:34
 */
@Component
public class EmailQueueConsumer implements MessageListener{

        @Override
        public void onMessage(Message message) {
                MapMessage msg = (MapMessage) message;
                try {
                        String telephone = msg.getString("telephone");
                        String activeCode = msg.getString("code");
                        String email = msg.getString("email");
                        String subject = "宅急送激活邮件";
                        String content = "尊敬的用户，请点击下面的连接进行账户激活！"+ MailUtils.activeUrl +"?activeCode="+ activeCode + "&telephone="+ telephone;
                        MailUtils.sendMail(subject, content, email);
                } catch (JMSException e) {
                        e.printStackTrace();
                }
        }
}
