import './Header.css';
import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';

const Header = () => {
  const { user, logoutFunc } = useContext(AuthContext);

  return (
    <header className="App-header">
      <h1>Chat App</h1>
      {user ? (
        <>
          <button onClick={logoutFunc}>Logout</button>
          <Link to="/ChatList">Chats</Link>
        </>
      ) : (
        <>
          <Link to="/LoginPage">Login</Link>
          <Link to="/RegisterPage">Register</Link>
        </>
      )}
    </header>
  );
};
  
export default Header;