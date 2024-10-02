document.addEventListener('DOMContentLoaded', () => {
	// 방 생성 버튼 클릭 이벤트
	const createRoomBtn = document.getElementById('create-room-btn');
	if (createRoomBtn) {
		createRoomBtn.addEventListener('click', () => {
			// 방 이름과 비밀번호를 사용자로부터 입력받음
			const roomName = prompt("생성할 방의 이름을 입력하세요:", "새로운 퀴즈방");
			const roomPassword = prompt("방의 비밀번호를 입력하세요:", "1234");

			if (roomName && roomPassword) {
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
							// 생성된 방으로 이동
							window.location.href = `${root}/quiz/rooms/${data.roomId}`;
						} else {
							alert('방 생성에 실패했습니다: ' + data.message);
						}
					})
					.catch(error => {
						console.error('방 생성 오류:', error);
						alert('방 생성 중 오류가 발생했습니다.');
					});
			} else {
				alert('방 이름과 비밀번호를 입력해야 합니다.');
			}
		});
	}
});

// 방 참여 함수
function joinRoom(roomId) {
	window.location.href = `${root}/quiz/rooms/${roomId}`;
}
