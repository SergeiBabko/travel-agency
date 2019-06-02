$(document).ready(function() {
	
/*----------------------------------------
				MATERIALIZE
------------------------------------------*/

	$('select').formSelect();

	$('.datepicker').datepicker({
		autoClose : true,
		showClearBtn : true,
		format : 'dd-mm-yyyy'
	});

	$('.sidenav').sidenav({
		closeOnClick : false,
		draggable : true
	});

	$('.collapsible').collapsible();

	$('.fixed-action-btn').floatingActionButton({
		direction : 'left',
		hoverEnabled : false
	});
	
	$('.toolbarEnabled').floatingActionButton({
	    toolbarEnabled: true
	});

	$(".dropdown-trigger").dropdown({
		// hover : true,
		// coverTrigger : false,
		coverTrigger : true,
		constrainWidth : false
	});

	$('.slider').slider({
		interval : 15000
	});

	$('.tooltipped').tooltip();

//	$('input').characterCounter();
//	$('textarea').characterCounter();

	$('.modal').modal({
		opacity : '0.7'
	});

	$('.materialboxed').materialbox();
	
});

/*----------------------------------------
				MY SCRIPTS
------------------------------------------*/

/* PRELOADER ACTIVATION */ /* WORKS GREAT */
$(window).on('load', function() {
	setTimeout(function() {
		$('body').addClass('loaded');
	});
});

/* UPDATE USER ONLINE STATUS */ /* WORKS GREAT */
setInterval(function() {
	var timeInMs = Date.now();
	$.ajax({
        url: "/ta/user/update",
        type:"post",
        data: {
        	date: timeInMs,
        }
    });
}, 30 * 1000);

/* GET USER ONLINE STATUS */ /* WORKS GREAT */
function getOnlineStatus(id){
	function getStatus(){
		$.ajax({
	        url: "/ta/user/onlinestatus",
	        type:"post",
	        data: {
	        	id: id,
	        },
	        success: function(response){    
	        	if (response){
	        		$("#isOnline-" + id).html('<p class="btn roundborder z-depth-0 gradient-45deg-cyan-light-green" style="height: 22px; line-height: 22px; font-size: 9pt">online</p>');
	        	} else {
	        		$("#isOnline-" + id).html('offline');
	        	}
	        }
	    });
	}
	getStatus();
	setInterval(function() {
		getStatus();
	}, 15 * 1000);
}

/* HIDE NAV ON SCROLL */ /* WORKS NOT SO GREAT */
/*var lastScrollTop = 0;
$(window).scroll(function(event) {
	var st = $(this).scrollTop();
	if (st > lastScrollTop) {
		$('.nav-wrapper').removeClass('navbar-fixed');
	} else {
		$('.nav-wrapper').addClass('navbar-fixed');
	}
	lastScrollTop = st;
});*/

/* GRID ITEMS */ /* WORKS GREAT */
$(function() {
	var $containerBlog = $("#item-posts");
	$containerBlog.imagesLoaded(function() {
		$containerBlog.masonry({
			itemSelector : ".item",
			columnWidth : ".item-sizer",
			horizontalOrder: true
		});
	});
});

/* RANGE SLIDER FOR TOUR SEARCH */ /* WORKS GREAT */
function initRange(maxvalue, f, t, prefix) {
	$(".range").ionRangeSlider({
		type : "double",
		grid : true,
		from: f,
		to: t,
	    step: 1,
		min : 0,
		max : maxvalue,
		prefix : prefix
	});
}

/* ADD/DELETE FAVORITE TOUR */ /* WORKS GREAT */
function addToFavorite(contextPath, tourId) {
	$.get(contextPath + "/favorite/adddelete?tourId=" + tourId, function(result) {
		var arr = JSON.parse(result);
		if (arr.result == 1) {
			$(".favoriteCount-" + tourId).text(arr.count);
			$(".addToFavorite-" + tourId).removeClass('cyan lighten-1');
			$(".addToFavorite-" + tourId).addClass('gradient-45deg-red-red');
			$(".addToFavorite-" + tourId).html('<i class="material-icons">favorite</i>');
		} else if (arr.result == 0) {
			$(".favoriteCount-" + tourId).text(arr.count);
			$('.favorite-' + tourId).remove();
			$(".addToFavorite-" + tourId).removeClass('gradient-45deg-red-red');
			$(".addToFavorite-" + tourId).addClass('cyan lighten-1');
			$(".addToFavorite-" + tourId).html('<i class="material-icons">favorite_border</i>');
		}
	});
}

