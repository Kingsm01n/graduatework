import React, { useEffect, useState } from 'react';
import Header from '../header/Header'
import './Transactions.css';

const Transactions = () => {
    const [balances, setBalances] = useState(null);
    const [transactions, setTransactions] = useState(null);
    const token = localStorage.getItem('token');

    useEffect(() => {
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
        }

        const fetchTransactions = async () => {
            try {
                const balanceId = balances[0].id;
                const response = await fetch('http://localhost:8081/api/v1/transactions?balanceId=' + balanceId, {
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

        fetchBalances();
        fetchTransactions();
    }, [token]);

    return (
        <div className="transactions-container">
            <Header/>
            <div className="transactions-content">
                <h2>Транзакції</h2>
                <div className="transactions-filter">
                    <select>
                        {balances !== null ? balances.map((balance, index) => (
                            <option value={balance.id}>{balance.name}</option>
                        )) : 'Загрузка...'}
                    </select>
                    <button className="add-button">Додати</button>
                </div>
                <div className="transactions-list">
                    <ul>
                        <li className="transaction-item">
                            <span>• Транзакція 1</span>
                            <span>Категорія 1</span>
                            <span>4 635.01 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 2</span>
                            <span>Категорія 2</span>
                            <span>36.00 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 3</span>
                            <span>Категорія 3</span>
                            <span>597.26 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 4</span>
                            <span>Категорія 4</span>
                            <span>475.41 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 5</span>
                            <span>Категорія 5</span>
                            <span>1 236.23 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                        <li className="transaction-item">
                            <span>• Транзакція 6</span>
                            <span>Категорія 6</span>
                            <span>987.98 грн</span>
                            <button className="edit-button">Редагувати</button>
                            <button className="delete-button">Видалити</button>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default Transactions;
