package dev.roberts;

import java.util.Date;
import dev.roberts.Person;
import dev.roberts.User;

public class Story {
	private User author, editor;
	private String title, blurb, description, status;
	private Date estCompDate;
	private int storyLength;

	
	public Story() {
		this.author = new User();
		this.editor = new User();
		this.title = "TITLE";
		this.blurb = "BLURB";
		this.description = "DESCRIPTION";
		this.status = "STATUS";
		this.estCompDate = new Date();
		this.storyLength = 0;
	}
	
	public Story(User auth, String title, String blurb, String desc, Date day, int len) {
		this.author = auth;
		this.editor = new User();
		this.title = title;
		this.blurb = blurb;
		this.description = desc;
		this.status = "Pending senior editor approval";
		this.estCompDate = day;
		this.storyLength = len;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public User getEditor() {
		return editor;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBlurb() {
		return blurb;
	}
	
	public String getDesc() {
		return description;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Date getCompDate() {
		return estCompDate;
	}
	
	public int getLength() {
		return storyLength;
	}
	
	public void setAuthor(User u) {
		author = u;
	}
	
	public void setEditor(User u) {
		editor = u;
	}
	
	public void setTitle(String s) {
		title = s;
	}
	
	public void setBlurb(String s) {
		blurb = s;
	}
	
	public void setDesc(String s) {
		description = s;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public void setCompDate(Date d) {
		estCompDate = d;
	}
	
	public void setLength(int x) {
		storyLength = x;
	}
}