/* ACTIVATE/DEACTVATE TOUR */ /* WORKS GREAT */
function deleteOrActivate(contextPath, tourId, tourName, status) {
	
	$(".tour-" + tourId).ready(function() {
		if (status === "inactive") {
			$(".tour-" + tourId).addClass('gradient-45deg-orange-deep-orange');
		} else {
			$(".tour-" + tourId).removeClass('gradient-45deg-orange-deep-orange');
		}
	});
	
	$(".deleteoractivate-" + tourId).click(function() {
		$.get(contextPath + "/tours/deleteoractivate?id=" + tourId, function(bool) {
			if (bool) {
				swal({
					title: "Activate tour:",
					text: tourName,
					timer: 3000,
					icon: "success"
				});
				$(".deleteoractivate-" + tourId).html('<a><i class="material-icons hvr-icon">delete</i></a>');
				$(".tour-" + tourId).removeClass('gradient-45deg-orange-deep-orange');
			} else {
				swal({
					title: "Deactivate tour:",
					text: tourName,
					timer: 3000,
					icon: "success"
				});
				$(".deleteoractivate-" + tourId).html('<a><i class="material-icons hvr-icon">restore_from_trash</i></a>');
				$(".tour-" + tourId).addClass('gradient-45deg-orange-deep-orange');
			}
		});
	});
	
}

/* ADD TOUR DATES */ /* WORKS GREAT */
function addTourDates(contextPath, tourId) {
	var startDate = $( "#startDate" ).val();
	var endDate = $( "#endDate" ).val();
	var numPerson = $( "#numPerson" ).val();
	if (startDate === "" || endDate === "" || numPerson === "") {
		swal({
			title: "Empty inputs!",
			text: "You need to enter all values to add tour date.",
			icon: "warning",
			timer: 3000,
		});
	} else {
		$.ajax({
			url: contextPath + "/tourdate/add",
			type:"get",
			data: {
				startDate: startDate,
				endDate: endDate,
				numPerson: numPerson,
				tourId: tourId,
			},
			success: function(response){     
				if (response.result) {
					swal({
						title: response.message,
						timer: 3000,
						icon: "success"
					});
					var div = document.getElementById("dates");
					if (div.innerHTML.trim() === "no dates found" || div.innerHTML.trim() === "даты не найдены" ){
						div.innerHTML = '';
					}
					div.innerHTML += '<button class="btn btn-small roundborder gradient-45deg-red-red deleteTourDate-' + response.dateId + '"' + 
						'onclick="deleteTourDate(\'' + contextPath  + '\', \'' + response.dateId + '\');" style="margin: 0.5vh; padding: 0 1vh">' +
						''+ response.dateStart + ' – '+ response.dateEnd + '</button>'
				} else {
					swal({
						title: "Something went wrong! ",
						text: response.message,
						icon: "warning",
						timer: 3000,
					});
				}
			}
		});
	}
}

/* DELETE TOUR DATES */ /* WORKS GREAT */
function deleteTourDate(baseUrl, tourDateId) {
	swal({
		title: "ATTENTION!",
		text: "You can't delete old dates! This feature is only available to delete invalid dates IMMEDIATELY after they are created. Are you sure you would like to delete this date?",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
		if (yes) {
			$.get(baseUrl + "/tourdate/delete?id=" + tourDateId, function(result) {
			if (result === "Done!") {
					swal({
						title: result,
						timer: 3000,
						icon: "success"
					});
					$('.deleteTourDate-' + tourDateId).remove();
				} else {
					swal({
						title: result,
						icon: "warning",
						timer: 3000,
					});
				}
			}); 
		} 
	});
}

/* ACCOUNT RESTORE */ /* WORKS GREAT */
function accountRestore(contextPath) {
	var email = $( "#restoredEmail" ).val();
	if (email === "") {
		swal({
			title: "Empty inputs!",
			text: "You need to enter your email to restore your account.",
			icon: "warning",
			timer: 3000,
		});
	} else {
		$.get(contextPath + "/recovercustomer/restore?email=" + email, function(json) {
			var arr = JSON.parse(json);
				if (arr.result === "Success!") {
					$("#restoredEmail").val('');
					swal({
						title: arr.result,
						text: arr.message,
						timer: 5000,
						icon: "success"
					});
				} else {
					swal({
						title: arr.result,
						text: arr.message,
						icon: "warning",
						timer: 7000,
					});
				}
		});
	}
}

