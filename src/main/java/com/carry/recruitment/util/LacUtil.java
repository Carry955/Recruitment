package com.carry.recruitment.util;

import com.baidu.nlp.LAC;
import com.carry.recruitment.entity.Lac;

import java.util.ArrayList;

public class LacUtil {

    static {
        String dir_path = System.getProperty("user.dir")+"\\src\\main\\resources\\dll\\";
        System.load(dir_path+"libiomp5md.dll");
        System.load(dir_path+"mklml.dll");
        System.load(dir_path+"mkldnn.dll");
        System.load(dir_path+"lacjni.dll");
    }
    public static Lac run(String str){
        String dir_path = System.getProperty("user.dir");
        String model_path = dir_path+"\\src\\main\\java\\com\\carry\\recruitment\\util\\lac_model";
        LAC lac = new LAC(model_path);
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        try{
            lac.run(str, words, tags);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Lac(words, tags);
    }
}
