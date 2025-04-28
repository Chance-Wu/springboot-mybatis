//package com.chance.component;
//
//import com.chance.service.EmailMonitoringService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * @author chance
// * @date 2025/4/26 09:27
// * @since 1.0
// */
//@Component
//public class EmailScheduler {
//
//    @Autowired
//    private EmailMonitoringService emailMonitoringService;
//
//    // 每隔6s执行一次
//    @Scheduled(fixedRate = 6000)
//    public void monitorEmails() {
//        emailMonitoringService.checkEmailsForAllAccounts();
//    }
//}
