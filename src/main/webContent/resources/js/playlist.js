let player;
let isPlaying = false;
let duration = 0;

let currentPlayOrder = 1;  // 현재 재생 중인 순서 (처음엔 1로 초기화)
const playlistId = 1;

// YouTube Iframe API 로드 후 player 객체 생성
function loadYouTubeIframeAPI() {
	// YouTube API가 로드되었는지 확인
	if (typeof YT === 'undefined' || typeof YT.Player === 'undefined') {
		const tag = document.createElement('script');
		tag.src = "https://www.youtube.com/iframe_api";
		const firstScriptTag = document.getElementsByTagName('script')[0];
		firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
	} else {
		// API가 이미 로드되었으면 플레이어를 초기화
		onYouTubeIframeAPIReady();
	}
}

// YouTube Iframe API 준비 함수
function onYouTubeIframeAPIReady() {
	if (typeof player !== 'undefined') {
		player.destroy();  // 기존 플레이어 제거
	}

	player = new YT.Player('musicPlayer', {
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
}

// 플레이어 준비 후 재생시간 및 이벤트 설정
function onPlayerReady(event) {
	duration = player.getDuration();
	$('#durationTime').text(formatTime(duration));

	// 재생바 및 시간 업데이트
	setInterval(() => {
		if (isPlaying) {
			const currentTime = player.getCurrentTime();
			$('#currentTime').text(formatTime(currentTime));
			$('#seekBar').val((currentTime / duration) * 100);
		}
	}, 1000);

	// 이벤트가 매번 새로 등록되도록 off 후 다시 on
	$('#playPauseBtn').off('click').on('click', function() {
		if (isPlaying) {
			player.pauseVideo();
		} else {
			player.playVideo();
		}
		isPlaying = !isPlaying;
		updatePlayPauseIcon();
	});

	// 재생바 조작 시 비디오 이동
	$('#seekBar').off('input').on('input', function() {
		const seekToTime = (this.value / 100) * duration;
		player.seekTo(seekToTime);
	});

	// 이전 곡으로 넘어가는 로직
	$('#prevBtn').off('click').on('click', function() {
		if (currentPlayOrder > 1) {
			currentPlayOrder--;  // 재생 순서를 이전으로 감소
			loadMusicByOrder(currentPlayOrder);  // 해당 순서의 곡을 로드
		} else {
			alert('첫 번째 곡입니다. 더 이전 곡이 없습니다.');
		}
	});

	// 다음 곡으로 넘어가는 로직
	$('#nextBtn').off('click').on('click', function() {
		currentPlayOrder++;  // 재생 순서를 다음으로 증가
		loadMusicByOrder(currentPlayOrder);  // 해당 순서의 곡을 로드
	});

	// AJAX로 특정 순서의 곡을 로드하는 함수
	function loadMusicByOrder(playOrder) {
    $.ajax({
        url: `/Project_2/musicPlayer/play/${playlistId}/${playOrder}`,
        method: 'GET',
        success: function(music) {
            if (music) {
                // 기존 플레이어를 삭제한 후 새로 생성
                player.destroy();
                player = new YT.Player('musicPlayer', {
                    videoId: music.videoUrl,
                    events: {
                        'onReady': onPlayerReady,
                        'onStateChange': onPlayerStateChange
                    }
                });
                $('#currentSongTitle').text(music.music_Name);
                $('#currentArtist').text(music.artist);
            }
        },
        error: function(err) {
            alert('곡을 가져오는 데 오류가 발생했습니다.');
        }
    });
}


	$('#shuffleBtn').off('click').on('click', function() {
		// 셔플 기능 로직 작성
	});
}

function updatePlayPauseIcon() {
	if (isPlaying) {
		$('#playPauseBtn i').removeClass('bi-play-fill').addClass('bi-pause-fill');
	} else {
		$('#playPauseBtn i').removeClass('bi-pause-fill').addClass('bi-play-fill');
	}
}

// 포맷 시간 함수 (초를 MM:SS 형식으로 변환)
function formatTime(time) {
	const minutes = Math.floor(time / 60);
	const seconds = Math.floor(time % 60);
	return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
}

// 플레이어 상태 변경 이벤트 처리
function onPlayerStateChange(event) {
	if (event.data === YT.PlayerState.PLAYING) {
		isPlaying = true;
		duration = player.getDuration();
		$('#durationTime').text(formatTime(duration));
	} else {
		isPlaying = false;
	}
}

// 페이지가 로드될 때마다 YouTube Iframe API 및 이벤트 재등록
$(document).ready(function() {
	loadYouTubeIframeAPI();
});

// AJAX 완료 시 이벤트 재등록
$(document).on('ajaxComplete', function() {
	loadYouTubeIframeAPI();
});
