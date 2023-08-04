package com.waani.server.amqp;

import com.waani.server.config.properties.AmqpProperties;
import com.waani.server.listener.IJmsConnectionListener;
import com.waani.server.listener.IMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.apache.qpid.jms.JmsConnection;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;


@Log4j2
@RequiredArgsConstructor
public class AmqpConsumer {

    private final AmqpProperties amqpProperties ;

    private static final String signMethod = "hmacsha1" ;


    private static final String USERNAME_FORMAT = "%s-%d|authMode=aksign"
            + ",signMethod=%s"
            + ",timestamp=%d"
            + ",authId=%s"
            + ",iotInstanceId=%s"
            + ",consumerGroupId=%s"
            + "|";

    private static final String CONNECTION_URL_FORMAT = "failover:(amqps://%s:5671?amqp.idleTimeout=80000)?failover.reconnectDelay=30";

    private static final AtomicInteger consumer = new AtomicInteger(0) ;


    public void startConsumer(IMessageListener iMessageListener) throws Exception {
        long timeStamp = System.currentTimeMillis();

        String userName = String.format(USERNAME_FORMAT, amqpProperties.getClientId(),
                consumer.getAndIncrement(), signMethod, timeStamp, amqpProperties.getAccessKey(),
                amqpProperties.getIotInstanceId(), amqpProperties.getConsumerGroupId()) ;

        String password = doSign(timeStamp);

        Context context = new InitialContext(getEnvironment());
        ConnectionFactory cf = (ConnectionFactory)context.lookup("SBCF");
        Destination queue = (Destination)context.lookup("QUEUE");
        // 创建连接。
        Connection connection = cf.createConnection(userName, password);

        ((JmsConnection)connection).addConnectionListener(new IJmsConnectionListener());
        // 创建会话。
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        connection.start();
        // 创建Receiver连接。
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(iMessageListener);
    }




    private String doSign(long timeStamp) throws Exception {
        String signContent = "authId=" + amqpProperties.getAccessKey() + "&timestamp=" + timeStamp;
        SecretKeySpec signingKey = new SecretKeySpec(amqpProperties.getAccessSecret().getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(signContent.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }


    private Hashtable<String, String> getEnvironment(){
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("connectionfactory.SBCF", String.format(CONNECTION_URL_FORMAT, amqpProperties.getHost()));
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        return hashtable ;
    }

}
