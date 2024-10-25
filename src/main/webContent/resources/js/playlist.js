let player;
let isPlaying = false;
let duration = 0;
let currentPlayOrder = 1;  // 현재 재생 중인 순서 (처음엔 1로 초기화)


// YouTube Iframe API 로드 후 player 객체 생성
function loadYouTubeIframeAPI() {
	if (typeof YT === 'undefined' || typeof YT.Player === 'undefined') {
		const tag = document.createElement('script');
		tag.src = "https://www.youtube.com/iframe_api";
		const firstScriptTag = document.getElementsByTagName('script')[0];
		firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
	} else {
		onYouTubeIframeAPIReady();
	}
}

// YouTube Iframe API 준비 함수
function onYouTubeIframeAPIReady() {
	if (player) {
		player.destroy();  // 기존 플레이어 제거
	}

	player = new YT.Player('musicPlayer', {
		events: {
			'onReady': onPlayerReady,
			'onStateChange': onPlayerStateChange
		}
	});
}

// 플레이어 준비 후 재생 시간 및 이벤트 설정
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

	// play/pause 버튼 이벤트 설정
	$('#playPauseBtn').off('click').on('click', togglePlayPause);

	// 재생바 조작 시 비디오 이동
	$('#seekBar').off('input').on('input', function() {
		const seekToTime = (this.value / 100) * duration;
		player.seekTo(seekToTime);
	});

	// 곡 다음/이전 버튼 설정
	$('#nextBtn').off('click').on('click', playNextSong);
	$('#prevBtn').off('click').on('click', playPreviousSong);

	// 셔플 버튼 설정
	$('#shuffleBtn').off('click').on('click', shufflePlaylist);
}

// 곡 재생/일시정지 토글
function togglePlayPause() {
	if (isPlaying) {
		player.pauseVideo();
	} else {
		player.playVideo();
	}
	isPlaying = !isPlaying;
	updatePlayPauseIcon();
}

// 곡 순서 변경
function playNextSong() {
	currentPlayOrder++;
	loadMusicByOrder(currentPlayOrder);
}

function playPreviousSong() {
	if (currentPlayOrder > 1) {
		currentPlayOrder--;
		loadMusicByOrder(currentPlayOrder);
	} else {
		Swal.fire({
			icon: 'error',
			title: '이전 곡이 없어요',
			text: '',
			background: '#3A3A3A',
			color: '#fff',
			confirmButtonColor: '#1db954',
			confirmButtonText: '확인'
		});
	}
}

function getPlaylistIdFromURL() {
	const urlParams = new URLSearchParams(window.location.search);
	return urlParams.get('playlistId');
}

let playlistId = getPlaylistIdFromURL();

// AJAX로 특정 순서의 곡을 로드하는 함수
function loadMusicByOrder(playOrder) {
	$.ajax({
		url: `/Project_2/musicPlayer/play/${playlistId}/${playOrder}`,  // URL에서 추출한 playlistId 사용
		method: 'GET',
		dataType: 'json',
		success: function(music) {
			if (music) {
				console.log(music);

				// YouTube iframe의 src를 동적으로 변경
				$('#musicPlayer').attr('src', `https://www.youtube.com/embed/${music.videoUrl}?enablejsapi=1&autoplay=1`);

				// 현재 곡 제목과 아티스트 정보 업데이트
				$('#currentSongTitle').text(music.music_Name);
				$('#currentArtist').text(music.artist);

				// 플레이어 상태 초기화
				player.stopVideo();
				player.loadVideoById(music.videoUrl);  // 새로운 비디오 로드
				player.playVideo();
			} else {
				Swal.fire({
					icon: 'error',
					title: '해당 순서의 노래가 없습니다.',
					background: '#3A3A3A',
					color: '#fff',
					confirmButtonColor: '#1db954',
					confirmButtonText: '확인'
				});
			}
		},
		error: function(err) {
			Swal.fire({
				icon: 'error',
				title: '노래를 가져오는데 문제가 발생했습니다',
				background: '#3A3A3A',
				color: '#fff',
				confirmButtonColor: '#1db954',
				confirmButtonText: '확인'
			});
			console.log(err);
		}
	});
}


// 볼륨 조절 함수
function changeVolume(volume) {
	if (player && typeof player.setVolume === 'function') {
		player.setVolume(volume);
	}
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

// play/pause 아이콘 업데이트
function updatePlayPauseIcon() {
	const icon = $('#playPauseBtn i');
	if (isPlaying) {
		icon.removeClass('bi-play-fill').addClass('bi-pause-fill');
	} else {
		icon.removeClass('bi-pause-fill').addClass('bi-play-fill');
	}
}

// 포맷 시간 함수 (초를 MM:SS 형식으로 변환)
function formatTime(time) {
	const minutes = Math.floor(time / 60);
	const seconds = Math.floor(time % 60);
	return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
}

// 플레이리스트 셔플
function shufflePlaylist() {
	// 셔플 기능 구현 (예: 랜덤으로 순서 바꾸기)
	console.log("셔플 기능 추가 필요");
}

// 페이지가 로드될 때 YouTube Iframe API 로드
$(document).ready(function() {
	loadYouTubeIframeAPI();
});

// 곡 삭제 기능
function deleteSong(musicId) {
	if (confirm("정말 이 곡을 삭제하시겠습니까?")) {
		$.ajax({
			url: `/Project_2/musicPlayer/delete/${musicId}`,
			method: 'POST',
			success: function() {
				Swal.fire({
					icon: 'success',
					title: '곡을 삭제했어요',
					text: '',
					background: '#3A3A3A',
					color: '#fff',
					confirmButtonColor: '#1db954',
					confirmButtonText: '확인'
				});
				location.reload();
			},
			error: function(err) {
				Swal.fire({
					icon: 'error',
					title: '곡을 삭제하는 중 오류가 발생했어요',
					text: '',
					background: '#3A3A3A',
					color: '#fff',
					confirmButtonColor: '#1db954',
					confirmButtonText: '확인'
				});;
				console.log(err);
			}
		});
	}
}

// Sortable 설정 (드래그앤드롭 순서 변경)
let sortable = new Sortable(document.getElementById('playlist'), {
	handle: '.drag-handle',
	animation: 150,
	onEnd: function(evt) {
		let order = [];
		$('#playlist tr').each(function(index, element) {
			order.push($(element).data('id'));
		});

		// AJAX로 새로운 순서를 서버에 전송
		$.ajax({
			url: '/Project_2/musicPlayer/updateOrder',
			method: 'POST',
			data: { order: order },
			success: function() {

			},
			error: function(err) {
				Swal.fire({
					icon: 'error',
					title: '순서 업데이트 중 오류가 발생했어요',
					text: '',
					background: '#3A3A3A',
					color: '#fff',
					confirmButtonColor: '#1db954',
					confirmButtonText: '확인'
				});
				console.log(err);
			}
		});
	}
});


function loadVideo(videoUrl, musicName, playOrder) {
	// YouTube iframe src를 동적으로 변경
	$('#musicPlayer').attr('src', `https://www.youtube.com/embed/${videoUrl}?enablejsapi=1&autoplay=1`);

	// 현재 곡 제목 업데이트
	$('#currentSongTitle').text(musicName);

	// currentPlayOrder 업데이트 (현재 재생 중인 곡 순서)
	currentPlayOrder = playOrder + 1;
}
