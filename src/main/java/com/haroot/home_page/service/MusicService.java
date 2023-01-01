package com.haroot.home_page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.MusicDto;
import com.haroot.home_page.repository.MusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicService {
	private final MusicRepository musicRepository;

	public List<MusicDto> getAllMusic() {
		return MusicDto.ofList(musicRepository.findAll());
	}
}
