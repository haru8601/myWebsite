package com.haroot.home_page.logic;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * ipアドレスクラス
 * @author sekiharuhito
 *
 */
public class IpLogic extends Thread {
    
    private HttpServletRequest request;
    private JdbcTemplate jdbcT;

    public IpLogic(
            HttpServletRequest request,
            JdbcTemplate jdbcT) {
        this.request = request;
        this.jdbcT = jdbcT;
    }
    
    /**
     * IPアドレス取得
     * @param request リクエスト
     * @return IPアドレス
     */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public void run() {
		// クライアントIPアドレス記録
		String ip = getIp(request);
		// referer取得
		String referer = request.getHeader("REFERER");
		// urlからの直移動やharoot.net内の移動やなら無視
		if (referer == null || referer.indexOf("haroot.net") != -1) {
			return;
		}
		String dateStr = DateLogic.getJSTDateStr();

		List<Map<String, Object>> ipInfoList = jdbcT.queryForList("select * from ipList where ip=?", ip);
		// 知ってるipなら
		if (!ipInfoList.isEmpty()) {
			Map<String, Object> ipInfo = ipInfoList.get(0);
			int count = Integer.parseInt(ipInfo.get("count").toString());
			String updateStr = "update ipList set count=?, last_date=?, referer=? where ip=?";
			jdbcT.update(updateStr, count + 1, dateStr, referer, ip);
			// 知らないipなら
		} else {
			String insertStr = "insert into ipList(ip,count,first_date,last_date,referer) values(?,?,?,?,?)";
			jdbcT.update(insertStr, ip, 1, dateStr, dateStr, referer);
		}
		return;
	}
}
