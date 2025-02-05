import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation, Navigate, redirect } from 'react-router-dom';
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

function Chat() {
  const [messages, setMessages] = useState([]);
  const [currentMessage, setCurrentMessage] = useState('');
  const [stompClient, setStompClient] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const sock = new SockJS('http://localhost:8080/ws');
    const client = Stomp.over(sock);
    axios.get("http://localhost:8080/getMessages").then((result) => {
      setMessages(result);
     }).catch((error) => {
       console.log(error);
       console.log(error.status);
       <Navigate to="/LoginPage" replace />
       console.log("Редирект");
       redirect('/LoginPage');
     })
    client.connect({}, () => {
      setStompClient(client);
      alert(stompClient);
      client.subscribe('/topic/messages/' + location.id, (message) => {
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
    if (stompClient) {
      stompClient.send("/app/send", {}, JSON.stringify({ data: currentMessage, chatId: location.id }));
      setMessages([...messages, currentMessage]);
      setCurrentMessage('');
    }
  };

    return (
        <div>
            <h1>Chat</h1>
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
        </div>
    );
};
export default Chat;