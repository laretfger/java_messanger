import React, { useState, useEffect } from 'react';
import { loginFunc } from '../context/AuthContext.js'
import { Link, useNavigate, useLocation, Navigate, redirect } from 'react-router-dom';
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

function Chat() {
  const [messages, setMessages] = useState([]);
  const [chatName, setChatName] = useState([]);
  const [currentMessage, setCurrentMessage] = useState('');
  const [newUser, setNewUser] = useState('');
  const [stompClient, setStompClient] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();
  const token = localStorage.getItem('jwt');


  const headers = {
    'Authorization': 'Bearer ' + token
  };

  useEffect(() => {
    console.log(location.state.id);
    const sock = new SockJS('http://localhost:8080/ws', {
      headers: {
        "Authorization": 'Bearer ' + token
      }
    });
    const client = Stomp.over(sock);
      if(!token) {
        document.location.href = 'http://localhost:3000/LoginPage';
      }
    axios.get(`http://localhost:8080/getMessages/${location.state.id}`, {
      headers: {
        "Authorization": 'Bearer ' + token
      }
    }).then((result) => {
      setChatName(result.data.chatName);
      setMessages(result.data.data);
     }).catch((error) => {
       console.log(error);
       console.log(error.status);
      if(error.status == 403){
        document.location.href = 'http://localhost:3000/LoginPage';
      }
     })
    client.connect(headers, () => {
      setStompClient(client);
      client.subscribe('/topic/messages/' + location.state.id, (message) => {
        const parsedMessage = JSON.parse(message.data);
        setMessages([...messages, parsedMessage]);
      });
      
    });
    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);


  const sendMessage = () => {
    if (stompClient && stompClient.connected && currentMessage.trim() !== '') {
      stompClient.send("/app/send", headers, JSON.stringify({ data: currentMessage, chatId: location.state.id }));
      setMessages([...messages, currentMessage]);
      setCurrentMessage('');
    }
  };

  const addNewUser  = () => {
    axios.post("http://localhost:8080/addNewUser ", {
        loginNew: newUser,
        chatId: location.state.id
    }, { headers })
    .then(() => {
        setNewUser ('');
    })
    .catch(error => {
        console.error("Ошибка при добавлении пользователя:", error);
    });
  };


    return (
        <div>
            <h1>Chat</h1>
            {messages.map((message) => (
              <div className="Message" > 
                {message}
              </div>
            ))}
            <div>
                {messages.map((msg, index) => (
                    <p key={index}>{msg.data}</p>
                ))}
            </div>
            <input
                type="text"
                value={currentMessage}
                onChange={(e) => setCurrentMessage(e.target.value)}
            />
            <button onClick={sendMessage}>Send</button>
            <br/>
            <input
                type="text"
                value={newUser}
                onChange={(e) => setNewUser(e.target.value)}
            />
            <button onClick={addNewUser}>Добавить пользователя</button>
        </div>
    );
};
export default Chat;