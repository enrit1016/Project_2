<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Search</title>
<style>
body {
	font-family: Arial, sans-serif;
}

.container {
	width: 80%;
	margin: 0 auto;
	padding: 20px;
}

.search-form {
	margin-bottom: 20px;
}

.search-form input[type="text"] {
	width: 70%;
	padding: 10px;
	font-size: 16px;
}

.search-form button {
	padding: 10px 15px;
	font-size: 16px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

table, th, td {
	border: 1px solid #ccc;
}

th, td {
	padding: 10px;
	text-align: left;
}

img {
	max-width: 100px;
}

.no-results {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="container">
		<!-- 검색 창 -->
		<div class="search-form">
			<h1>Search for Music</h1>
			<form action="search" method="get">
				<input type="text" name="query" placeholder="Enter song or artist"
					value="${param.query}" required />
				<button type="submit">Search</button>
			</form>
		</div>

		<!-- 검색 결과 -->
		<div class="search-results">
			<h2>Search Results for "${param.query}"</h2>

			<c:choose>
				<c:when test="${not empty musicList}">
					<table>
						<thead>
							<tr>
								<th>Album Image</th>
								<th>Title</th>
								<th>Artist</th>
								<th>Album Title</th>

								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="music" items="${musicList}">
								<tr>
									<td><img src="${music.albumImage}" alt="Album Image" /></td>
									<td>${music.title}</td>
									<td>${music.albumArtist}</td>
									<td>${music.albumTitle}</td>
									<td><a href="${music.link}" target="_blank">View</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<p class="no-results">No results found.</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>
