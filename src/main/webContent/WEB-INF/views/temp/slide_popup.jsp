<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">



   <!-- SweetAlert 다크 테마 및 스크립트 추가 -->
   <link
           href="https://cdn.jsdelivr.net/npm/@sweetalert2/theme-dark@4/dark.css"
           rel="stylesheet">
   <script
           src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>



   <title>결제 옵션</title>
   <link rel="stylesheet" href="${root}css/popup.css">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>



   <!-- 폰트 통일 적용 -->
   <style>
      body {
         font-family: 'Arial', sans-serif; /* 모든 텍스트에 Arial 폰트 적용 */
         color: white;
      }

      h1, h2, b, h3 {
         font-family: 'Arial', sans-serif; /* 폰트 통일 */
      }

      /* 모달 창 내의 버튼들에 Arial 폰트 적용 */
      .btn-pay, .mocean-modal-close {
         font-family: 'Arial', sans-serif; /* 폰트 통일 */
         font-weight: bold; /* 가독성을 위해 글씨를 굵게 설정 */
         background-color: #ff4f6c; /* 버튼 배경색 */
         color: white; /* 글자 색상 */
         padding: 10px 20px; /* 버튼 패딩 */
         border: none; /* 테두리 제거 */
         border-radius: 5px; /* 둥근 모서리 */
         cursor: pointer; /* 클릭 가능한 버튼 스타일 */
         font-size: larger;
      }

      .btn-pay:hover, .mocean-modal-close:hover {
         background-color: #ff6b81; /* 호버 시 버튼 색상 변경 */
      }

      .btn-group {
         display: flex;
         justify-content: center;
         gap: 20px;
         margin-top: 10px;
      }

      .mocean-modal-button {
         position: relative;
         overflow: hidden;
         padding: 20px;
         background-color: #1c1c1c;
         color: white;
         border-radius: 10px;
         text-align: left;
         box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
         transition: transform 0.2s ease-in-out;
         width: 300px;

      }

      .mocean-modal-button:hover {
         transform: scale(1.05);
      }

      .button-header {
         color: white;
         font-size: 14px;
         font-weight: bold;
         padding: 5px 10px;
         border-radius: 20px;
         margin-bottom: 10px;
      }
      .monthly-btn .button-header{
         background-color: #ffd2d7;
      }
      .yearly-btn .button-header {
         background-color: #ffc862; /* 같은 색상을 유지 */
      }
      .mp3-btn .button-header{
         background-color: #a1be52;
      }
      .trial-button {
         background-color: #ff4f6c;
         color: white;
         text-align: center;
         padding: 10px;
         border-radius: 20px;
         font-weight: bold;
         font-size: 16px;
         margin-top: 10px;
      }
      .monthly-btn .trial-button{
         background-color: #ffd2d7;
      }
      .yearly-btn .trial-button {
         background-color: #ffc862; /* 같은 색상을 유지 */
      }
      .mp3-btn .trial-button{
         background-color: #a1be52;
      }
      .benefits-list {
         list-style: none;
         padding: 0;
         margin: 10px 0;
         color: white;
      }

      .benefits-list li {
         margin-bottom: 8px;
      }


   </style>
</head>

<body>



<header>
   <!-- top_menu.jsp 포함 -->
   <jsp:include page="/WEB-INF/views/include/top_menu.jsp" />
</header>




<header>
   <h1>오리둥둥이와 함께 음악을 시작해보세요.</h1>
</header>





<h2>음악 스트리밍 구독</h2>
<b>3000만 곡의 무제한 음악과 유튜브 커버곡까지 이용할 수 있어요.</b>

<div class="btn-group">
   <!-- 월간결제 버튼 -->
   <button class="mocean-modal-button monthly-btn"
           data-mocean-type="slide-in-bottom"
           data-mocean-out-type="slide-out-top">

      <div class="button-header">1개월 동안 무료</div>
      <div class="button-content">
         <h3>월간 결제</h3>
         <p>₩9,900원 결제</p>
      </div>
      <ul class="benefits-list">
         <li>언제든 해지 가능</li>
      </ul>
      <div class="trial-button"
      >1개월 무료 체험</div>
   </button>

   <!-- 연간결제 버튼 -->
   <button class="mocean-modal-button yearly-btn"
           data-mocean-type="slide-in-bottom" data-target="#mocean-modal-2">
      <div class="button-header">1개월 동안 무료</div>
      <div class="button-content">
         <h3>연간 결제</h3>
         <p>99,900원 결제</p>
      </div>
      <ul class="benefits-list">
         <li>언제든 해지 가능</li>
      </ul>
      <div class="trial-button">1개월 무료 체험</div>
   </button>

   <button class="mocean-modal-button mp3-btn"
           data-mocean-type="slide-in-bottom"
           data-mocean-out-type="slide-out-top">

      <div class="button-header">1개월 동안 무료</div>
      <div class="button-content">
         <h3>MP3 다운</h3>
         <p>₩5,900원 결제</p>
      </div>
      <ul class="benefits-list">
         <li>언제든 해지 가능</li>
      </ul>
      <div class="trial-button"
      >1개월 무료 체험</div>
   </button>

</div>





<!-- 월간 결제 모달 -->
<div class="mocean-wrap mocean-modal-wrap" id="mocean-modal-wrap">
   <div class="mocean-content mocean-modal" id="mocean-modal">
      <h2>선택한 구독 상품</h2>
      <div class="mocean-modal-content">
         <h3>월간결제</h3>
         <p>9,900원/월</p>
         <button type="button" id="mocean-modal-close-mainBtn" onClick="location.href='${root}main'">메인으로</button>
        
         <button class="mocean-modal-close">닫기</button>
      </div>
   </div>
</div>









<!-- 연간 결제 모달 -->
<div class="mocean-wrap mocean-modal-wrap" id="mocean-modal-2">
   <div class="mocean-content mocean-modal">
      <h2>선택한 구독 상품</h2>
      <div class="mocean-modal-content">
         <h3>연간결제</h3>
         <p>99,900원/년</p>
         <button type="button" class="mocean-modal-close mainBtn" onClick="location.href='${root}main'">메인으로</button>
         
         <button class="mocean-modal-close">닫기</button>
      </div>
   </div>
</div>

<script src="${root}js/popup.js"></script>








<script>


   // SweetAlert로 알림창 띄우기
   <% if (request.getAttribute("successMessage") != null) { %>
   Swal.fire({
      icon: 'success',
      title: '결제 완료',
      text: '<%= request.getAttribute("successMessage") %>',
      background: '#3A3A3A',
      color: '#fff',
      confirmButtonColor: '#1db954',
      confirmButtonText: '확인'
   }).then((result) => {
      if (result.isConfirmed) {
         window.close(); // 팝업 창 닫기
      }
   });
   <% } %>

   // 카카오페이 결제 팝업창 연결
   $(function() {
      // 결제 팝업창 열기
      function openKakaoPay(totalPrice) {
         let data = {
            name: '스트리밍',
            totalPrice: totalPrice
         };


         $.ajax({
            type: 'post',
            url: '${root}temp/kakao/pay/ready',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function(response) {
               window.open(response.next_redirect_pc_url, '카카오페이', 'width=500px, height=950px, left=670px, top=400px');

            }
         });




      }

      $(".trial-button, .btn-pay, .annual").click(function(e) {
         const totalPrice = $(this).hasClass('annual') ? 99900 : 9900;
         openKakaoPay(totalPrice);
      });
   });


</script>
</body>
</html>