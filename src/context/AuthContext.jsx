import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Simulate checking for existing session
  React.useEffect(() => {
    // Here you would typically check for an existing session
    // For now, we'll just set loading to false after a brief delay
    const timer = setTimeout(() => {
      setLoading(false);
    }, 1000);

    return () => clearTimeout(timer);
  }, []);

  const login = async (email, password) => {
    try {
      setLoading(true);
      // Here you would typically make an API call to your backend
      // For now, we'll simulate a successful login
      if (email && password) {
        setUser({ email });
        return true;
      } else {
        throw new Error('Invalid credentials');
      }
    } catch (error) {
      throw new Error(error.message || 'Failed to login');
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setLoading(true);
    setUser(null);
    setLoading(false);
  };

  const value = {
    user,
    loading,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}; 