/* BOOKED TOUR */ /* WORKS GREAT */
function bookedTour(contextPath, tourId) {
	$("#tourDates").change(function() {
		selectedId = $(this).val();
	});
	$("#bookedTour").click(function() {
		var personCount = $( "#personCount" ).val();
		if (typeof selectedId === "undefined" || personCount == null){
			swal({
				title: "Empty fields!",
				text: "To book a tour you must specify the date of travel and the number of people.",
				icon: "warning",
				timer: 7000,
			});
		} else {
			var tourDateId = selectedId;
			var firstName = $( "#firstName" ).val();
			var lastName = $( "#lastName" ).val();
			var middleName = $( "#middleName" ).val();
			var birthday = $( "#birthday" ).val();
			var passportNumber = $( "#passportNumber" ).val();
			var passportStart = $( "#passportStart" ).val();
			var passportEnd = $( "#passportEnd" ).val();
			var phoneNumber = $( "#phoneNumber" ).val();
			var country = $( "#country" ).val();
			var city = $( "#city" ).val();
			var street = $( "#street" ).val();
			var message = $( "#message" ).val();
			
			$.ajax({
		        url: contextPath + "/booked/add",
		        type:"post",
		        data: {
		        	tourDateId: tourDateId,
		        	personCount: personCount,
		        	tourId: tourId,
		        	firstName: firstName,
		        	lastName: lastName,
		        	middleName: middleName,
		        	birthday: birthday,
		        	passportNumber: passportNumber,
		        	passportStart: passportStart,
		        	passportEnd: passportEnd,
		        	phoneNumber: phoneNumber,
		        	country: country,
		        	city: city,
		        	street: street,
		        	message: message
		        },
		        success: function(result){     
		        	if (result === "Done!") {
						swal({
							title: result,
							text: "We received your request. Our managers will contact you. Have a good day.",
							timer: 5000,
							icon: "success"
						});
					} else {
						swal({
							title: "Something went wrong! ",
							text: result,
							icon: "warning",
							timer: 5000,
						});
					}
		        }
		    });
			
		}
	});
}

/* DELETE BOOKED TOUR */ /* WORKS GREAT */
function deleteBookedTour(baseUrl, bookedId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this application!",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
			if (yes) {
				$.get(baseUrl + "/booked/delete?bookedId=" + bookedId, function(result) {
					if (result === "Done!") {
						swal({
							title: result,
							timer: 3000,
							icon: "success"
						});
						$('.booked-' + bookedId).remove();
					} else {
						swal({
							title: result,
							icon: "warning",
							timer: 3000,
						});
					}
				}); 
			} 
		});
}

/* RATE TOUR DATE */ /* WORKS GREAT */
function rateTourDate(contextPath) {
	$(".tourDateReview").change(function() {
		selectedId = $(this).val();
	});
	$(".rating").change(function() {
		rating = $(this).val();
	});
	$(".reviewMessage").change(function() {
		reviewMessage = $(this).val();
	});
	$(".rateTour").click(function() {
		if (typeof selectedId === "undefined" || rating == null || typeof reviewMessage === "undefined" || reviewMessage == null || reviewMessage === ""){
			swal({
				title: "Empty fields!",
				text: "To review a tour date you must fill in all fields.",
				icon: "warning",
				timer: 7000,
			});
		} else {
			$.ajax({
		        url: contextPath + "/review/add",
		        type:"post",
		        data: {
		        	tourDateId: selectedId,
		        	review: reviewMessage,
		        	rating: rating
		        },
		        success: function(result){     
		        	if (result === "Done!") {
						swal({
							title: result,
							text: "We received your review. Have a good day.",
							timer: 5000,
							icon: "success"
						});
						$(".reviewMessage").val('');
					} else {
						swal({
							title: "Something went wrong! ",
							text: result,
							icon: "warning",
							timer: 5000,
						});
					}
		        }
		    });
		}
	});
}

/* DELETE REVIEW ON TOUR DATE */ /* WORKS GREAT */
function deleteReview(contextPath, reviewId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this review!",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
			if (yes) {
				$.get(contextPath + "/review/delete?reviewId=" + reviewId, function(result) {
					if (result === "Done!") {
						swal({
							title: result,
							timer: 3000,
							icon: "success"
						});
						$('.review-' + reviewId).remove();
					} else {
						swal({
							title: result,
							icon: "warning",
							timer: 3000,
						});
					}
				}); 
			} 
		});
}

/* DEACTIVATE CUSTOMER ACCOUNT */ /* WORKS GREAT */
function deactivateCustomer(contextPath, customerId) {
	swal({
		title: "Are you sure?",
		text: "You can restore your account on login page anytime.",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
			if (yes) {
				$.ajax({
			        url: contextPath + "/customer/delete",
			        type:"get",
			        data: {
			        	customerId: customerId,
			        },
			        success: function(result){     
			        	if (result === "Done!") {
							swal({
								title: result,
								text: "You will be logout.",
								timer: 5000,
								icon: "warning",
								buttons: true,	
							}).then((yes) => {
								location.href = contextPath + "/execute_logout";
							});
						} else {
							swal({
								title: "Something went wrong! ",
								text: result,
								icon: "warning",
								timer: 5000,
							});
						}
			        }
			    });
			}
		});
}

