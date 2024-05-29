import React, { useEffect, useState } from 'react';
import './Dashboard.css';
import Header from '../header/Header';
import { useNavigate } from 'react-router-dom';
import BudgetChart from '../charts/BudgetChart';

const Dashboard = () => {
    const navigate = useNavigate();
    const [balance, setBalance] = useState(null);
    const [transactions, setTransactions] = useState(null);
    const [categories, setCategories] = useState(null);
    const [budgets, setBudgets] = useState(null);
    const token = localStorage.getItem('token');

    useEffect(() => {
        const fetchBalance = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/v1/balances', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    const totalBalance = data.reduce((total, balance) => total + balance.currentValue, 0);
                    setBalance(totalBalance);
                } else {
                    console.error('Ошибка при получении баланса');
                }
            } catch (error) {
                console.error('Ошибка при отправке запроса:', error);
            }
        };
        const fetchTransactions = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/v1/transactions', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    setTransactions(data);
                } else {
                    console.error('Ошибка при получении баланса');
                }
            } catch (error) {
                console.error('Ошибка при отправке запроса:', error);
            }
        };
        const fetchCategories = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/v1/categories', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    setCategories(data);
                } else {
                    console.error('Ошибка при получении баланса');
                }
            } catch (error) {
                console.error('Ошибка при отправке запроса:', error);
            }
        };
        const fetchBudgets = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/v1/budgets', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    setBudgets(data);
                } else {
                    console.error('Ошибка при получении баланса');
                }
            } catch (error) {
                console.error('Ошибка при отправке запроса:', error);
            }
        };

        fetchTransactions();
        fetchCategories();
        fetchBudgets();
        fetchBalance();
    }, [token]);

    return (
        <div className="dashboard">
          <Header/>
          <main className="dashboard-main">
            <div className="top-row">
              <div className="balance-box">
                <h2>Баланс</h2>
                <div className="balance">
                  <div className="balance-amount">{balance !== null ? `${balance} грн` : 'Загрузка...'}</div>
                </div>
              </div>
              <div className="transactions-box">
                <h2>Транзакції</h2>
                <div className="transactions">
                  <ul>
                      {transactions !== null ? transactions.map((transaction, index) => (
                          <li key={index}>
                              {transaction.name} <span>{transaction.value} грн</span>
                          </li>
                      )) : 'Загрузка...'}
                  </ul>
                </div>
              </div>
            </div>
            <div className="bottom-row">
              <div className="category-and-budget-row">
                <div className="categories-box">
                  <h2>Категорії</h2>
                  <div className="categories">
                    <ul>
                      {categories !== null ? categories.map((category, index) => (
                          <li key={index}>
                              {category.name}
                          </li>
                      )) : 'Загрузка...'}
                    </ul>
                  </div>
                </div>
                <div className="budgets-box">
                  <h2>Бюджети</h2>
                  <div className="budgets">
                    <ul>
                      {budgets !== null ? budgets.map((budget, index) => (
                          <li key={index}>
                              {budget.name}
                          </li>
                      )) : 'Загрузка...'}
                    </ul>
                  </div>
                </div>
              </div>
              <div className="chart">
                {budgets !== null ? <BudgetChart budgets={budgets} />  : 'Загрузка...'}
              </div>
            </div>
          </main>
        </div>
    );
};

export default Dashboard;
