package com.kgisl.am;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kgisl.am.tenant.TenantService;

@Controller
public class RSSController {
	
	@Autowired
	private RSSParser rssParser;
	@Autowired
	private TenantService tenantService;
    @GetMapping("/news")
    public String rssForm(Model model,@RequestParam String tenantCode) throws Exception {
        tenantService.loadSettingsTenant(tenantCode);
        //String readNYTFeed = readRssFeed(left.getNyt());
        ArrayList<String> urls=new ArrayList<String>();
       // urls.add("https://lifehacker.com/rss");
        
        //Technology 
        //urls.add("https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml");https://feeds.a.dj.com/rss/RSSWSJD.xml
        	//	urls.add("https://feeds.a.dj.com/rss/RSSWSJD.xml");
        //urls.add("https://timesofindia.indiatimes.com/rssfeeds/66949542.cms");
        urls.add("http://feeds.feedburner.com/gadgets360-latest?format=xml");
        		
        //Market News
        //urls.add("https://feeds.a.dj.com/rss/RSSMarketsMain.xml");
        		
        
        //Sports
        urls.add("https://timesofindia.indiatimes.com/rssfeeds/4719148.cms");
        //urls.add("https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml");
        
        //Science
        //urls.add("https://rss.nytimes.com/services/xml/rss/nyt/Science.xml");
        
        urls.add("https://timesofindia.indiatimes.com/rssfeeds/-2128672765.cms");
        
        
        //Health
        //urls.add("https://rss.nytimes.com/services/xml/rss/nyt/Health.xml");
        urls.add("https://timesofindia.indiatimes.com/rssfeeds/3908999.cms");
        
        //Travel
        //urls.add("https://rss.nytimes.com/services/xml/rss/nyt/Travel.xml");
        		
        
        //urls.add("https://www.bbc.com/news/10628494");
        /*urls.add("http://rss.cnn.com/rss/cnn_topstories.rss");
        urls.add("http://rss.cnn.com/rss/cnn_topstories.rss");
        urls.add("http://rss.cnn.com/rss/cnn_topstories.rss");
        urls.add("http://rss.cnn.com/rss/cnn_topstories.rss");*/
        
        ArrayList<Template> templates = rssParser.readRssFeed();
        model.addAttribute("currentPage", 1);
		model.addAttribute("totalPages", templates.size()/10);
		model.addAttribute("totalItems", templates.size());
        model.addAttribute("templates",templates);

        return "index";
    }
}