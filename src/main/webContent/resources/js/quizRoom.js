document.addEventListener('DOMContentLoaded', () => {
	// 게임 시작 버튼 이벤트
	document.getElementById('start-quiz-btn').addEventListener('click', () => {
		fetch(`${root}/quiz/rooms/start`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ roomId: roomId }) // 방 ID 전달
		})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					alert('퀴즈가 시작되었습니다!');
					// 퀴즈 문제 표시
					document.getElementById('quiz-text').textContent = data.quizQuestion;
				} else {
					alert('퀴즈 시작에 실패했습니다.');
				}
			})
			.catch(error => {
				console.error('퀴즈 시작 오류:', error);
				alert('퀴즈 시작 중 오류가 발생했습니다.');
			});
	});

	// 로비로 이동 버튼 이벤트
	document.getElementById('go-lobby-btn').addEventListener('click', () => {
		window.location.href = `${root}/quiz/lobby`;
	});

	// 정답 제출 버튼 이벤트
	document.getElementById('submit-answer-btn').addEventListener('click', () => {
		const answer = document.getElementById('quiz-answer').value.trim();

		if (!answer) {
			alert('정답을 입력해주세요.');
			return;
		}

		fetch(`${root}/quiz/rooms/submitAnswer`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ roomId: roomId, answer: answer }) // 방 ID와 정답 전달
		})
			.then(response => response.json())
			.then(data => {
				if (data.correct) {
					alert('정답입니다!');
				} else {
					alert('오답입니다.');
				}
			})
			.catch(error => {
				console.error('정답 제출 오류:', error);
			});
	});

	// 채팅 전송 버튼 이벤트
	document.getElementById('send-chat-btn').addEventListener('click', () => {
		const message = document.getElementById('chat-input').value.trim();

		if (!message) {
			alert('메시지를 입력해주세요.');
			return;
		}

		fetch(`${root}/quiz/rooms/sendMessage`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ roomId: roomId, message: message }) // 방 ID와 메시지 전달
		})
			.then(response => response.json())
			.then(data => {
				if (data.success) {
					document.getElementById('chat-messages').innerHTML += `<p>${data.message}</p>`;
					document.getElementById('chat-input').value = '';
				} else {
					alert('메시지 전송에 실패했습니다.');
				}
			})
			.catch(error => {
				console.error('메시지 전송 오류:', error);
			});
	});
});
