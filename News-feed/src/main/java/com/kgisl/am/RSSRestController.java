package com.kgisl.am;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kgisl.am.models.RssFeed;
import com.kgisl.am.service.RssFeedService;
import com.kgisl.am.tenant.TenantService;


@RestController("/")
public class RSSRestController {
	
	@Autowired
	private RssFeedService rssFeedService;
	
	@Autowired
	private TenantService tenantService;
	
	@PostMapping("/{tenantCode}/rssfeeds")
	public ResponseEntity<RssFeed> createTutorial(@RequestBody RssFeed rssFeed,@PathVariable("tenantCode") String tenantCode) {
		try {
			tenantService.loadSettingsTenant(tenantCode);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			RssFeed feed = rssFeedService.saveRssFeed(rssFeed);
			return new ResponseEntity<>(feed, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
