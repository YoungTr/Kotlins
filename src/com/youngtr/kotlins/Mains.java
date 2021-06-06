package com.youngtr.kotlins;

import com.youngtr.kotlins.c2.InFunctions;
import com.youngtr.kotlins.c3.StringUtilKt;
import com.youngtr.kotlins.c8.UnitKt;
import kotlin.jvm.functions.Function2;

public class Mains {
  public static void main(String[] args) {
    boolean isLetter = InFunctions.isLetter('c');
    System.out.println(isLetter);

    char lastChar = StringUtilKt.lastChar("Kotlin");
    System.out.println(lastChar);

    StringUtilKt.getLastChar("Kotlin?");

    UnitKt.twoAndThree((a, b) -> a + b);

    UnitKt.twoAndThree(
        new Function2<Integer, Integer, Integer>() {
          @Override
          public Integer invoke(Integer a, Integer b) {
            return a * b;
          }
        });
  }
}
