package com.itacademy.jd2.bs.ta.web.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class NewsDTO {

	private Integer id;
	private Date created;
	private Date updated;
	private Integer version;

	@NotEmpty
	@Size(min = 10, max = 100)
	private String name;
	@NotEmpty
	private String image;
	@NotEmpty
	@Size(min = 20)
	private String text;
	private String shortText;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String getShortText() {
		return shortText;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	public void setShortText(final String text) {
		if (text != null && text.length() > 100) {
			StringBuffer sb = new StringBuffer(text.substring(0, 99));
			if (sb.charAt(sb.length() - 1) == ' ') {
				sb.deleteCharAt(sb.length() - 1);
			}
			shortText = sb + "...";
		} else {
			shortText = text;
		}
	}

}
