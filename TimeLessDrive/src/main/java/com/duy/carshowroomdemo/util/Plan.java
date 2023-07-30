package com.duy.carshowroomdemo.util;

public class Plan {
   public static final String PLAN_1 = "plan 1";
   public static final String PLAN_2 = "plan 2";
   public static final String PLAN_3 = "plan 3";
   public static long getPrice(String plan){
       switch (plan){
           case PLAN_1 -> {
               return 299;
           }
           case PLAN_2 -> {
               return 599;
           }
           case PLAN_3 -> {
               return 799;
           }
       }
       return 0;
   }
   public static long getDuration(String plan){
//       switch (plan){
//           case PLAN_1 -> {
//               return 30;
//           }
//           case PLAN_2 -> {
//               return 180;
//           }
//           case PLAN_3 -> {
//               return 365;
//           }
//       }
       return 30;
   }
   public static int getPriority(String plan){



       switch (plan){
           case PLAN_1 -> {
               return 1;
           }
           case PLAN_2 -> {
               return 2;
           }
           case PLAN_3 -> {
               return 3;
           }
       }
       return 0;
   }
}
