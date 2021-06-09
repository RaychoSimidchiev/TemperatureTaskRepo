package com.company;

import javax.swing.plaf.basic.BasicBorders;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {


        Map<Date, Temperature> map = new LinkedHashMap<>();
        map = initializeDateTemperatureMap(map);

        calculateAverageForWeek(map);

        Map<Date,Temperature> sortedMap = sortMap(map);

        calculateTemperatureChanges(sortedMap);
    }


    public static Map<Date, Temperature> initializeDateTemperatureMap(Map<Date, Temperature> map) {
        Scanner sc = new Scanner(System.in);

        String line;

        while(!(line = sc.nextLine()).isEmpty()){


            String[] input = line.trim().split("\"|:|,| ");

            String[] d = input[1].split("\\.");

            Date date = new Date(Integer.parseInt(d[0]),Integer.parseInt(d[1]),Integer.parseInt(d[2]));

            Temperature temperature = new Temperature(Double.parseDouble(input[4]));

            map.put(date, temperature);
        }
        return map;
    }

    public static void calculateAverageForWeek(Map<Date, Temperature> map) {
        double sum1 = 0;
        double sum2 = 0;

        for (Date d: map.keySet()){
            if(d.getMonth() == 1){
                if (d.getDay() <= 7){
                    sum1 += map.get(d).getDegree();
                }else {
                    sum2 += map.get(d).getDegree();
                }
            }
        }

        System.out.println("през първата седмица на януари средната температура ще бъде :" + String.format("%.2f", sum1/7) + "\n");
        System.out.println("през втората седмица на януари средната температура ще бъде:" + String.format("%.2f", sum2/7) + "\n");
    }

    public static Map<Date, Temperature> sortMap(Map<Date, Temperature> map){

        Map<Date, Temperature> sortedMap = new LinkedHashMap<>();

        List<Date> dates = new ArrayList<>(map.keySet());

        List<LocalDate> dateMin =  new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            dateMin.add(LocalDate.of(dates.get(i).getYear(), dates.get(i).getMonth(), dates.get(i).getDay()));
        }

        Collections.sort(dateMin);

        for (LocalDate date: dateMin) {
            Date d = new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());

            sortedMap.put(d, map.get(d));
        }
        return  sortedMap;
    }
    static void calculateTemperatureChanges(Map<Date, Temperature> map){

        double previousDay = 0;

        for (Date d: map.keySet()) {
            java.util.Date date = new java.util.Date(d.getDay(), d.getMonth(), d.getYear());

            String dayOfWeek = new SimpleDateFormat("EEEE").format(date);

            System.out.println(dayOfWeek + " -> " + d.getDay()  +"-" + d.getMonth() + "-" + d.getYear() +
                    ": " + String.format("%.2f",map.get(d).getDegree() - previousDay));
            previousDay = map.get(d).getDegree();
        }
    }
}


