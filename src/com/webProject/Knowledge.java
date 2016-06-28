package com.webProject;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Knowledge {
	@Persistent
	private String Mail;
	@Persistent
	private String Topic;
	@Persistent
	private String SubTopic;
	@Persistent
	private String Name;
	@Persistent
	private String Level;
	@Persistent
	private String Description;
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public String getTopic() {
		return Topic;
	}
	public void setTopic(String topic) {
		Topic = topic;
	}
	public String getSubTopic() {
		return SubTopic;
	}
	public void setSubTopic(String subTopic) {
		SubTopic = subTopic;
	}
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
