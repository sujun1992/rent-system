// Copyright 2021 Mobvoi Inc. All Rights Reserved.

package com.rent.system.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author jun.su <jun.su@mobvoi.com>
 * @Date 2021/8/11 15:31
 */
public class TimeUtil {

  private static final DateTimeFormatter dateFormatter = DateTimeFormatter
      .ofPattern("yyyy/MM/dd HH:mm:ss");

  public static String formatterTime(long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
        .format(dateFormatter);
  }

}
