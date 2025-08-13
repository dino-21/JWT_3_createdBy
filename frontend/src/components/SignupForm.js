import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function SignupForm() {
  const [username, setUsername] = useState('');//입력값 상태 관리
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); // 페이지 이동을 위한 훅

  //회원가입 요청 처리 함수
  const handleSignup = async (e) => {
    e.preventDefault(); // 폼 제출 시 기본 동작(페이지 새로고침) 방지

    //아이디 공백 제거 + 소문자 통일
    const cleanUsername = username.trim().toLowerCase();

  
    try {  
      //백엔드로 회원가입 요청 전송 (POST /api/auth/signup)
      await axios.post('http://localhost:8080/api/auth/signup', {
        username: cleanUsername, // 수정된 값 전송
        password,
      });

      alert('회원가입 성공! 로그인 페이지로 이동합니다.');

      // 회원가입 성공 시 로그인 페이지로 이동
      navigate('/login');
    } catch (error) {
      //오류 발생 시 사용자에게 알림
      alert('회원가입 실패: ' + error.response?.data || '오류 발생');
    }
  };

  //화면 렌더링 (회원가입 입력 폼)
  return (
    <Container style={{ marginTop: '100px' }}>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card>
            <Card.Body>
              <h3 className="text-center mb-4">회원가입</h3>

               {/* 회원가입 입력 폼 */}
              <Form onSubmit={handleSignup}>
                  {/* 아이디 입력 */}
                <Form.Group className="mb-3">
                  <Form.Label>아이디</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="아이디를 입력하세요"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </Form.Group>

                {/* 비밀번호 입력 */}
                <Form.Group className="mb-3">
                  <Form.Label>비밀번호</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="비밀번호를 입력하세요"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </Form.Group>

                {/* 회원가입 버튼 */}
                <div className="d-grid">
                  <Button variant="success" type="submit">
                    회원가입
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default SignupForm;