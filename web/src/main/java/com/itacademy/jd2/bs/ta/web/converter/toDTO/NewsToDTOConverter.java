package com.itacademy.jd2.bs.ta.web.converter.toDTO;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.web.dto.NewsDTO;

@Component
public class NewsToDTOConverter implements Function<INews, NewsDTO> {

	@Override
	public NewsDTO apply(final INews entity) {
		final NewsDTO dto = new NewsDTO();
		dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
		dto.setName(entity.getName());
		dto.setImage(entity.getImage());
		dto.setText(entity.getText());
		dto.setShortText(entity.getText());
		dto.setCreated(entity.getCreated());
		dto.setUpdated(entity.getUpdated());
		return dto;
	}

}
