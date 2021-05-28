package com.youngtr.kotlins;

import com.youngtr.kotlins.c2.InFunctions;
import com.youngtr.kotlins.c3.StringUtilKt;

public class Mains {
  public static void main(String[] args) {
    boolean isLetter = InFunctions.isLetter('c');
    System.out.println(isLetter);

    char lastChar = StringUtilKt.lastChar("Kotlin");
    System.out.println(lastChar);

    StringUtilKt.getLastChar("Kotlin?");
  }
}
