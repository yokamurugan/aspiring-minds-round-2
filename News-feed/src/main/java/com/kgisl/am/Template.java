package com.kgisl.am;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Template {

	private String title;
	private String description;
	private Date publishedDate;
	private String link;
	private String Category;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String toString() {
		return "<p>" + "<h4>" + "<br>" + this.getTitle() + "</br>" + "</h4>" + "</p>" + "</br><p>"
				+ this.getDescription() + "</p>" + "<p>" + this.getPublishedDate() + "</p>" + "<br>" + "<a href="
				+ this.getLink() + ">" + "full article" + "</a>" + "</br>";

	}

	public ArrayList<Template> orderByDate(ArrayList<Template> list) {
		list.sort(Comparator.comparing(o -> o.getPublishedDate()));
		return list;
	}

}
