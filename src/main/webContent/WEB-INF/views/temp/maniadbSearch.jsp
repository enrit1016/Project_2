<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>Music Search</title>
<style>
/* 스타일은 기존과 동일 */
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

.search-form select {
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
				<input type="text" name="query"
					placeholder="Enter song, artist, or album" value="${param.query}"
					required /> <select name="searchType">
					<option value="song"
						${param.searchType == 'song' ? 'selected' : ''}>Song</option>
					<option value="artist"
						${param.searchType == 'artist' ? 'selected' : ''}>Artist</option>
					<option value="album"
						${param.searchType == 'album' ? 'selected' : ''}>Album</option>
				</select>
				<button type="submit">Search</button>
			</form>
		</div>

		<!-- 검색 결과 -->
		<div class="search-results">
			<h2>Search Results for "${param.query}" (${param.searchType})</h2>

			<c:choose>
				<c:when test="${searchType == 'song'}">
					<c:forEach var="music" items="${searchResult}">
						<div>
							<h3>${music.title}</h3>
							<img src="${music.albumImage}" alt="${music.albumArtist}" />
							<p>Album: ${music.albumTitle}</p>
							<p>Artist: ${music.albumArtist}</p>
							<a href="${music.guid}">상세 페이지로</a>
							<a href="parseDetail?guid=${music.guid}&type=album">제작중인 페이지로</a>
							
						</div>
					</c:forEach>
				</c:when>
				<c:when test="${searchType == 'artist'}">
					<c:forEach var="artist" items="${searchResult}">
						<div>
							<h3>${artist.artistName}</h3>
							<img src="${artist.image}" alt="${artist.artistName}">
							<p>Period: ${artist.period}</p>
							<p>Description: ${artist.description}</p> <br />
							<a href="parseDetail?guid=${artist.link}&type=artist">제작중인 페이지로</a>
							<a href="parseDetail?guid=${artist.link}&type=artist">제작중인 페이지로</a>
							<c:forEach var="majorSong" items="${artist.majorSongList}"
								varStatus="status">
								<tr>
									<!-- 트랙 이름 -->
									<td>${majorSong}</td> <br />
									
								</tr>
							</c:forEach>
						</div>
					</c:forEach>
				</c:when>
				<c:when test="${searchType == 'album'}">
					<c:forEach var="album" items="${searchResult}">
						<div>
							<h3>${album.albumName}</h3>
							<p>Artist: ${album.albumArtist}</p>
							<p>Release Company: ${album.releaseCompany}</p>
							<a href="${album.guid}">상세 페이지로</a>
							<a href="parseDetail?guid=${fn:escapeXml(album.guid)}&type=album">제작중인 페이지로</a>
							<c:forEach var="track" items="${album.trackList}"
								varStatus="status">
								<tr>
									<!-- 트랙 이름 -->
									<td>${track}</td> <br />
									
								</tr>
							</c:forEach>
							<p>Description: ${album.description}</p>
							<img src="${album.albumimage}" alt="${album.albumName}">
						</div>
					</c:forEach>
				</c:when>
			</c:choose>

		</div>
	</div>
</body>
</html>
