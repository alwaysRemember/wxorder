package com.thj.wxorder.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/7/26 15:20
 **/

public class Date2LongSerializer extends JsonSerializer<Date> {

    // 毫秒转为秒的方法重写
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(date.getTime()/1000);
    }
}
