package com.perfect.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.entity.StructureReportEntity;
import com.perfect.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/17.
 */
public class RegionalCodeUtils {

    public static  Map<Long,String> regionalCode(List<Long> id){
        Jedis jc = JRedisUtils.get();
        String data = jc.get("RegionalCodeRedis");
        Gson gson = new Gson();
        Map<String,String> d = gson.fromJson(data, new TypeToken<Map<Long,String>>() {}.getType());
        Map<Long,String> map = new HashMap<>();
        for (Long aLong : id){
            map.put(aLong, d.get(aLong));
        }
        return map;
    }
    public static  Map<Long,String> regionalCodeName(List<String> name){
        Jedis jc = JRedisUtils.get();
        String data = jc.get("RegionalCodeRedis");
        Gson gson = new Gson();
        Map<Long,String> d = gson.fromJson(data, new TypeToken<Map<Long,String>>() {}.getType());
        Map<Long,String> map = new HashMap<>();
        for (String aString : name){
            for (Map.Entry<Long, String> voEntity : d.entrySet()){
                if(aString.equals(voEntity.getValue())){
                    map.put(voEntity.getKey(),aString);
                }
            }
        }
        return map;
    }
}
