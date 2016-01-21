package com.test;

import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;

import retrofit.Converter;

/**
 * retrofit转换成字符串支持
 * Created by Administrator on 2015/11/19.
 * ToDo:http://www.ithao123.cn/content-10622612.html
 */
public final class StringConverterFactory extends Converter.Factory {
    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new ConfigurationServiceConverter();
    }

    final class ConfigurationServiceConverter implements Converter<ResponseBody, String> {

        @Override
        public String convert(ResponseBody value) throws IOException {
            BufferedReader r = new BufferedReader(new InputStreamReader(value.byteStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        }
    }
}
