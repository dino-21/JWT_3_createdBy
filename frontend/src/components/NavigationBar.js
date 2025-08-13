import React from 'react';
import { Nav } from 'react-bootstrap';
import { jwtDecode } from 'jwt-decode';  // JWT 토큰 디코딩을 위한 라이브러리
import { useNavigate } from 'react-router-dom';

const NavigationBar = () => {
  const navigate = useNavigate();   // 페이지 이동 함수 사용
  const token = localStorage.getItem('token'); // 저장된 JWT 토큰 가져오기

  let username = null;

  // 토큰이 존재하면 username 추출
  if (token) {
    try {
      const decoded = jwtDecode(token); // 토큰 디코딩 (payload 추출)
      console.log("디코딩된 JWT:", decoded);
      username = decoded.sub;  // payload에서 사용자 이름(sub) 추출
    } catch (e) {
      console.error('토큰 해독 실패:', e); // 디코딩 실패 시 로그 출력
    }
  }

  // 로그아웃 버튼 클릭 시 호출되는 함수
  const handleLogout = () => {
    localStorage.removeItem('token'); // 토큰 삭제
    alert('로그아웃 되었습니다.');
    navigate('/');  // 홈으로 이동
  };


   // 상단 메뉴 렌더링
  return (
    <Nav activeKey="/home" className="justify-content-end" style={{ padding: '10px' }}>
      <Nav.Item>
        <Nav.Link href="/">홈</Nav.Link>
      </Nav.Item>

      {/* 로그인 상태에 따라 다른 메뉴 출력 */}
      {token ? ( 
        
        // 로그인 상태일 경우
        <>
          <Nav.Item>
            <Nav.Link disabled>{username}님</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link onClick={handleLogout}>로그아웃</Nav.Link>
          </Nav.Item>
        </>
      ) : (
         // 비로그인 상태일 경우
        <>
          <Nav.Item>
            <Nav.Link href="/login">로그인</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link href="/signup">회원가입</Nav.Link>
          </Nav.Item>
        </>
      )}
    </Nav>
  );
};

export default NavigationBar;