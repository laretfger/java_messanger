import { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './RegistrPage.css';
import { AuthContext } from '../context/AuthContext';

function RegistrPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [login, setLogin] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const {user, loginFunc} = useContext(AuthContext);

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:8080/registry", {
        login: login,
        password: password,
        confirm_password: confirmPassword,
        email: email
    }
    ).then((result) => {
        localStorage.setItem('jwt', result.data.token);

        delete result.data.token;
        localStorage.setItem('user', result.data);
        document.location.href = 'http://localhost:3000/';
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



