import './ChatList.css';
import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';


function ChatList() {
    const [chatIdList, setchatIdList] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/getChatsId").then((result) => {
         setchatIdList(result);
        }).catch((error) => {
          console.log(error);
          console.log(error.status);
          navigate('LoginPage');
        })
    }, []);

    return (
      <>
        {chatIdList.map((chatId) => {
            <div className="" key={chatId} onClick={() => navigate('Chat', { state: { id: chatId }})}>
                <img src=""/>
                <h3 className="userName"></h3>
                <p className="lastMessage"></p>
            </div>
            
        })}
      </>
    );
  }
  
  export default ChatList;