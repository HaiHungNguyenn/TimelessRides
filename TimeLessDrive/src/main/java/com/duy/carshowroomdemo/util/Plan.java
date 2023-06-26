package com.duy.carshowroomdemo.util;

public class Plan {
   public static final String PLAN_1 = "plan 1";
   public static final String PLAN_2 = "plan 2";
   public static final String PLAN_3 = "plan 3";
   public static final String PLAN_4 = "plan 4";
   public static final String PLAN_5 = "plan 5";
   public static final String PLAN_6 = "plan 6";
   public static long getPrice(String plan){
       switch (plan){
           case PLAN_1 -> {
               return 1000000;
           }
           case PLAN_2 -> {
               return 5000000;
           }
           case PLAN_3 -> {
               return 10000000;
           }
       }
       return 0;
   }
   public static long getDuration(String plan){
       switch (plan){
           case PLAN_1 -> {
               return 30;
           }
           case PLAN_2 -> {
               return 180;
           }
           case PLAN_3 -> {
               return 365;
           }
       }
       return 0;
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
           case PLAN_4 -> {
               return 4;
           }
           case PLAN_5 -> {
               return 5;
           }
           case PLAN_6 -> {
               return 6;
           }
       }
       return 0;
   }
}
