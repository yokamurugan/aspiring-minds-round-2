package com.kgisl.am;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kgisl.am.models.RssFeed;
import com.kgisl.am.repository.RssFeedRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
@Component
class RSSParser {
	@Autowired 
	private RssFeedRepository feedRepository;
	
    public ArrayList<Template> readRssFeed() throws IOException, FeedException {
    	 List<RssFeed> feeds=feedRepository.findAll();
    	 ArrayList<Template> templates=new ArrayList<Template>();
    	 for(RssFeed rss:feeds) {
        URL feedSource = new URL(rss.getUrl());
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        
        for(Object o: feed.getEntries()) {
        	
            SyndEntry entry = (SyndEntry) o;
            Template template=new Template();
            template.setTitle(entry.getTitle());
            template.setDescription(entry.getDescription().getValue());
            template.setLink(entry.getLink());
            template.setPublishedDate(entry.getPublishedDate());
            template.setCategory(rss.getCategory());
            templates.add(template);
        }
    	 }
        return templates;
    }
}