package com.happylifeplat.security.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.happylifeplat.security.common.StringUnicodeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/18 14:08</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class JsonUtils {

    /**
     * object mapper
     */
    private static final ObjectMapper OBJECT_MAPPER;

    /**
     * init object mapper
     */
    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);//忽略 默认值
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateUtils.DateFormatType.DATE_FORMAT_STR.getValue()));//设置日期格式化
        //使Jackson JSON支持Unicode编码非ASCII字符
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new StringUnicodeSerializer());
        OBJECT_MAPPER.registerModule(module);
        //设置null值不参与序列化(字段不被显示)
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 将对象转换成json字符串
     *
     * @param object 序列化对象
     * @return json串
     * @throws IOException io exception
     */
    public static String object2Json(Object object) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json  json串
     * @param clazz 序列化对象
     * @param <T>   序列化对象类型
     * @return 序列化对象
     * @throws IOException io exception
     */
    public static <T> T json2Object(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    /**
     * 将json字段串转换为List
     *
     * @param json  json串
     * @param clazz 序列化对象
     * @param <T>   序列化对象类型
     * @return 序列化集合
     * @throws IOException io exception
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return OBJECT_MAPPER.readValue(json, type);
    }

    /**
     * 将json字段转换成Map
     *
     * @param json       json串
     * @param keyClass   map key 类型
     * @param valueClass map value 类型
     * @param <K>        key 序列化对象类型
     * @param <V>        value 序列化对象类型
     * @return 序列化Map
     * @throws IOException io exception
     */
    public static <K, V> Map<K, V> json2Map(String json, Class<K> keyClass, Class<V> valueClass) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return OBJECT_MAPPER.readValue(json, type);
    }
}