/* MANAGE CUSTOMER ACCOUNT */ /* WORKS GREAT */
function updateCustomer(contextPath, customerId) {
	var firstName = $( "#firstName" ).val();
	var lastName = $( "#lastName" ).val();
	var middleName = $( "#middleName" ).val();
	var birthday = $( "#birthday" ).val();
	var passportNumber = $( "#passportNumber" ).val();
	var passportStart = $( "#passportStart" ).val();
	var passportEnd = $( "#passportEnd" ).val();
	var phoneNumber = $( "#phoneNumber" ).val();
	var country = $( "#country" ).val();
	var city = $( "#city" ).val();
	var street = $( "#street" ).val();
	
	$.ajax({
        url: contextPath + "/customer/update",
        type:"post",
        data: {
        	customerId: customerId,
        	firstName: firstName,
        	lastName: lastName,
        	middleName: middleName,
        	birthday: birthday,
        	passportNumber: passportNumber,
        	passportStart: passportStart,
        	passportEnd: passportEnd,
        	phoneNumber: phoneNumber,
        	country: country,
        	city: city,
        	street: street,
        },
        success: function(result){     
        	if (result === "Done!") {
        		swal({
					title: result,
					text: "To see the global changes you need to relogin in your account. Do you want to logout?",
					icon: "success",
					buttons: true,	
        		}).then((yes) => {
        			if (yes) {
        				location.href = contextPath + "/execute_logout";
        			  }
        		});
			} else {
				swal({
						title: "Something went wrong! ",
						text: result,
						icon: "warning",
						timer: 5000,
					});
			}
        }
    });
	
}

/* CHANGE PASSWORD */ /* WORKS GREAT */
function updatePassword(contextPath, userId) {
	var oldPassword = $( "#oldPassword" ).val();
	var newPassword = $( "#newPassword" ).val();
		
	$.ajax({
        url: contextPath + "/user/password",
        type:"post",
        data: {
        	id: userId,
        	oldPassword: oldPassword,
        	newPassword: newPassword,
        },
        success: function(result){     
        	if (result === "Done!") {
        		swal({
					title: result,
					text: "You need to relogin with your new password.",
					icon: "success",
					buttons: true,	
        		}).then((yes) => {
        			location.href = contextPath + "/execute_logout";
        		});
			} else {
				swal({
					title: "Something went wrong! ",
					text: result,
					icon: "warning",
					timer: 5000,
				});
			}
        }
    });
	
}

/* DELETE NEWS */ /* WORKS GREAT */
function deleteNews(contextPath, newsId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this news!",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
			if (yes) {
				$.get(contextPath + "/news/delete?newsId=" + newsId, function(result) {
					if (result === "Done!") {
						swal({
							title: result,
							timer: 3000,
							icon: "success"
						});
						$('.news-' + newsId).remove();
						
						$(function() {
							var $containerBlog = $("#item-posts");
							$containerBlog.imagesLoaded(function() {
								$containerBlog.masonry({
									itemSelector : ".item",
									columnWidth : ".item-sizer",
									horizontalOrder: true
								});
							});
						});
						
					} else {
						swal({
							title: result,
							icon: "warning",
							timer: 3000,
						});
					}
				}); 
			} 
		});
}

/* REGISTER */ /* WORKS GREAT */
function register(contextPath) {
	var regemail = $( "#regemail" ).val();
	var regpass = $( "#regpass" ).val();
	if (regemail === "" || regpass === "") {
		swal({
				title: "Empty inputs!",
				text: "You need to enter your email and password to register in this app.",
				icon: "warning",
				timer: 5000,
			});
	} else {
		$.ajax({
	        url: contextPath + "/register/register",
	        type:"post",
	        data: {
	        	email: regemail,
	        	password: regpass,
	        },
	        success: function(result){     
	        	if (result === "Done!") {
	        		swal({
						title: result,
						text: "Now you can login in app.",
						timer: 3000,
						icon: "success"
	        		})
				} else {
					swal({
						title: "Something went wrong! ",
						text: result,
						icon: "warning",
						timer: 5000,
					});
				}
	        }
	    });
	}
}

