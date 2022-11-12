package com.haroot.home_page.model;

import lombok.Data;

/**
 * Topicデータ
 * @author sekiharuhito
 *
 */
@Data
public class TopicData {
	private int id;
	private String title;
	private String url;
	private String summary;
	private String img;
}