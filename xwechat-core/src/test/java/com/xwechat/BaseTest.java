/**
 * @author yuanwq, date: 2017年2月22日
 */
package com.xwechat;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;

/**
 * @author yuanwq
 */
public class BaseTest {

  @BeforeClass
  public static void initTest() {
    setupLogger();
  }

  protected static void setupLogger() {
    Properties props = new Properties();
    props.setProperty("log4j.rootLogger", "INFO, stdout");
    props.setProperty("log4j.logger.com.lookcommon", "DEBUG");
    props.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
    props.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
    props.setProperty("log4j.appender.stdout.layout.ConversionPattern",
        "%d{ABSOLUTE} %5p %c{1}:%L - %m%n");

    PropertyConfigurator.configure(props);
  }
}