/* CREATE NEW MANAGER */ /* WORKS GREAT */
function createNewManager(contextPath) {
	var email = $( "#newManagerEmail" ).val();
	var pass = $( "#newManagerPassword" ).val();
	var name = $( "#newManagerName" ).val();
	var surname = $( "#newManagerSurname" ).val();
	if (email === "" || pass === "") {
		swal({
				title: "Empty inputs!",
				text: "You need to enter email and password to register new manager.",
				icon: "warning",
				timer: 5000,
			});
	} else {
		$.ajax({
	        url: contextPath + "/manager/register",
	        type:"post",
	        data: {
	        	email: email,
	        	password: pass,
	        	name: name,
	        	surname: surname,
	        },
	        success: function(response){     
	        	if (response.result) {
	        		swal({
						title: response.message,
						text: "New moderator created.",
						timer: 3000,
						icon: "success"
	        		})
	        		var tbody = document.getElementById("managers");
	        	    var row = tbody.insertRow();
	        	    row.id = "manager-" + response.userId;
	        	    
	        	    var cell1 = row.insertCell(0);
	        	    
	        	    if (response.count % 2 == 0) {
	        	    	row.classList.add("even");
	        	    	cell1.style["background-color"] = "#26c6da10";
	        	    } else {
	        	    	row.classList.add("odd");
	        	    	cell1.style["background-color"] = "#26c6da20";
	        	    }
	        	    
	        	    var cell2 = row.insertCell(1);
	        	    var cell3 = row.insertCell(2);
	        	    var cell4 = row.insertCell(3);
	        	    var cell5 = row.insertCell(4);
	        	    var cell6 = row.insertCell(5);
	        	    var cell7 = row.insertCell(6);
	        	    cell1.innerHTML = response.userId;
	        	    cell2.innerHTML = response.userEmail;
	        	    cell3.innerHTML = response.userName;
	        	    cell4.innerHTML = response.userSurname;
	        	    cell5.innerHTML = response.userRole;
	        	    cell6.innerHTML = response.userCreated;
	        	    cell7.innerHTML = "<a class=\"btn-floating waves-effect waves-light red lighten-1\" " +
					        	    "style=\"width: 22px; height: 22px; line-height: 22px\"> <i class=\"material-icons\" " +
					        	    "onclick=\"deleteManagerAccount('" + contextPath + "', '" + response.userId + "');\"" +
					        	    "style=\"font-size: 10pt; line-height: inherit\">delete</i></a>" + 
					        	    "<script>deleteManagerAccount('" + contextPath + "', '" + response.userId + "');</script>";
				} else {
					swal({
						title: "Something went wrong! ",
						text: response.message,
						icon: "warning",
						timer: 5000,
					});
				}
	        }
	    });
	}
}

/* DELETE MANAGER ACCOUNT */ /* WORKS GREAT */
function deleteManagerAccount(contextPath, managerId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this manager account!",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
		if (yes) {
			$.ajax({
				url: contextPath + "/manager/delete",
				type:"post",
				data: {
					id: managerId,
				},
				success: function(result){     
					if (result === "Done!") {
						swal({
							title: result,
							text: "Manager was deleted.",
							timer: 3000,
							icon: "success"
						})
						$("#manager-" + managerId).remove();
					} else {
						swal({
							title: "Something went wrong! ",
							text: result,
							icon: "warning",
							timer: 5000,
						});
					}
				}
			});
		}
	});
}

/* UPDATE MANAGER ACCOUNT */ /* WORKS GREAT */
function updateManager(contextPath, userId) {
	var firstName = $( "#firstName" ).val();
	var lastName = $( "#lastName" ).val();
	
	$.ajax({
        url: contextPath + "/manager/update",
        type:"post",
        data: {
        	id: userId,
        	firstName: firstName,
        	lastName: lastName,
        },
        success: function(result){     
        	if (result === "Done!") {
        		swal({
					title: result,
					text: "To see the global changes you need to relogin in your account. Do you want to logout?",
					icon: "success",
					buttons: true,	
        		}).then((yes) => {
        			if (yes) {
        				location.href = contextPath + "/execute_logout";
        			  }
        		});
			} else {
				swal({
					title: "Something went wrong! ",
					text: result,
					icon: "warning",
					timer: 5000,
				});
			}
        }
    });
		
}

/* BLOCK CUSTOMER ACCOUNT */ /* WORKS GREAT */
function blockCustomer(contextPath, customerId, status) {
	
	$("#customer-" + customerId).ready(function() {
		if (status === "blocked") {
			$("#customer-" + customerId).addClass('even deep-orange lighten-4');
		} else {
			$("#customer-" + customerId).removeClass('deep-orange lighten-4');
		}
	});
	
	$(".blockCustomer-" + customerId).change(function() {
		$.ajax({
			url: contextPath + "/manager/blockcustomer",
			type:"post",
			data: {
				id: customerId,
			},
			success: function(result){     
				if (result === "active") {
					 M.toast({html: result, classes: 'rounded'});
					 $("#customer-" + customerId).removeClass('deep-orange lighten-4');
					 $("#status-" + customerId).html('active');
				} else if (result === "blocked") {
					 M.toast({html: result, classes: 'rounded'});
					 $("#customer-" + customerId).addClass('deep-orange lighten-4');
					 $("#status-" + customerId).html('blocked');
				} else {
					swal({
						title: "Something went wrong! ",
						text: result,
						icon: "warning",
						timer: 5000,
					});
				}
			}
		});
	});
}

