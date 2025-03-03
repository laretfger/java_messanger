import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const loginFunc = (userData) => {
    setUser(userData);
  };

  const logoutFunc = () => {
    setUser(null);
  };


  return (
    <AuthContext.Provider value={{ user, loginFunc, logoutFunc }}>
      {children}
    </AuthContext.Provider>
  );
};