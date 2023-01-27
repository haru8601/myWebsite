package com.haroot.home_page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haroot.home_page.dto.PokeDto;
import com.haroot.home_page.repository.PokeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PokeService {
	private final PokeRepository pokeRepository;

	public List<PokeDto> getAll() {
		return PokeDto.ofList(pokeRepository.findAll());
	}
}
