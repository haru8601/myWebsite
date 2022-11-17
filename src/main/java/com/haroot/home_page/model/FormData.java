package com.haroot.home_page.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 問い合わせデータ
 * 
 * @author sekiharuhito
 *
 */
@Data
public class FormData {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String content;

    @AssertTrue(message = "プライバシーポリシーに同意する場合はチェックしてください")
    private boolean checkbox;
}
