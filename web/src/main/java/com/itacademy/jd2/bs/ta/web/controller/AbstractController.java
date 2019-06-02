package com.itacademy.jd2.bs.ta.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.itacademy.jd2.bs.ta.dao.api.filter.AbstractFilter;
import com.itacademy.jd2.bs.ta.web.dto.list.GridStateDTO;
import com.itacademy.jd2.bs.ta.web.dto.list.SortDTO;
import com.itacademy.jd2.bs.ta.web.security.AuthHelper;

public abstract class AbstractController<DTO> {
	protected GridStateDTO getListDTO(final HttpServletRequest req, final int itemsPerPage) {
		final String sessionModelName = getClass().getSimpleName() + "_GRID_STATE";

		GridStateDTO gridState = (GridStateDTO) req.getSession().getAttribute(sessionModelName);
		if (gridState == null) {
			gridState = new GridStateDTO(itemsPerPage);
			req.getSession().setAttribute(sessionModelName, gridState);
		}
		req.setAttribute(GridStateDTO.GRID_STATE_SESSION_KEY, gridState);
		return gridState;
	}

	protected void prepareFilter(final GridStateDTO gridState, final AbstractFilter filter) {
		filter.setLimit(gridState.getItemsPerPage());
		int offset = gridState.getItemsPerPage() * (gridState.getPage() - 1);
		filter.setOffset(gridState.getTotalCount() < offset ? 0 : offset);

		final SortDTO sortModel = gridState.getSort();
		if (sortModel != null) {
			filter.setSortColumn(sortModel.getColumn());
			filter.setSortOrder(sortModel.isAscending());
		}
	}

	protected Integer getLoggedUserId() {
		return AuthHelper.getLoggedUserId();
	}

	protected String getLoggedUserRole() {
		return AuthHelper.getLoggedUserRole();
	}

}