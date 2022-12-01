package com.haroot.home_page.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haroot.home_page.properties.YoutubeProperties;

import lombok.RequiredArgsConstructor;

/**
 * youtubeコントローラー
 * 
 * @author sekiharuhito
 *
 */
@Controller
@RequestMapping("/youtube")
@RequiredArgsConstructor
public class YoutubeController {
    final JdbcTemplate jdbcT;
    final YoutubeProperties youtubeProperties;

    /**
     * Youtube一覧表示
     * 
     * @param mav MAV
     * @return
     */
    @GetMapping
    public ModelAndView youtube(ModelAndView mav) {
        // youtubeAPIから動画一覧を取得
        List<Map<String, Object>> youtubeList = new ArrayList<>();
        int maxResults = 50;
        try {
            URL url = new URL("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet" + "&playlistId="
                    + youtubeProperties.getPlaylistId() + "&maxResults=" + String.valueOf(maxResults) + "&key="
                    + youtubeProperties.getKey());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(url);
            int itemLen = json.get("items").size();
            // 各動画情報取得
            for (int i = 0; i < itemLen; i++) {
                JsonNode snippet = json.get("items").get(i).get("snippet");
                String title = snippet.get("title").toString().replaceAll("\"", "");
                // タイトルにショーツがついてたら飛ばす
                if (title.indexOf("#Shorts") != -1) {
                    continue;
                }
                Map<String, Object> youtubeMap = new HashMap<>();
                String id = String.valueOf(itemLen - i - 1);
                String videoId = snippet.get("resourceId").get("videoId").toString().replaceAll("\"", "");
                youtubeMap.put("id", id);
                youtubeMap.put("title", title);
                youtubeMap.put("url", "https://www.youtube.com/watch?v=" + videoId);
                youtubeMap.put("img", "http://img.youtube.com/vi/" + videoId + "/mqdefault.jpg");
                youtubeList.add(youtubeMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.addObject("youtubeList", youtubeList);
        mav.setViewName("contents/youtube");
        return mav;
    }
}
