package com.example.tests;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Parameterized.class)
@SuiteClasses({SeleniumJunitTest.class})
public class ParameterizedSuite {
 
 
  @Parameterized.Parameters
  public static Collection<String> primeNumbers() {
     return Arrays.asList(new String[] {
         "firefox", "chrome"
     });
  }

} 