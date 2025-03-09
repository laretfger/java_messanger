import './Header.css';
import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const Header = () => {
  const user = localStorage.getItem('user');

  return (
    <header className="App-header">
      <h1>Chat App</h1>
      {user ? (
        <>
          <Link to="/">Chats</Link>
          <Link to="/Profile" className="link">Профиль</Link>
        </>
      ) : (
        <>
          <Link to="/LoginPage" className="link">Вход</Link>
          <Link to="/RegistrPage" className="link">Регистрация</Link>
        </>
      )}
    </header>
  );
};
  
export default Header;