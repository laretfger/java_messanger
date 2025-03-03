import React from 'react';
import { useState, useContext } from 'react'; // для управления состоянием формы
import { Link } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../../context/AuthContext.js';
import './LoginPage.css';

function Login() {
  const [password, setPassword] = useState('');
  const [login, setLogin] = useState('');

  const {user, loginFunc} = useContext(AuthContext);

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/login", {
        login: login,
        password: password
    }
    ).then((result) => {
        localStorage.setItem('jwt', result.data.token);
        console.log(result.data.token);
        delete result.data.token;
        loginFunc(result.data);
        console.log(result);
        document.location.href = 'http://localhost:3000/';
    });
  };

  return (
    <>
      <div className="login-container">
        <h1>Вход</h1>
        <form onSubmit={handleSubmit}>

        <label>Login:</label>
        <input
            type="text"
            placeholder="Login"
            value={login}
            onChange={(e) => setLogin(e.target.value)}
            required
            maxLength={20}
          />

          <label>Password:</label>
          <input
            type="password"
            placeholder="Пароль"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            maxLength={20}
          />
          <button type="submit">Войти</button>
        </form>
      </div>
      <Link to="/RegistrPage">Регистрация</Link>
    </>
  );
}
export default Login;