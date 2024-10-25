<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Modal UI</title>
<link rel="stylesheet" type="text/css" href="${root}css/modalUI.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
	<h3 class="title">
		<b>Mocean</b> SliderModals
	</h3>

	<div class="btn-group">

		<button class="mocean-modal-button" data-mocean-type="slide-in-top">Slide
			In (top)</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-right">Slide
			In (right)</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-bottom">Slide
			In (bottom)</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-left">Slide
			In (left)</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-top"
			data-mocean-out-type="slide-out-bottom">From Top to Bottom</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-bottom"
			data-mocean-out-type="slide-out-top">From Bottom to Top</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-left"
			data-mocean-out-type="slide-out-right">From Left to Right</button>
		<button class="mocean-modal-button" data-mocean-type="slide-in-right"
			data-mocean-out-type="slide-out-left">From Right to Left</button>

	</div>


	<div class="mocean-wrap mocean-modal-wrap" id="mocean-modal-wrap">
		<div class="mocean-content mocean-modal" id="mocean-modal">
			<h3>The Mocean Modal</h3>
			<div class="mocean-modal-content">
				<p>Praesent commodo cursus magna, vel scelerisque nisl
					consectetur et. Aenean eu leo quam. Pellentesque ornare sem lacinia
					quam venenatis vestibulum. Aenean lacinia bibendum nulla sed
					consectetur. Nullam quis risus eget urna mollis ornare vel eu leo.
					Nullam quis risus eget urna mollis ornare vel eu leo.</p>
				<button class="mocean-modal-close">Close me!</button>
			</div>
		</div>
	</div>
	<script src="${root}js/modalUI.js"></script>
</body>
</html>