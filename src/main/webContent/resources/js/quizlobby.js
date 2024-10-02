document.addEventListener('DOMContentLoaded', () => {
	// 방 생성 버튼 클릭 이벤트
	const createRoomBtn = document.getElementById('create-room-btn');
	if (createRoomBtn) {
		createRoomBtn.addEventListener('click', () => {
			const roomName = prompt("생성할 방의 이름을 입력하세요:", "새로운 퀴즈방");
			if (!roomName) {
				alert('방 이름을 입력해야 합니다.');
				return;
			}

			let roomPassword = prompt("방의 비밀번호를 입력하세요 (빈칸으로 두면 비밀번호 없이 생성됩니다):");
			if (roomPassword === null) {
				roomPassword = "";
			}

			fetch(`${root}/quiz/rooms/create`, {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({
					quizRoomName: roomName,
					quizRoomPassword: roomPassword
				})
			})
				.then(response => {
					if (!response.ok) {
						throw new Error(`HTTP error! Status: ${response.status}`);
					}
					return response.json();
				})
				.then(data => {
					if (data.success) {
						alert('방이 생성되었습니다!');
						window.location.href = `${root}/quiz/rooms/${data.roomId}`;
					} else {
						alert('방 생성에 실패했습니다: ' + (data.message || '알 수 없는 오류'));
					}
				})
				.catch(error => {
					console.error('방 생성 오류:', error);
					alert('방 생성 중 오류가 발생했습니다: ' + error.message);
				});
		});
	}

	// 동적으로 생성된 방 참여 버튼에 클릭 이벤트 추가
	document.querySelectorAll('.join-room-btn').forEach(button => {
		button.addEventListener('click', () => {
			const roomId = button.getAttribute('data-room-id');
			const requiresPassword = button.getAttribute('data-requires-password') === 'true';

			if (!roomId) {
				alert('방 ID를 찾을 수 없습니다.');
				return;
			}

			// 비밀번호가 필요한 방일 경우, 비밀번호 입력 요청
			let roomPassword = '';
			if (requiresPassword) {
				roomPassword = prompt("방의 비밀번호를 입력하세요:");
				if (roomPassword === null) {
					alert('방 참여가 취소되었습니다.');
					return;
				}
			}

			// 방 참여 요청
			joinRoom(roomId, roomPassword);
		});
	});
});

// 방 참여 함수
function joinRoom(roomId, roomPassword = '') {
	console.log(`Joining room with ID: ${roomId}`); // 디버그용 로그
	fetch(`${root}/quiz/rooms/join`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ roomId: roomId, roomPassword: roomPassword }) // 방 참여 시 roomId와 비밀번호 전송
	})
		.then(response => {
			if (!response.ok) {
				throw new Error(`HTTP error! Status: ${response.status}`);
			}
			return response.json();
		})
		.then(data => {
			if (data.success) {
				alert('방에 참여하였습니다!');
				window.location.href = `${root}/quiz/rooms/${roomId}`;
			} else {
				alert('방 참여에 실패했습니다: ' + (data.message || '알 수 없는 오류'));
			}
		})
		.catch(error => {
			console.error('방 참여 오류:', error);
			alert('방 참여 중 오류가 발생했습니다: ' + error.message);
		});
}
