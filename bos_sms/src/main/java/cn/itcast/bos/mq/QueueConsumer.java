package cn.itcast.bos.mq;

import java.io.UnsupportedEncodingException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.stereotype.Component;
import cn.itcast.bos.util.SmsUtils;

/**
 * 队列消息消费者,实现短信的发送
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月12日  下午10:34:25
 */
@Component
public class QueueConsumer implements MessageListener {

        @Override
        public void onMessage(Message message) {
                MapMessage msg = (MapMessage) message;
/*                try {
                        String telephone = msg.getString("telephone");
                        String checkCode = msg.getString("checkCode");
                        String content = "尊敬的用户,您本次请求的验证码是:"+ checkCode + ",请在30分钟内完成校验. 请妥善保管!";
                        String result = SmsUtils.sendSmsByHTTP(telephone, content);
                        if (result.startsWith("000")) {
                                System.out.println("验证码发送成功!");
                        }
                } catch (JMSException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                }*/
                try {
                        String telephone = msg.getString("telephone");
                        String checkCode = msg.getString("checkCode");
                        String content = "尊敬的用户,您本次请求的验证码是:"+ checkCode + ",请在30分钟内完成校验. 请妥善保管!";
                        String result = "000/xxx";
                        if (result.startsWith("000")) {
                                System.out.println(content + ",电话号码是:" +telephone);
                        }
                } catch (JMSException e) {
                        e.printStackTrace();
                }
        }

}
