package cn.itcast.bos.mq;

import java.io.UnsupportedEncodingException;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import cn.itcast.bos.util.SmsUtils;

/**
 * 用户登录短信平台
 *
 * @author 长孙建坤  18092853734
 * @version 1.0 ，2017年7月19日  下午8:40:02
 */
@Component
public class LoginQueueConsumer implements MessageListener{

        @Override
        public void onMessage(Message message) {
                MapMessage msg = (MapMessage) message;
                try {
                        String telephone = msg.getString("telephone");
                        String checkCode = msg.getString("randomCode");
                        String content = "尊敬的用户,您本次请求的验证码是:"+ checkCode + ",请在30分钟内完成校验. 请妥善保管!";
                        String result = SmsUtils.sendSmsByHTTP(telephone, content);
                        if (result.startsWith("000")) {
                                System.out.println("验证码发送成功!");
                        }
                } catch (JMSException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                }
               /* try {
                        String telephone = msg.getString("telephone");
                        String checkCode = msg.getString("checkCode");
                        String content = "尊敬的用户,您本次请求的验证码是:"+ checkCode + ",请在30分钟内完成校验. 请妥善保管!";
                        String result = "000/xxx";
                        if (result.startsWith("000")) {
                                System.out.println(content + ",电话号码是:" +telephone);
                        }
                } catch (JMSException e) {
                        e.printStackTrace();
                }*/
        }

}