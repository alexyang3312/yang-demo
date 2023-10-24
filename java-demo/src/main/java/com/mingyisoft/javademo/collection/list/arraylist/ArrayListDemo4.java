package com.mingyisoft.javademo.collection.list.arraylist;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ArrayListDemo4 {
    public static void main(String[] args) throws Exception{
        Map<String,String> m = new TreeMap<>();
        m.put("08:00","a");
        m.put("07:00","b");

        m.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });

        // 5次内存分组，1.[类型]，2.[线路]，3.[站点]，4.[班次]，5.[时段]
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        Person p5 = new Person();
        Person p6 = new Person();
        Person p7 = new Person();
        Person p8 = new Person();

        p1.setStudentPhone("111");
        p1.setBusLineType(0);
        p1.setBusLineName("线路1");
        p1.setStationName("第a站");
        p1.setShiftName("班次1");
        p1.setBusPeriod("07:00");

        p2.setStudentPhone("222");
        p2.setBusLineType(0);
        p2.setBusLineName("线路1");
        p2.setStationName("第a站");
        p2.setShiftName("班次1");
        p2.setBusPeriod("08:00");

        p3.setStudentPhone("333");
        p3.setBusLineType(0);
        p3.setBusLineName("线路1");
        p3.setStationName("第a站");
        p3.setShiftName("班次2");
        p3.setBusPeriod("07:00");

        p4.setStudentPhone("444");
        p4.setBusLineType(0);
        p4.setBusLineName("线路2");
        p4.setStationName("第a站");
        p4.setShiftName("班次1");
        p4.setBusPeriod("07:00");

        p5.setStudentPhone("555");
        p5.setBusLineType(0);
        p5.setBusLineName("线路2");
        p5.setStationName("第b站");
        p5.setShiftName("班次2");
        p5.setBusPeriod("07:00");

        p6.setStudentPhone("666");
        p6.setBusLineType(0);
        p6.setBusLineName("线路2");
        p6.setStationName("第b站");
        p6.setShiftName("班次2");
        p6.setBusPeriod("07:00");

        p7.setStudentPhone("777");
        p7.setBusLineType(1);
        p7.setBusLineName("线路2");
        p7.setStationName("第a站");
        p7.setShiftName("班次1");
        p7.setBusPeriod("07:00");

        p8.setStudentPhone("888");
        p8.setBusLineType(1);
        p8.setBusLineName("线路2");
        p8.setStationName("第a站");
        p8.setShiftName("班次1");
        p8.setBusPeriod("07:00");

        List<Person> demoList = new ArrayList<>();
        demoList.add(p1);
        demoList.add(p2);
        demoList.add(p3);
        demoList.add(p4);
        demoList.add(p5);
        demoList.add(p6);
        demoList.add(p7);
        demoList.add(p8);


        Map<Integer, Map<String, List<Map<String, String>>>> groupedDataXXX = demoList.stream()
                .collect(Collectors.groupingBy(
                        Person::getBusLineType,
                        Collectors.groupingBy(
                                xxx -> xxx.getBusLineName() + "-" + xxx.getShiftName(),
                                Collectors.mapping(person -> {
                                    Map<String, String> personMap = new HashMap<>();
                                    personMap.put("phone", person.getStudentPhone());
                                    return personMap;
                                }, Collectors.toList())
                        )
                ));



        Map<Integer, Map<String, Map<String, List<Person>>>> groupedData = demoList.stream()
                .collect(Collectors.groupingBy(
                        Person::getBusLineType,
                        Collectors.groupingBy(
                                Person::getBusLineName,
                                Collectors.groupingBy(
                                        Person::getShiftName
                                )
                        )
                ));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("arr1=", groupedData.entrySet().stream().map(entry -> {
            Map<String, Object> busLineTypeMap = new LinkedHashMap<>();
            busLineTypeMap.put("busLineType", entry.getKey());
            busLineTypeMap.put("arr2=", entry.getValue().entrySet().stream().map(busLineNameEntry -> {
                Map<String, Object> busLineNameMap = new LinkedHashMap<>();
                busLineNameMap.put("busLineName", busLineNameEntry.getKey());
                busLineNameMap.put("arr3=", busLineNameEntry.getValue().entrySet().stream().map(shiftNameEntry -> {
                    Map<String, Object> shiftNameMap = new LinkedHashMap<>();
                    shiftNameMap.put("shiftName", shiftNameEntry.getKey());
                    shiftNameMap.put("arr4=", shiftNameEntry.getValue().stream().map(person -> {
                        Map<String, String> personMap = new LinkedHashMap<>();
                        personMap.put("phone", person.getStudentPhone());
                        return personMap;
                    }).collect(Collectors.toList()));
                    return shiftNameMap;
                }).collect(Collectors.toList()));
                return busLineNameMap;
            }).collect(Collectors.toList()));
            return busLineTypeMap;
        }).collect(Collectors.toList()));


        System.out.println(JSONArray.toJSON(result));
    }

    static class Person{
        public Person(){

        }
        public Person(String studentName, String studentPhone){
            this.studentName = studentName;
            this.studentPhone = studentPhone;
        }

        // 班次
        String shiftName;
        // 学生名称
        String studentName = "张三";
        // 学生手机号
        String studentPhone;
        // 线路类型，0：去驾校，1：返程，2：去考场
        Integer busLineType;
        // 线路名称
        String busLineName;
        // 站点名称
        String stationName;
        // 时段
        String busPeriod;

        public String getStudentPhone() {
            return studentPhone;
        }

        public void setStudentPhone(String studentPhone) {
            this.studentPhone = studentPhone;
        }

        public String getBusPeriod() {
            return busPeriod;
        }

        public void setBusPeriod(String busPeriod) {
            this.busPeriod = busPeriod;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getBusLineName() {
            return busLineName;
        }

        public void setBusLineName(String busLineName) {
            this.busLineName = busLineName;
        }

        public String getShiftName() {
            return shiftName;
        }

        public void setShiftName(String shiftName) {
            this.shiftName = shiftName;
        }

        public Integer getBusLineType() {
            return busLineType;
        }

        public void setBusLineType(Integer busLineType) {
            this.busLineType = busLineType;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }
    }
}
