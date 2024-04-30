//package com.chance.util;
//
//import org.apache.hc.client5.http.impl.classic.HttpClients;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * @author: chance
// * @date: 2024/4/28 09:38
// * @since: 1.0
// */
//public class HttpClient {
//
//    public static void main(String[] args) throws IOException {
//        HttpClients.createDefault()
//        HttpGet httpget = new HttpGet("http://localhost/");
//        CloseableHttpResponse response = httpclient.execute(httpget);
//        try {
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                try {
//                    // do something useful
//                } finally {
//                    instream.close();
//                }
//            }
//        } finally {
//            response.close();
//        }
//// httpclient.close();
//
//    }
//}
