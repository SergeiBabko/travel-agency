<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br />

<c:choose>
	<c:when test="${currentPageGridState.totalCount>0}">
		<div class="row center-align">
			<ul class="pagination">
				<c:choose>
					<c:when test="${currentPageGridState.firstPage}">
						<li class="waves-effect disabled roundborder"><a class="material-icons">first_page</a></li>
						<li class="waves-effect disabled roundborder"><a class="material-icons">chevron_left</a></li>
					</c:when>
					<c:otherwise>
						<li class="waves-effect roundborder"><a class="material-icons" href="?page=${currentPageGridState.fPage}">first_page</a></li>
						<li class="waves-effect roundborder"><a class="material-icons" href="?page=${currentPageGridState.page-1}">chevron_left</a></li>
					</c:otherwise>
				</c:choose>
				<c:forEach begin="1" end="${currentPageGridState.pageCount}" varStatus="loop">
					<c:choose>
						<c:when test="${loop.index == currentPageGridState.page}">
							<li class="active waves-effect waves-light roundborder"><a>${loop.index}</a></li>
						</c:when>
						<c:otherwise>
							<li class="waves-effect roundborder"><a href="?page=${loop.index}">${loop.index}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${currentPageGridState.lastPage}">
						<li class="waves-effect disabled roundborder"><a class="material-icons">chevron_right</a></li>
						<li class="waves-effect disabled roundborder"><a class="material-icons">last_page</a></li>
					</c:when>
					<c:otherwise>
						<li class="waves-effect roundborder"><a class="material-icons" href="?page=${currentPageGridState.page+1}">chevron_right</a></li>
						<li class="waves-effect roundborder"><a class="material-icons" href="?page=${currentPageGridState.pageCount}">last_page</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</c:when>
</c:choose>
