import React from 'react';
import './Header.css';
import { useNavigate } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();

    const handleHome = () => {
        navigate('/home');
    }
    const handleTransactions = () => {
        navigate('/transactions');
    }
    const handleBalances = () => {
        navigate('/balances');
    }
    const handleAnalytic = () => {
        navigate('/analytics');
    }
    const handleBudgets = () => {
        navigate('/budgets');
    }
    const handleSettings = () => {
        navigate('/settings');
    }
    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/');
    };

    return (
        <header className="App-header">
          <div className="header-content">
            <h1 onClick={handleHome}>Finio</h1>
            <p>Все для твого фінансового комфорту</p>
          </div>
          <div className="header-buttons">
            <button className="transactions-button" onClick={handleTransactions}>Транзакції</button>
            <button className="balances-button" onClick={handleBalances}>Баланси</button>
            <button className="analytic-button" onClick={handleAnalytic}>Аналітика</button>
            <button className="budgets-button" onClick={handleBudgets}>Бюджети</button>
            <button className="settings-button" onClick={handleSettings}>Налаштування</button>
            <button className="logout-button" onClick={handleLogout}>Вихід</button>
          </div>
        </header>
    );
}

export default Header;