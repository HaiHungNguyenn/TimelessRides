package com.duy.carshowroomdemo.util;

import lombok.Data;

public class Plan {
   public static final String plan1 = "Plan 1";
   public static final String plan2 = "Plan 2";
   public static final String plan3 = "Plan 3";
   public static long getPrice(String plan){
       switch (plan){
           case plan1 -> {
               return 1000000;
           }
           case plan2 -> {
               return 5000000;
           }
           case plan3 -> {
               return 10000000;
           }
       }
       return 0;
   }
   public static long getDuration(String plan){
       switch (plan){
           case plan1 -> {
               return 30;
           }
           case plan2 -> {
               return 180;
           }
           case plan3 -> {
               return 365;
           }
       }
       return 0;
   }
}
