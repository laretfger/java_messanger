import React, { useEffect, useState } from 'react';
import './Profile.css';
import axios from 'axios';

const Profile = () => {

    const [email, setEmail] = useState();
    const [login, setLogin] = useState();
    const token = localStorage.getItem('jwt');

    useEffect(() => {
        if(!token) {
            document.location.href = 'http://localhost:3000/LoginPage';
        }
        axios.get("http://localhost:8080/me", {
            headers: {
                "Authorization": 'Bearer ' + token
            }
            }).then((result) => {
                setLogin(result.data.login);
                setEmail(result.data.email);
                console.log(result.data);
            }).catch((error) => {
                console.log(error);
                console.log(error.status);
                if(error.status == 403){
                    document.location.href = 'http://localhost:3000/LoginPage';
                }
            })
    })

    const logout = () => {
        localStorage.setItem('jwt', '');
        setEmail();
        log
    }
    return (

        <div className="profile-container">
            <div className="profile-header">
                <h1>Ваш профиль</h1>
            </div>
            <div className="profile-content">
                <div className="profile-details">
                    <h2>Имя: {login}</h2>
                    <p><strong>Email:</strong> {email}</p>
                </div>
            </div>
            <div className="profile-actions">
                {/* <button className="edit-button">Редактировать профиль</button> */}
                <button className="logout-button" onClick={logout}>Выйти</button>
            </div>
        </div>
    );
};

export default Profile;

