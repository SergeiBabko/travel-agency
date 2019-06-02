package com.itacademy.jd2.bs.ta.web.converter.fromDTO;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.service.INewsService;
import com.itacademy.jd2.bs.ta.web.dto.NewsDTO;

@Component
public class NewsFromDTOConverter implements Function<NewsDTO, INews> {

	private INewsService newsService;

	@Autowired
	public NewsFromDTOConverter(final INewsService newsService) {
		super();
		this.newsService = newsService;
	}

	@Override
	public INews apply(final NewsDTO dto) {
		final INews entity = newsService.createEntity();
		entity.setId(dto.getId());
        entity.setVersion(dto.getVersion());
		entity.setName(dto.getName());
		entity.setImage(dto.getImage());
		entity.setText(dto.getText());
		return entity;
	}

}
