import React from 'react';
import { useNavigate } from "react-router";
import './ChatItem.css';

const ChatItem = ({ chat }) => {
  const navigate = useNavigate();

  const navigateFun = (e) => {
    navigate('/Chat', { state: { id: chat.chatId }});
  }

  return (
    <div className="chat-item" onClick={navigateFun}>
      <div className="chat-info">
        <div className="chat-name">{chat.chatName}</div>
        {/* <div className="chat-preview">{chat.lastMessage}</div> */}
      </div>
      {/* <div className="chat-time">{chat.time}</div> */}
    </div>
  );
};


export default ChatItem;