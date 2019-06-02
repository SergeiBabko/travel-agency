<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<footer class="page-footer gradient-45deg-light-blue-cyan">
	<div class="container">
		<div class="row">
			<div class="col l6 s12" style="padding: 0">
				<h5 class="white-text light">Travel Agency</h5>
				<p class="grey-text text-lighten-4">
					<span style="font-style: italic">
						<mytaglib:i18n key="footer.quote" /><br/>
					</span>
					<span>
						&copy; <mytaglib:i18n key="footer.quoteAuthor" />
					</span>
				</p>
			</div>
			<div class="col l4 offset-l2 s12" style="padding: 0">
				<h5 class="white-text light"><mytaglib:i18n key="footer.links" /></h5>
				<ul>
					<li>
						<a class="modal-trigger" href="#users">
							<img class="hoverable circle hvr-grow-my2" src="${contextPath}/resources/img/users.png" style="width: 32px; margin-bottom: 10px">
						</a>
						&nbsp;
						<a class="modal-trigger" href="#db">
							<img class="hoverable circle hvr-grow-my2" src="${contextPath}/resources/img/db.png" style="width: 32px; margin-bottom: 10px">
						</a>
						&nbsp;
						<a class="modal-trigger" href="#mm">
							<img class="hoverable circle hvr-grow-my2" src="${contextPath}/resources/img/mm.png" style="width: 32px; margin-bottom: 10px">
						</a>
						&nbsp;
						<a href="https://github.com/dzhivushko/G-JD2-09-11_sbabko" target="_blank">
							<img class="hoverable circle hvr-grow-my2" src="${contextPath}/resources/img/git.png" style="width: 32px; margin-bottom: 10px">
						</a>
						&nbsp;
						<a href="https://raw.githubusercontent.com/dzhivushko/G-JD2-09-11_sbabko/master/docs/Java%20Developer%20-%20Babko%20Sergei.jpg?token=AkhRFMG6AW3Nf8q6dJR8btW8DLFESDWcks5cHjgxwA%3D%3D" target="_blank">
							<img class="hoverable circle hvr-grow-my2" src="${contextPath}/resources/img/resume.png" style="width: 32px; margin-bottom: 10px">
						</a>
						&nbsp;
						<a href="https://www.linkedin.com/in/sergei-babko-95073a176/" target="_blank">
							<img class="hoverable circle hvr-grow-my2" src="${contextPath}/resources/img/in.png" style="width: 32px; margin-bottom: 10px">
						</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="footer-copyright">
		<div class="container">
			<mytaglib:i18n key="footer.copyright" />
		</div>
	</div>
</footer>

<div id="users" class="modal roundborder">
	<div class="modal-content center-align roundborder">
		<h4 style="margin: 0 0 0.46rem 0">Accounts:</h4>
		<br/>
		<h6><span style="font-weight: 500">Login: </span>admin@gmail.com</h6>
		<h6><span style="font-weight: 500">Password: </span>123456</h6>
		<br/>
		<h6><span style="font-weight: 500">Login: </span>moderator@gmail.com</h6>
		<h6><span style="font-weight: 500">Password: </span>123456</h6>
		<br/>
		<h6>Please register customer profile in this app with your real email to evaluate my email sender :)</h6>
	</div>
</div>

<div id="db" class="modal modal-big roundborder">
	<div class="modal-content roundborder" style="padding: 0">
		<img class="materialboxed roundborder" src="https://github.com/dzhivushko/G-JD2-09-11_sbabko/blob/master/docs/TravelAgency%20DB.png?raw=true" style="width: 100%">
	</div>
</div>
<div id="mm" class="modal modal-big roundborder">
	<div class="modal-content roundborder" style="padding: 0">
		<img class="materialboxed roundborder" src="https://raw.githubusercontent.com/dzhivushko/G-JD2-09-11_sbabko/master/docs/TravelAgency%20MM.png?token=AkhRFAL49qYV4AIwfgJ-L6-WfAFMjH37ks5cG3OPwA%3D%3D" style="width: 100%">
	</div>
</div>