/* SET BOOKED AS PROCESSED */ /* WORKS GREAT */
function processBooked(contextPath, bookedId, processed) {
	
	$('.booked-' + bookedId).ready(function() {
		if (processed === 'true') {
			$('.booked-' + bookedId).addClass('green accent-1');
		} else {
			$('.booked-' + bookedId).removeClass('green accent-1');
		}
	});
	
	function changeStatus() {
		$.ajax({
			url: contextPath + "/booked/processed",
			type:"post",
			data: {
				bookedId: bookedId,
			},
			success: function(result){     
				if (result === "Set as processed!") {
					 M.toast({html: result, classes: 'rounded'});
					 $('.booked-' + bookedId).addClass('green accent-1');
				} else if (result === "Set as unprocessed!") {
					 M.toast({html: result, classes: 'rounded'});
					 $('.booked-' + bookedId).removeClass('green accent-1');
				} else {
					swal({
						title: "Something went wrong! ",
						text: result,
						icon: "warning",
						timer: 5000,
					});
				}
			}
		});
	}
	
	$(".processBooked-" + bookedId).change(function() {
		if($(this).prop("checked")) {
			swal({
				title: "ATTENTION",
				text: "Are you sure you want to change the booked status to processed? After the page updating you can no longer change this status!",
				icon: "warning",
				buttons: true,	
    		}).then((yes) => {
    			if (yes) {
    				changeStatus();
    			  } else {
    				  var checkbox = document.querySelector(".processBooked-" + bookedId);
    			      checkbox.checked = false;
    			  }
    		});
		} else{ 
			changeStatus();
		}
	});
}

/* ADD NEW COUNTRY */ /* WORKS GREAT */
function createNewCountry(contextPath) {
	var name = $( "#newCountryName" ).val();
	if (name === "") {
		swal({
			title: "Empty input!",
			text: "You need to enter country name to add new country.",
			icon: "warning",
			timer: 5000,
		});
	} else {
		$.ajax({
	        url: contextPath + "/country/add",
	        type:"post",
	        data: {
	        	name: name,
	        },
	        success: function(response){     
	        	if (response.result) {
	        		swal({
						title: response.message,
						text: "New county created.",
						timer: 3000,
						icon: "success"	
	        		})
	        		var tbody = document.getElementById("country");
	        	    var row = tbody.insertRow();
	        	    row.id = "country-" + response.countryId;
	        	    
	        	    var cell1 = row.insertCell(0);
	        	    
	        	    if (response.count % 2 == 0) {
	        	    	row.classList.add("even");
	        	    	cell1.style["background-color"] = "#26c6da10";
	        	    } else {
	        	    	row.classList.add("odd");
	        	    	cell1.style["background-color"] = "#26c6da20";
	        	    }
	        	    
	        	    var cell2 = row.insertCell(1);
	        	    var cell3 = row.insertCell(2);
	        	    var cell4 = row.insertCell(3);
	        	    cell1.innerHTML = response.countryId;
	        	    cell2.innerHTML = response.countryName;
	        	    cell3.innerHTML = response.countryCreated;
	        	    cell4.innerHTML = "<a class=\"btn-floating waves-effect waves-light teal lighten-3\" " +
					        	    "href=\"#editCountry-"+ response.countryId + "\" style=\"width: 22px; height: 22px; line-height: 22px\"> " +
					        	    "<i class=\"material-icons\" style=\"font-size: 10pt; line-height: inherit\">edit</i></a>" +
					        	    " " +
					        	    "<a class=\"btn-floating waves-effect waves-light red lighten-1\" onclick=\"deleteCountry('" + contextPath + "', '" + response.countryId + "');\" " +
					        	    "style=\"width: 22px; height: 22px; line-height: 22px\">" +
					        	    "<i class=\"material-icons\" style=\"font-size: 10pt; line-height: inherit\">delete</i></a>";
				} else {
					swal({
						title: "Something went wrong! ",
						text: response.message,
						icon: "warning",
						timer: 5000,
					});
				}
	        }
	    });
	}
}

/* DELETE COUNTRY */ /* WORKS GREAT */
function deleteCountry(contextPath, countryId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this country. And you can't remove the country, which is referred to the city.",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
		if (yes) {
			$.ajax({
				url: contextPath + "/country/delete",
				type:"post",
				data: {
					id: countryId,
				},
				success: function(result){     
					if (result === "Done!") {
						swal({
							title: result,
							text: "Country was deleted.",
							timer: 3000,
							icon: "success"	
						})
						$("#country-" + countryId).remove();
					} else {
						swal({
							title: "Something went wrong! ",
							text: result,
							icon: "warning",
							timer: 5000,
						});
					}
				}
			});
		}
	});
}

/* UPDATE COUNTRY */ /* WORKS GREAT */
function updateCountry(contextPath, countryId, defName) {
	
	var name = $("#countryName-" + countryId).val();
	
	$.ajax({
        url: contextPath + "/country/update",
        type:"post",
        data: {
        	id: countryId,
        	name: name,
        },
        success: function(response){     
        	if (response.result) {
        		swal({
					title: "Done!",
					timer: 3000,
					icon: "success"
        		})
        		$("#defCountryName-" + countryId).html(name);
			} else {
				$("#countryName-" + countryId).val(defName);
				$("#countryLabel-" + countryId).addClass("active");
				swal({
					title: "Something went wrong! ",
					text: response.message,
					icon: "warning",
					timer: 5000,
				});
			}
        }
    });
		
}

