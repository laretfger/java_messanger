import './ChatList.css';
import { useEffect, useState } from 'react';
import { Link, useNavigate, redirect } from 'react-router-dom';
import ChatItem from '../ChatItem/ChatItem.js';
import axios from 'axios';


function ChatList() {
    const [chatIdList, setChatIdList] = useState([]);
    const [newChatName, setNewChatName] = useState('');
    const [userNameChat, setUserNameChat] = useState('');


    const navigate = useNavigate();
    const token = localStorage.getItem('jwt');

    useEffect(() => {
      if(!token) {
        document.location.href = 'http://localhost:3000/LoginPage';
      }
        axios.get("http://localhost:8080/getChats", {
          headers: {
            "Authorization": 'Bearer ' + token
          }
        }).then((result) => {
         setChatIdList(result.data);
         console.log(result.data);
        }).catch((error) => {
          console.log(error);
          console.log(error.status);

            localStorage.setItem('jwt', '');
            document.location.href = 'http://localhost:3000/LoginPage';
        })
    }, []);

    const createChat = (e) => {
      e.preventDefault();
      if (newChatName.trim()) {
        const headers = {
          'Authorization': 'Bearer ' + token
        };
        axios.post("http://localhost:8080/create",
          {
            chatName: newChatName
          }, {headers}
        ).then((result) => {
          setNewChatName('');
          alert('Чат создан!');
          setChatIdList([...chatIdList, result.data]);
          console.log(result.data);
        }).catch((error) => {
          if(error.status == 403){
            alert('Redire');
            // localStorage.setItem('jwt', '');
            // document.location.href = 'http://localhost:3000/LoginPage';
          }
          // result
        })
      }

    }

    return (
      <>
        <form className="new-chat-form" onSubmit={createChat}>
                  {/* <input
                      type="text"
                      placeholder="Имя собеседника"
                      value={userNameChat}
                      onChange={(e) => setNewChatName(e.target.value)}
                      className="new-chat-input"
                  /> */}
                  <input
                      type="text"
                      placeholder="Название чата"
                      value={newChatName}
                      onChange={(e) => setNewChatName(e.target.value)}
                      className="new-chat-input"
                  />
                  <button type="submit" className="new-chat-button">Создать</button>
        </form>
          {chatIdList != [] && (
            <div className="chat-list">
              {chatIdList.map((chat, index) => (
                <ChatItem key={index} chat={chat} />
              ))}
            </div>
          )}
      </>
    );
  }
  
  export default ChatList;