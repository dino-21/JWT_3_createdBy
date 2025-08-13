import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link, useNavigate } from "react-router-dom";
import { Table, Button, Spinner } from "react-bootstrap";

function MemberView() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [member, setMember] = useState(null);
  const [loading, setLoading] = useState(true);

  const loginUser = localStorage.getItem("username");
  const hasToken = localStorage.getItem("token") !== null;

  useEffect(() => {
    setLoading(true);
    axios
      .get(`http://localhost:8080/api/members/${id}`)
      .then((res) => setMember(res.data))
      .catch((err) => {
        console.error(err);
        alert("상세 조회 실패");
        navigate("/");
      })
      .finally(() => setLoading(false));
  }, [id, navigate]);

  if (loading) {
    return (
      <div className="d-flex justify-content-center mt-5">
        <Spinner animation="border" role="status" />
      </div>
    );
  }

  if (!member) return null;

  const thStyle = {
    backgroundColor: "#f2f2f2",
    textAlign: "left",
    width: "150px"
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">회원 상세</h2>
      <Table bordered hover responsive className="align-middle">
        <tbody>
          <tr>
            <th style={thStyle}>ID</th>
            <td style={{ textAlign: "left" }}>{member.id}</td>
          </tr>
          <tr>
            <th style={thStyle}>Name</th>
            <td style={{ textAlign: "left" }}>{member.name}</td>
          </tr>
          <tr>
            <th style={thStyle}>Age</th>
            <td style={{ textAlign: "left" }}>{member.age}</td>
          </tr>
          <tr>
            <th style={thStyle}>Phone</th>
            <td style={{ textAlign: "left" }}>{member.phone}</td>
          </tr>
          <tr>
            <th style={thStyle}>Address</th>
            <td style={{ textAlign: "left" }}>{member.address}</td>
          </tr>
          {member.createdBy && (
            <tr>
              <th style={thStyle}>작성자</th>
              <td style={{ textAlign: "left" }}>{member.createdBy}</td>
            </tr>
          )}
        </tbody>
      </Table>

      {/* 버튼 영역 */}
      <div className="d-flex justify-content-between mt-3">
        {/* 왼쪽: 수정 버튼 */}
        <div>
          {hasToken && member.createdBy === loginUser && (
            <Link to={`/edit/${member.id}`}>
              <Button variant="warning">수정</Button>
            </Link>
          )}
        </div>

        {/* 오른쪽: 리스트 버튼 */}
        <div>
          <Link to="/">
            <Button variant="secondary">리스트</Button>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default MemberView;
