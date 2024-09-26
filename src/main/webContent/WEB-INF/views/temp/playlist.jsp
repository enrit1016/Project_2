<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>playlist</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<style>
.song-list {
    list-style-type: none;
    padding: 0;
}
.song-item {
    padding: 10px;
    margin: 5px 0;
    background-color: #f0f0f0;
    cursor: move;
    display: flex; /* Flexbox 사용 */
    justify-content: space-between; /* 항목들을 양쪽으로 배치 */
    align-items: center; /* 수직 정렬 */
}
.play-button {
    margin-left: 10px;
}
</style>
</head>
<body>
    <h1>플레이리스트</h1>
    <form action="${root}temp/addSongFromYouTube" method="POST">
        <label for="query">Search YouTube:</label> <input type="text" id="query" name="query" required> <input type="submit" value="Search and Add">
    </form>

    <ul id="sortable" class="song-list">
        <c:forEach var="song" items="${songs}">
            <li class="song-item" data-id="${song.id}" data-url="${song.url}">
                ${song.title} - ${song.artist}
                <button class="play-button" data-url="${song.url}">재생</button>
            </li>
        </c:forEach>
    </ul>

    <button id="saveOrder">Save Order</button>

    <div id="videoPlayer" style="display:none; margin-top: 20px;">
        <h2>재생 중인 동영상</h2>
        <iframe id="youtubeIframe" width="560" height="315" src="" frameborder="0" allowfullscreen></iframe>
    </div>

    <script>
        $(function() {
            $("#sortable").sortable();
            $("#sortable").disableSelection();

            $("#saveOrder").click(function() {
                var orderedIds = [];
                $("#sortable .song-item").each(function() {
                    orderedIds.push($(this).data("id"));
                });

                $.ajax({
                    type: "POST",
                    url: "${root}temp/updateOrder",
                    data: JSON.stringify({
                        orderedIds: orderedIds
                    }),
                    contentType: "application/json; charset=utf-8",
                    success: function(response) {
                        alert("Playlist order updated successfully!");
                    },
                    error: function(error) {
                        alert("Error updating order!");
                    }
                });
            });

            $(".play-button").click(function(event) {
                event.stopPropagation(); // 리스트 항목 클릭 이벤트 전파 방지
                const songUrl = $(this).data("url");
                playVideo(songUrl);
            });
        });

        function playVideo(url) {
            const videoId = url.split('v=')[1];
            const ampersandPosition = videoId.indexOf('&');
            if (ampersandPosition !== -1) {
                videoId = videoId.substring(0, ampersandPosition);
            }
            document.getElementById('youtubeIframe').src = "https://www.youtube.com/embed/" + videoId + "?autoplay=1";
            document.getElementById('videoPlayer').style.display = 'block';
        }
    </script>
</body>
</html>
