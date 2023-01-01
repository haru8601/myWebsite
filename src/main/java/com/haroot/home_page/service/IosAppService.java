package com.haroot.home_page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.IosAppDto;
import com.haroot.home_page.repository.IosAppRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IosAppService {
	private final IosAppRepository iosAppRepository;

	/**
	 * 全件取得
	 * 
	 * @return iosAppのリスト
	 */
	public List<IosAppDto> getAll() {
		return IosAppDto.listOf(iosAppRepository.findAll());
	}
}
