package com.chance.designpartterms;

import com.chance.designpatterns.factory.abstrct.Button;
import com.chance.designpatterns.factory.abstrct.CheckBox;
import com.chance.designpatterns.factory.abstrct.WinterSkinFactory;
import com.chance.designpatterns.factory.method.EmailFactory;
import com.chance.designpatterns.factory.method.NotificationFactory;
import com.chance.designpatterns.factory.simple.NotificationSender;
import com.chance.designpatterns.factory.simple.SenderFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/2 16:51
 * @since 1.0
 */
@Slf4j
public class AbstractFactoryTest {

    @Test
    public void simpleFactoryTest() {
        NotificationSender sender = SenderFactory.createSender("EMAIL");
        sender.send("<EMAIL>", "注册成功");
    }

    @Test
    public void methodFactoryTest() {
        NotificationFactory emailFactory = new EmailFactory();
        NotificationSender emailSender = emailFactory.createSender();
        emailSender.send("<EMAIL>", "注册成功");
    }

    @Test
    public void abstractFactoryTest() {
        // 只需要切换工厂实例，即可切换整个产品族的主题
        WinterSkinFactory winterSkinFactory = new WinterSkinFactory();

        Button button = winterSkinFactory.createButton();
        CheckBox checkBox = winterSkinFactory.createCheckBox();

        // 确保 button 和 checkbox 都是 Winter 风格的
        button.display();
        checkBox.select();
    }
}
