<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>gameEffect01</title>

<link rel="stylesheet" href="${root }/css/game.css">
<link rel="stylesheet" href="${root }/css/music.css">
<%-- <link rel="stylesheet" href="${root }/css/searchgame.css">
<link rel="stylesheet" href="${root }/css/memory.css"> --%>
<link rel="stylesheet" href="${root }/css/style.css">
</head>

<body>
	<div class="cursor">
		<img src="${root }/img/game_cursor_icon11.png" alt="마우스">
	</div>

	<header id="header">
		<h1>DuckMusicQuiz</h1>
		<div class="time"></div>
	</header>
	<!-- // header -->


	<main id="main">
		<div class="icon_box">
			<div class="icon i1">
				<img src="/resources/img/game_folder_icon11.png" alt="뮤직"> <span>Music</span>
			</div>
			<div class="icon i2">
				<img src="./../img/game_folder_icon12.png" alt="2"> <span>Search</span>
			</div>
			<div class="icon i3">
				<img src="./../img/game_folder_icon12.png" alt="3"> <span>1</span>
			</div>
			<div class="icon i4">
				<img src="./../img/game_folder_icon12.png" alt="4"> <span>2</span>
			</div>
			<div class="icon i5">
				<img src="./../img/game_folder_icon12.png" alt="5"> <span>3</span>
			</div>
		</div>
	</main>
	<!-- // main -->

	<footer id="footer">
		<div class="agent"></div>
	</footer>
	<!-- // footer -->


	<div class="game__box">
		<!-- music player -->
		<div class="music__wrap">
			<div class="music__inner">
				<div class="music__header">
					<div>***</div>
					<h2>Music Player</h2>
					<div>+++</div>
				</div>
				<div class="music__contents">
					<div class="music__view">
						<div class="img">
							<img src="" alt="">
						</div>
						<div class="title">
							<h3>A Year Ago</h3>
							<p>NETFFEX</p>
						</div>
						<div class="volume">
							<input type="range" id="volume-control" min="0.1" max="10">
						</div>
					</div>
					<div class="music__control">
						<div class="progress">
							<div class="bar">
								<audio id="main-audio" src=""></audio>
							</div>
							<div class="timer">
								<span class="current">0:00</span> <span class="duration">4:00</span>
							</div>
						</div>
						<div class="control">
							<i title="전체 반복" class="repeat" id="control-repeat"></i>
							<!-- <i title="한곡 반복" class="repeat_one"></i>
                            <i title="랜덤 반복" class="shuffle"></i> -->
							<i title="이전곡 재생" class="prev" id="control-prev"></i>
							<div class="center">
								<i title="재생" class="play" id="control-play"></i>
								<!-- <i title="정지" class="stop"></i> -->
							</div>
							<i title="다음곡 재생" class="next" id="control-next"></i> <i
								title="재생 목록" class="list" id="control-list"></i>
						</div>
					</div>
				</div>
				<div class="music__footer">
					<div class="music__list">
						<h3>
							<span class="list"></span>뮤직 리스트<a href="#" class="close"></a>
						</h3>
						<ul>
							<!-- <li>
                                <strong>제목</strong>
                                <em>아티스트</em>
                                <span>재생시간</span>
                            </li> -->
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- //music player -->



	</div>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	<script src="../assets/js/gsap.min.js"></script>
	<script src="../assets/js/music.js"></script>
	<script src="../assets/js/searchgame.js"></script>
	<script src="../assets/js/memory.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.11.0/gsap.min.js"></script>
	<script>
        $(".i1").draggable({});
        $(".i2").draggable({});
        $(".i3").draggable({});
        $(".i4").draggable({});
        $(".i5").draggable({});
        $(".i6").draggable({});
        $(".music__wrap").draggable();
        $(".search__wrap").draggable();
        $(".memory__wrap").draggable();

        const icon = document.querySelector(".icon");
        const i1 = document.querySelector(".i1");
        const i2 = document.querySelector(".i2");
        const i3 = document.querySelector(".i3");
        const i4 = document.querySelector(".i4");
        const i5 = document.querySelector(".i5");
        const i6 = document.querySelector(".i6");
        const cursor = document.querySelector(".cursor");
        const musicBox = document.querySelector(".music__wrap");
        const searchGame = document.querySelector(".search__wrap");
        const memoryGame = document.querySelector(".memory__wrap");
        const codeSite = document.querySelector(".icon_iframe");
        const codeExit = document.querySelector(".code_close");

        window.addEventListener("mousemove", e => {
            gsap.to(cursor, { duration: 0, left: e.pageX - 2, top: e.pageY - 5 });
        })

        i1.addEventListener("click", () => {
            if (i1.classList.contains("active")) {
                $(".i1 img").attr("src", "../assets/img/game_folder_icon11.png");
                i1.classList.remove("active");
                musicBox.classList.remove("show");
            } else {
                $(".i1 img").attr("src", "../assets/img/game_folder_over11.png");
                i1.classList.add("active");
                musicBox.classList.add("show");
            }
        })

        i2.addEventListener("click", () => {
            if (i2.classList.contains("active")) {
                $(".i2 img").attr("src", "../assets/img/game_folder_icon12.png");
                i2.classList.remove("active");
                searchGame.classList.remove("show");
            } else {
                $(".i2 img").attr("src", "../assets/img/game_folder_over12.png");
                i2.classList.add("active");
                searchGame.classList.add("show");
            }
        })

        i3.addEventListener("click", () => {
            if (i3.classList.contains("active")) {
                $(".i3 img").attr("src", "../assets/img/game_folder_icon12.png");
                i3.classList.remove("active");
                memoryGame.classList.remove("show");
            } else {
                $(".i3 img").attr("src", "../assets/img/game_folder_over12.png");
                i3.classList.add("active");
                memoryGame.classList.add("show");
            }
        })

        i4.addEventListener("click", () => {
            if (i4.classList.contains("active")) {
                $(".i4 img").attr("src", "../assets/img/game_folder_icon12.png");
                i4.classList.remove("active");
                i4.classList.remove("show");
            } else {
                $(".i4 img").attr("src", "../assets/img/game_folder_over12.png");
                i4.classList.add("active");
                i4.classList.add("show");
            }
        })

        i5.addEventListener("click", () => {
            if (i5.classList.contains("active")) {
                $(".i5 img").attr("src", "../assets/img/game_folder_icon12.png");
                i5.classList.remove("active");
                i5.classList.remove("show");
            } else {
                $(".i5 img").attr("src", "../assets/img/game_folder_over12.png");
                i5.classList.add("active");
                i5.classList.add("show");
            }
        })

        i6.addEventListener("click", () => {
            // $(".i6 img").attr("src", "../assets/img/game_folder_over16.png");
            // codeSite.classList.add("show");

            if (i6.classList.contains("show")) {
                $(".i6 img").attr("src", "../assets/img/game_folder_icon16.png");
                codeSite.classList.remove("show");
                i6.classList.remove("show");
            } else {
                $(".i6 img").attr("src", "../assets/img/game_folder_over16.png");
                codeSite.classList.add("show");
                i6.classList.add("show");
            }
        })

        codeExit.addEventListener("click", () => {
            codeSite.classList.remove("show");
            $(".i6 img").attr("src", "../assets/img/game_folder_icon16.png");
        })

        // 볼륨 조절
        const audio = document.getElementById('main-audio');
        const audioVolume = document.getElementById('volume-control');
        audioVolume.addEventListener("change", function (e) {
            audio.volume = this.value / 10;
        });


        // 외부영역 클릭 시 팝업 닫기
        // $(document).mouseup(function (e){
        // 	var LayerPopup = $(".icon_iframe");
        // 	if(LayerPopup.has(e.target).length === 0){
        // 		LayerPopup.removeClass("show");
        //         $(".i6 img").attr("src", "../assets/img/game_folder_icon16.png");
        // 	}
        // });

        //시계
        function printTime() {
            //지금 시간 넣기기
            const clock = document.querySelector(".time");
            const now = new Date();

            //한자리 숫자 앞에다 0을 넣어주기 위한 변수
            let nowHours = now.getHours();
            let nowMins = now.getMinutes();
            let nowSecs = now.getSeconds();
            if (nowHours > 12) {
                nowHours = `${nowHours - 12}`
            } else if (nowHours >= 0 && nowHours <= 9) {
                nowHours = `0${nowHours}`
            };
            if (nowMins < 10) nowMins = `0${nowMins}`;
            if (nowSecs < 10) nowSecs = `0${nowSecs}`;


            let nowMonth = now.getMonth() + 1;
            let nowDate = now.getDate();
            if (nowMonth < 10) nowMonth = `0${nowMonth}`
            if (nowDate < 10) nowDate = `0${nowDate}`

            //데이터에서 연도만 가져오기
            const nowTime = now.getFullYear() + ". " + nowMonth + ". " + nowDate + "| " + nowHours + "시 " + nowMins + "분 " + nowSecs + "초입니다."

            clock.innerText = nowTime;
            setTimeout("printTime()", 1000);
        }


        function printAgent() {
            const agent = document.querySelector(".agent");
            const os = navigator.userAgent.toLocaleLowerCase();

            agent.innerText = os;

            if (os.indexOf("window") >= 0) {
                agent.innerText = "현재 윈도우를 사용하고 있으며, 화면 크기는 " + screen.width + " * " + screen.height + " 입니다.";
                document.querySelector("body").classList.add("window");
            } else if (os.indexOf("macintosh") >= 0) {
                agent.innerText = "현재 맥을 사용하고 있으며, 화면 크기는 " + screen.width + " * " + screen.height + " 입니다.";
                document.querySelector("body").classList.add("mac");
            } else if (os.indexOf("iphone") >= 0) {
                agent.innerText = "현재 아이폰을 사용하고 있으며, 화면 크기는 " + screen.width + " * " + screen.height + " 입니다.";
                document.querySelector("body").classList.add("iphone");
            } else if (os.indexOf("android") >= 0) {
                agent.innerText = "현재 안드로이폰을 사용하고 있으며, 화면 크기는 " + screen.width + " * " + screen.height + " 입니다.";
                document.querySelector("body").classList.add("android");
            }

        }

        window.onload = function () {
            printTime();
            printAgent();
        }

    </script>


</body>

</html>