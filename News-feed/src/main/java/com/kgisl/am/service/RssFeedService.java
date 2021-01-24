package com.kgisl.am.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgisl.am.models.RssFeed;
import com.kgisl.am.repository.RssFeedRepository;

@Service
public class RssFeedService {
	
	@Autowired
	private RssFeedRepository feedRepository;
	
	public RssFeed saveRssFeed(RssFeed rssFeed) {
		return feedRepository.save(rssFeed);
	}

}
