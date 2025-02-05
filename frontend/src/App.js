import './App.css';
import Header from './component/Header/Header.js';
import LoginPage from './component/LoginPage/LoginPage.js';
import RegistrPage from './component/RegistrPage/RegistrPage.js';
import Chat from './component/Chat/Chat.js';
import ChatList from './component/ChatList/ChatList.js';
import { Routes, Route } from 'react-router-dom';

function App() {
  return (
    <>
      <Header className="App-header"/>
      <Routes>
        <Route path="/Chat" element={<Chat />} />
        <Route path="/" element={<ChatList />} />
        <Route path="/LoginPage" element={<LoginPage />} />
        <Route path="/RegistrPage" element={<RegistrPage />} />
      </Routes>
    </>
  );
}

export default App;