/* ADD NEW TOUR TYPE */ /* WORKS GREAT */
function createNewTourType(contextPath) {
	var name = $( "#newTourType" ).val();
	if (name === "") {
		swal({
			title: "Empty input!",
			text: "You need to enter type to add new tour type.",
			icon: "warning",
			timer: 5000,
		});
	} else {
		$.ajax({
	        url: contextPath + "/type/add",
	        type:"post",
	        data: {
	        	name: name,
	        },
	        success: function(response){     
	        	if (response.result) {
	        		swal({
						title: response.message,
						text: "New tour type created.",
						timer: 3000,
						icon: "success"	
	        		})
	        		var tbody = document.getElementById("tourType");
	        	    var row = tbody.insertRow();
	        	    row.id = "tourType-" + response.tourTypeId;
	        	    
	        	    var cell1 = row.insertCell(0);
	        	    
	        	    if (response.count % 2 == 0) {
	        	    	row.classList.add("even");
	        	    	cell1.style["background-color"] = "#26c6da10";
	        	    } else {
	        	    	row.classList.add("odd");
	        	    	cell1.style["background-color"] = "#26c6da20";
	        	    }
	        	    
	        	    var cell2 = row.insertCell(1);
	        	    var cell3 = row.insertCell(2);
	        	    var cell4 = row.insertCell(3);
	        	    cell1.innerHTML = response.tourTypeId;
	        	    cell2.innerHTML = response.tourTypeName;
	        	    cell3.innerHTML = response.tourTypeCreated;
	        	    cell4.innerHTML = "<a class=\"btn-floating waves-effect waves-light teal lighten-3\" " +
					        	    "href=\"#editTourType-"+ response.tourTypeId + "\" style=\"width: 22px; height: 22px; line-height: 22px\"> " +
					        	    "<i class=\"material-icons\" style=\"font-size: 10pt; line-height: inherit\">edit</i></a>" +
					        	    " " +
					        	    "<a class=\"btn-floating waves-effect waves-light red lighten-1\" onclick=\"deleteTourType('" + contextPath + "', '" + response.tourTypeId + "');\" " +
					        	    "style=\"width: 22px; height: 22px; line-height: 22px\">" +
					        	    "<i class=\"material-icons\" style=\"font-size: 10pt; line-height: inherit\">delete</i></a>";
				} else {
					swal({
						title: "Something went wrong! ",
						text: response.message,
						icon: "warning",
						timer: 5000,
					});
				}
	        }
	    });
	}
}

/* DELETE TOUR TYPE */ /* WORKS GREAT */
function deleteTourType(contextPath, typeId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this type. And you can't remove the type, which is referred to the tour.",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
		if (yes) {
			$.ajax({
				url: contextPath + "/type/delete",
				type:"post",
				data: {
					id: typeId,
				},
				success: function(result){     
					if (result === "Done!") {
						swal({
							title: result,
							text: "Tour type was deleted.",
							timer: 3000,
							icon: "success"	
						})
						$("#tourType-" + typeId).remove();
					} else {
						swal({
							title: "Something went wrong! ",
							text: result,
							icon: "warning",
							timer: 5000,
						});
					}
				}
			});
		}
	});
}

/* UPDATE TOUR TYPE */ /* WORKS GREAT */
function updateTourType(contextPath, typeId, defName) {
	
	var name = $("#tourTypeName-" + typeId).val();
	
	$.ajax({
        url: contextPath + "/type/update",
        type:"post",
        data: {
        	id: typeId,
        	name: name,
        },
        success: function(response){     
        	if (response.result) {
        		swal({
					title: "Done!",
					timer: 3000,
					icon: "success"
        		})
        		$("#defTourType-" + typeId).html(name);
			} else {
				$("#tourTypeName-" + typeId).val(defName);
				$("#tourTypeLabel-" + typeId).addClass("active");
				swal({
					title: "Something went wrong! ",
					text: response.message,
					icon: "warning",
					timer: 5000,
				});
			}
        }
    });
		
}

