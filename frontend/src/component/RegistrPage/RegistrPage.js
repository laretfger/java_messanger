import React from 'react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function RegistrPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [login, setLogin] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/registry", {
        login: login,
        password: password,
        confirm_password: confirmPassword,
        email: email
    }
    ).then((result) => {
        alert(result);

    });
    console.log('Email:', email, 'Password:', password);
  };

  return (
    <>
        <div className="login-container">
        <h1>Регистрация</h1>
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

            <label>Email:</label>
            <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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
            
            <label>Confirm password:</label>
            <input
            type="password"
            placeholder="Подтверждение пароля"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            maxLength={20}
            />
            <button onSubmit={handleSubmit} >Зарегистрироваться</button>
        </form>
        </div>
        <Link to="/LoginPage">Вход</Link>
    </>
  );
}


export default RegistrPage;



