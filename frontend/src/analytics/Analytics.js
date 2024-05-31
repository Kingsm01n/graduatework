import React, { useEffect, useState } from 'react';
import './Analytics.css';
import Header from '../header/Header'
import BudgetChart from '../charts/BudgetChart'
import TransactionsChart from '../charts/TransactionsChart'

const Analytics = () => {
    const [budgets, setBudgets] = useState(null);
    const [transactions, setTransactions] = useState(null);
    const [balances, setBalances] = useState(null);
    const token = localStorage.getItem('token');

    useEffect(() => {
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
        const fetchBalances = async () => {
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
                    setBalances(data);
                } else {
                    console.error('Ошибка при получении баланса');
                }
            } catch (error) {
                console.error('Ошибка при отправке запроса:', error);
            }
        };

        fetchBalances();
        fetchTransactions();
        fetchBudgets();
    }, [token]);

    return (
        <div className="analytics">
            <Header/>
            <div className="content">
                <div className="analytics-section">
                    <h2>Аналітика</h2>
                    <div className="charts">
                        <div className="chart">
                            {transactions !== null && balances !== null ? <TransactionsChart balances={balances} transactions={transactions} /> : 'Загрузка...'}
                        </div>
                        <div className="chart">
                            {budgets !== null ? <BudgetChart budgets={budgets} />  : 'Загрузка...'}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Analytics;
