package com.chance.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p> CsvUtils </p>
 *
 * @author chance
 * @date 2023/5/24 15:27
 * @since 1.0
 */
public class CsvUtils {

    /**
     * 导出生成csv格式的文件
     *
     * @param titles    csv格式头文
     * @param propertys 需要导出的数据实体的属性，注意与title一一对应
     * @param list      需要导出的对象集合
     * @return
     * @throws Exception
     */
    public <T> void exportCsv(String[] titles, String[] propertys, List<T> list) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //构建输出流，同时指定编码
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream, Charset.forName("UTF-8")));
        try {
            //csv文件是逗号分隔，除第一个外，每次写入一个单元格数据后需要输入逗号
            for (String title : titles) {
                writer.write(title);
                writer.write(",");
            }
            //写完文件头后换行
            writer.write("\r\n");
            //写内容
            for (Object obj : list) {
                //利用反射获取所有字段
                Field[] fields = obj.getClass().getDeclaredFields();
                for (String property : propertys) {
                    for (Field field : fields) {
                        //设置字段可见性
                        field.setAccessible(true);
                        if (property.equals(field.getName())) {
                            writer.write(String.valueOf(field.get(obj)));
                            writer.write(",");
//                        System.out.println(field.get(obj));
                            continue;
                        }
                    }
                }
                //写完一行换行
                writer.write("\r\n");
            }
            System.out.println(byteArrayOutputStream.toString());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
//			throw new Exception("生成【"+filePath+"】失败");
        } finally {
            writer.close();
        }

    }
}
