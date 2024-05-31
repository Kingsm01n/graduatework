import React, { useEffect, useState } from 'react';
import Header from '../header/Header'
import CreateTransactionsSideBar from './CreateTransactionsSideBar'
import EditTransactionsSideBar from './EditTransactionsSideBar'
import './Transactions.css';

const Transactions = () => {
    const [balances, setBalances] = useState(null);
    const [transactions, setTransactions] = useState(null);
    const [categories, setCategories] = useState(null);
    const [selectedBalance, setSelectedBalance] = useState('');
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [isEditSidebarOpen, setIsEditSidebarOpen] = useState(false);
    const [transactionToEdit, setTransactionToEdit] = useState(null);
    const token = localStorage.getItem('token');

    const handleAddButtonClick = () => {
        setIsSidebarOpen(true);
    };

    const handleEditClick = (transaction) => {
        setTransactionToEdit(transaction);
        setIsEditSidebarOpen(true);
    };

    const handleDeleteClick = async (deletedTransaction) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/transactions/' + deletedTransaction.id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                setTransactions((prevTransactions) =>
                    prevTransactions
                        .filter((transaction) => transaction.id !== deletedTransaction.id)
                );
            } else {
                console.error('Ошибка при добавлении транзакции');
            }
        } catch (error) {
            console.error('Ошибка при отправке запроса:', error);
        }
    };

    const handleSidebarClose = () => {
        setIsSidebarOpen(false);
    };

    const handleAddTransaction = async (transaction) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/transactions', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(transaction)
            });

            if (response.ok) {
                const data = await response.json();
                if (selectedBalance === data.balance) {
                    setTransactions((prevTransactions) => [...prevTransactions, data]);
                }
            } else {
                console.error('Ошибка при добавлении транзакции');
            }
        } catch (error) {
            console.error('Ошибка при отправке запроса:', error);
        }
    };

    const handleEditTransaction = async (updatedTransaction) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/transactions/' + updatedTransaction.id, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(updatedTransaction)
            });

            if (response.ok) {
                const data = await response.json();
                setTransactions((prevTransactions) =>
                    prevTransactions
                        .map((transaction) => transaction.id === updatedTransaction.id ? updatedTransaction : transaction)
                        .filter((transaction) => transaction.balance === selectedBalance)
                );
            } else {
                console.error('Ошибка при добавлении транзакции');
            }
        } catch (error) {
            console.error('Ошибка при отправке запроса:', error);
        }
    };

    const handleSelectChange = async (e) => {
        try {
            const balanceId = e;
            setSelectedBalance(balanceId);
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
    }
    useEffect(() => {
        const fetchData = async () => {
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

                    try {
                        const balanceId = data[0].id;
                        setSelectedBalance(data[0].id);
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

        fetchCategories();
        fetchData();
    }, [token]);

    return (
        <div className="transactions-container">
            <Header/>
            <div className="transactions-content">
                <h2>Транзакції</h2>
                <div className="transactions-filter">
                    <select onChange={(e) => handleSelectChange(e.target.value)}>
                        {balances !== null ? balances.map((balance, index) => (
                            <option value={balance.id}>{balance.name}</option>
                        )) : 'Загрузка...'}
                    </select>
                    <button className="add-button" onClick={handleAddButtonClick}>Додати</button>
                </div>
                <div className="transactions-list">
                    <ul>
                        {transactions !== null ? transactions.map((transaction, index) => (
                            <li className="transaction-item">
                                <span>• {transaction.name}</span>
                                <span>{categories.find(cat => cat.id === transaction.category).name}</span>
                                <span>{transaction.value} грн</span>
                                <button className="edit-button" onClick={() => handleEditClick(transaction)}>Редагувати</button>
                                <button className="delete-button" onClick={() => handleDeleteClick(transaction)}>Видалити</button>
                            </li>
                        )) : 'Загрузка...'}
                    </ul>
                </div>
                {isSidebarOpen && (
                    <CreateTransactionsSideBar
                        categories={categories}
                        balances={balances}
                        onClose={handleSidebarClose}
                        onAddTransaction={handleAddTransaction}
                    />
                )}
                {isEditSidebarOpen && (
                    <EditTransactionsSideBar
                        transaction={transactionToEdit}
                        categories={categories}
                        balances={balances}
                        onClose={() => setIsEditSidebarOpen(false)}
                        onEditTransaction={handleEditTransaction}
                    />
                )}
            </div>
        </div>
    );
};

export default Transactions;
