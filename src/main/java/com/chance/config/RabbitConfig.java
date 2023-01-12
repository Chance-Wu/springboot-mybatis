//package com.chance.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
///**
// * @description: RabbitConfig
// * @author: chance
// * @date: 2022/10/9 22:19
// * @since: 1.0
// */
//@Configuration
//public class RabbitConfig {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String vhost;
//
//    /**
//     * Exchange 消息交换机：指定消息按什么规则，路由到那些个队列
//     */
//    public static final String EXCHANGE_A = "exchange_a";
//    public static final String EXCHANGE_B = "exchange_b";
//    public static final String EXCHANGE_C = "exchange_c";
//
//    /**
//     * Routing Key 路由关键字：exchange根据这个关键字进行消息投递
//     */
//    public static final String ROUTING_KEY_A = "ROUTING_KEY_A";
//    public static final String ROUTING_KEY_B = "ROUTING_KEY_B";
//    public static final String ROUTING_KEY_C = "ROUTING_KEY_C";
//
//    /**
//     * 交换机
//     */
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(EXCHANGE_A);
//    }
//
//    /**
//     * 队列A
//     */
//    @Bean
//    public Queue queueA() {
//        return new Queue("QUEUE_A", true);
//    }
//
//    /**
//     * 队列B
//     */
//    @Bean
//    public Queue queueB() {
//        return new Queue("QUEUE_B", true);
//    }
//
//    /**
//     * 一个交换机可以绑定多个消息队列，即消息通过一个交换机，可以分发到不同的队列当中去
//     */
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTING_KEY_A);
//    }
//
//    @Bean
//    public Binding bindingB() {
//        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.ROUTING_KEY_B);
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(vhost);
//        return connectionFactory;
//    }
//
//    /**
//     * 设置为 prototype 类型（如果是singleton，回调类就只能有一个）
//     */
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory());
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        //加载处理消息A的队列
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        //设置接收多个队列里面的消息，这里设置接收队列A
//        container.setQueues(queueA());
//        container.setExposeListenerChannel(true);
//        //设置最大的并发的消费者数量
//        container.setMaxConcurrentConsumers(10);
//        //最小的并发消费者的数量
//        container.setConcurrentConsumers(1);
//        //设置确认模式手工确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//            // 通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//            // 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它
//            channel.basicQos(1);
//            byte[] body = message.getBody();
//            logger.info(">>>>>>>>接收处理队列A当中的消息:{}", new String(body));
//            // 为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//            // 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        });
//        return container;
//    }
//}
