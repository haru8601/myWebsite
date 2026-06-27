package com.haroot.home_page.entity;

import lombok.Data;

@Data
public class RecaptchaVerifyEntity {
  private boolean success;
  private float score;
  private String action;
  private String challenge_ts;
  private String hostname;
  private String[] errorCodes;
}
