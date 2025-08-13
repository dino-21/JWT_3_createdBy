import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom"; // URL 파라미터 및 페이지 이동
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

// 회원 수정 컴포넌트
const UpdateMemberForm = () => {
  // URL의 id 파라미터 추출
  const { id } = useParams();

  // 페이지 이동 함수
  const navigate = useNavigate();

  // 회원 정보 상태 관리
  const [member, setMember] = useState({
    id: "",
    name: "",
    age: "",
    phone: "",
    address: "",
    createdBy: "", // 1 작성자 비교
  });

 // 2 진입 시 토큰 확인 + 데이터 조회 시 토큰 헤더 포함 + 작성자 본인 확인
 useEffect(() => {
  const token = localStorage.getItem("token");          
  const username = localStorage.getItem("username");   

  if (!token) {                                        
    alert("로그인이 필요합니다.");
    navigate("/");
    return;
  }

  axios
    .get(`http://localhost:8080/api/members/${id}`, {
      headers: { Authorization: `Bearer ${token}` },    // 토큰 포함
    })
    .then((res) => {
      setMember(res.data);

      // 작성자 본인만 접근 허용
      if (res.data?.createdBy && res.data.createdBy !== username) {
        alert("작성자만 수정할 수 있습니다.");
        navigate("/");
      }
    })
    .catch((err) => {
      if (err.response?.status === 403) {
        alert("작성자만 수정할 수 있습니다.");       
        navigate("/");
      } else {
        alert("회원 조회 실패");
      }
      console.error(err);
    });
}, [id, navigate]);



  // 입력값 변경 핸들링
  const handleChange = (e) => {
    const { name, value } = e.target;
    setMember((prev) => ({ ...prev, [name]: value })); // 기존 값 유지하며 변경된 필드 업데이트
  };

  // 폼 제출 시 실행
  const handleSubmit = (e) => {
    e.preventDefault();

      // 3 제출 시에도 토큰 재확인(이중 안전장치)
      const token = localStorage.getItem("token");
      if (!token) {
        alert("로그인이 필요합니다.");
        return;
      }
 
    // 이름 유효성 검사
    const nameRegex = /^[가-힣]{3,5}$/;
    if (!nameRegex.test(member.name.trim())) {
      alert("이름은 한글 3~5글자로 입력하세요.");
      return;
    }

    // 나이 유효성 검사
    const age = Number(member.age);
    if (isNaN(age) || age < 0 || age > 100) {
      alert("나이는 0부터 100 사이의 숫자로 입력하세요.");
      return;
    }

    // 전화번호 유효성 검사
    const phoneRegex = /^010-\d{4}-\d{4}$/;
    if (!phoneRegex.test(member.phone.trim())) {
      alert("전화번호는 010-0000-0000 형식으로 입력하세요.");
      return;
    }

    // 주소 비어있는지 확인
    if (!member.address.trim()) {
      alert("주소를 입력하세요.");
      return;
    }

    // 4 모든 유효성 통과 시 PUT 요청(수정)
    axios
        .put(`http://localhost:8080/api/members/${id}`, member, {
          headers: { Authorization: `Bearer ${token}` },     // 토큰 포함
        })
        .then(() => {
          alert("수정 완료!");
          navigate("/"); // 메인 페이지로 이동
        })
        .catch((err) => {
          if (err.response?.status === 403) {               // 권한 에러 처리
            alert("작성자만 수정할 수 있습니다.");
          } else {
            alert("수정 실패");
          }
          console.error(err);
        });

  };

  return (
    <Container className="mt-5">
      <h2 className="mb-4"> 회원 정보 수정</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Control type="hidden" name="id" value={member.id} />

        <Form.Group as={Row} className="mb-3" controlId="formName">
          <Form.Label column sm={2}>
            Name
          </Form.Label>
          <Col sm={10}>
            <Form.Control
              type="text"
              name="name"
              value={member.name}
              onChange={handleChange}
              required
            />
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-3" controlId="formAge">
          <Form.Label column sm={2}>
            Age
          </Form.Label>
          <Col sm={10}>
            <Form.Control
              type="number"
              name="age"
              value={member.age}
              onChange={handleChange}
              required
            />
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-3" controlId="formPhone">
          <Form.Label column sm={2}>
            Phone
          </Form.Label>
          <Col sm={10}>
            <Form.Control
              type="text"
              name="phone"
              value={member.phone}
              onChange={handleChange}
              required
            />
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-3" controlId="formAddress">
          <Form.Label column sm={2}>
            Address
          </Form.Label>
          <Col sm={10}>
            <Form.Control
              type="text"
              name="address"
              value={member.address}
              onChange={handleChange}
              required
            />
          </Col>
        </Form.Group>

        <div className="text-end">
          <Button variant="success" type="submit">
            저장
          </Button>
        </div>
      </Form>
    </Container>
  );
};

export default UpdateMemberForm;
