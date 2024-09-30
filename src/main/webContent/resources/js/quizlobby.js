document.addEventListener('DOMContentLoaded', () => {
    // 방 생성 버튼 클릭 이벤트
    document.getElementById('create-room-btn').addEventListener('click', () => {
        fetch(`${root}/quiz/createRoom`, { method: 'POST' })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('방이 생성되었습니다!');
                    // 생성된 방으로 이동
                    window.location.href = `${root}/quiz/room/${data.roomId}`;
                } else {
                    alert('방 생성에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('방 생성 오류:', error);
                alert('방 생성 중 오류가 발생했습니다.');
            });
    });
});

// 방 참여 함수
function joinRoom(roomCode) {
    window.location.href = `${root}/quiz/room/${roomCode}`;
}