/* ADD NEW CITY */ /* WORKS GREAT */
function getCountryId() {
	$(".countryAddSelect").change(function() {
		addedCountryId = $(this).val();
	});
	$(".countryEditSelect").change(function() {
		editCountryId = $(this).val();
	});
}
function createNewCity(contextPath) {
	var name = $( "#newCityName" ).val();
	if (name === "" || typeof addedCountryId === "undefined") {
		swal({
			title: "Empty input!",
			text: "You need to enter city name and select country to add new city.",
			icon: "warning",
			timer: 5000,
		});
	} else {
		$.ajax({
	        url: contextPath + "/city/add",
	        type:"post",
	        data: {
	        	name: name,
	        	countryId: addedCountryId,
	        },
	        success: function(response){     
	        	if (response.result) {
	        		swal({
						title: response.message,
						text: "New city created.",
						timer: 3000,
						icon: "success"	
	        		})
	        		var tbody = document.getElementById("city");
	        	    var row = tbody.insertRow();
	        	    row.id = "city-" + response.cityId;
	        	    
	        	    var cell1 = row.insertCell(0);
	        	    
	        	    if (response.count % 2 == 0) {
	        	    	row.classList.add("even");
	        	    	cell1.style["background-color"] = "#26c6da10";
	        	    } else {
	        	    	row.classList.add("odd");
	        	    	cell1.style["background-color"] = "#26c6da20";
	        	    }
	        	    
	        	    var cell2 = row.insertCell(1);
	        	    var cell3 = row.insertCell(2);
	        	    var cell4 = row.insertCell(3);
	        	    var cell5 = row.insertCell(4);
	        	    cell1.innerHTML = response.cityId;
	        	    cell2.innerHTML = response.cityName;
	        	    cell3.innerHTML = response.tourcountryName;
	        	    cell4.innerHTML = response.cityCreated;
	        	    cell5.innerHTML = "<a class=\"btn-floating waves-effect waves-light teal lighten-3\" " +
					        	    "href=\"#editCity-"+ response.cityId + "\" style=\"width: 22px; height: 22px; line-height: 22px\"> " +
					        	    "<i class=\"material-icons\" style=\"font-size: 10pt; line-height: inherit\">edit</i></a>" +
					        	    " " +
					        	    "<a class=\"btn-floating waves-effect waves-light red lighten-1\" onclick=\"deleteCity('" + contextPath + "', '" + response.cityId + "');\" " +
					        	    "style=\"width: 22px; height: 22px; line-height: 22px\">" +
					        	    "<i class=\"material-icons\" style=\"font-size: 10pt; line-height: inherit\">delete</i></a>";
				} else {
					swal({
						title: "Something went wrong! ",
						text: response.message,
						icon: "warning",
						timer: 5000,
					});
				}
	        }
	    });
	}
}

/* DELETE CITY */ /* WORKS GREAT */
function deleteCity(contextPath, cityId) {
	swal({
		title: "Are you sure?",
		text: "Once deleted, you will not be able to recover this city. And you can't remove the city, which is referred to the country.",
		icon: "warning",
		buttons: true,
	}).then((yes) => {
		if (yes) {
			$.ajax({
				url: contextPath + "/city/delete",
				type:"post",
				data: {
					id: cityId,
				},
				success: function(result){     
					if (result === "Done!") {
						swal({
							title: result,
							text: "City was deleted.",
							timer: 3000,
							icon: "success"	
						})
						$("#city-" + cityId).remove();
					} else {
						swal({
							title: "Something went wrong! ",
							text: result,
							icon: "warning",
							timer: 5000,
						});
					}
				}
			});
		}
	});
}

/* UPDATE CITY */ /* WORKS GREAT */
function updateCity(contextPath, cityId, defName) {
	
	var name = $("#cityName-" + cityId).val();
	
	$.ajax({
        url: contextPath + "/city/update",
        type:"post",
        data: {
        	id: cityId,
        	name: name,
        	countryId: editCountryId,
        },
        success: function(response){     
        	if (response.result) {
        		swal({
					title: "Done!",
					timer: 3000,
					icon: "success"
        		})
        		$("#defCityName-" + cityId).html(name);
        		$("#defCityCountryName-" + cityId).html(response.countryName);
			} else {
				$("#cityName-" + cityId).val(defName);
				$("#cityLabel-" + cityId).addClass("active");
				swal({
					title: "Something went wrong! ",
					text: response.message,
					icon: "warning",
					timer: 5000,
				});
			}
        }
    });
		
}

/* BOOKED CHART */ /* WORKS GREAT */
function getBookedByMonth() {
	
	function getCount(){
		$.ajax({
	        url: "/ta/booked/getcountbymonth",
	        type:"get",
	        success: function(response){   
	        	var ctx = document.getElementById('myChart').getContext('2d');
	        	var chart = new Chart(ctx, {
	        	    type: 'line',
        		    data: {
	        	        labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
	        	        datasets: [{
	        	            label: "Booked",
	        	            data: [response.january, response.february,
	        	            		response.march, response.april,
	        	            		response.may, response.june,
	        	            		response.july, response.august,
	        	            		response.september, response.october,
	        	            		response.november, response.december],
	        	            backgroundColor: '#26c6da',
	        	            borderColor: '#26c6da',
	        	        }]
	        	    },
	        	    options: {
	        	        legend: {
	        	            display: false,
	        	        },
		        	    title: {
		                    display: true,
		                    text: 'Count of booked by month'
		                },
		                scales: {
		                    yAxes: [{
		                    	ticks: {
		                            stepSize: 1
		                        }
		                    }]
		                }
	        	    }
	        	});
	        }
	    });
	}
	
	getCount();
	
	setInterval(function() {
		getCount();
	}, 30 * 1000);
}