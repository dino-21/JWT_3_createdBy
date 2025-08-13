import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MemberList from './components/MemberList';
import NewMemberForm from './components/NewMemberForm';
import UpdateMemberForm from './components/UpdateMemberForm';
import NavigationBar from "./components/NavigationBar";
import LoginForm from "./components/LoginForm";
import SignupForm from "./components/SignupForm";
import MemberView from "./components/MemberView"; 

function App() {
  return (
    <BrowserRouter>
      <NavigationBar />
      <Routes>
        <Route path="/" element={<MemberList />} />
          <Route path="/new" element={<NewMemberForm />} />
          <Route path="/edit/:id" element={<UpdateMemberForm />} />
          <Route path="/view/:id" element={<MemberView />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/signup" element={<SignupForm />} />
      </Routes>
  </BrowserRouter>
  );
}

export default App;
