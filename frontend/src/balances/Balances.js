import React, { useEffect, useState } from 'react';
import Header from '../header/Header'
import CreateBalancesSideBar from './CreateBalancesSideBar'
import EditBalancesSideBar from './EditBalancesSideBar'
import './Balances.css';

const Balances = () => {
    const [balances, setBalances] = useState(null);
    const [categories, setCategories] = useState(null);
    const [selectedBalance, setSelectedBalance] = useState('');
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [isEditSidebarOpen, setIsEditSidebarOpen] = useState(false);
    const [balanceToEdit, setBalanceToEdit] = useState(null);
    const token = localStorage.getItem('token');

    const handleAddButtonClick = () => {
        setIsSidebarOpen(true);
    };

    const handleEditClick = (balance) => {
        setBalanceToEdit(balance);
        setIsEditSidebarOpen(true);
    };

    const handleDeleteClick = async (deletedBalance) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/balances/' + deletedBalance.id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                setBalances((prevBalances) =>
                    prevBalances
                        .filter((balance) => balance.id !== deletedBalance.id)
                );
            } else {
            }
        } catch (error) {
        }
    };

    const handleSidebarClose = () => {
        setIsSidebarOpen(false);
    };

    const handleAddBalance = async (balance) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/balances', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(balance)
            });

            if (response.ok) {
                const data = await response.json();
                setBalances((prevBalances) => [...prevBalances, data]);
            } else {
            }
        } catch (error) {
        }
    };

    const handleEditBalance = async (updatedBalance) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/balances/' + updatedBalance.id, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(updatedBalance)
            });

            if (response.ok) {
                const data = await response.json();
                setBalances((prevBalances) =>
                    prevBalances
                        .map((balance) => balance.id === updatedBalance.id ? data : balance)
                );
            } else {
            }
        } catch (error) {
        }
    };

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
                } else {
                }
            } catch (error) {
            }
        };

        fetchData();
    }, [token]);

    return (
        <div className="balances-container">
            <Header/>
            <div className="balances-content">
                <h2>Баланси</h2>
                <div className="balances-filter">
                    <button className="add-button" onClick={handleAddButtonClick}>Додати</button>
                </div>
                <div className="balances-list">
                    <ul>
                        {balances !== null ? balances.map((balance, index) => (
                            <li className="balance-item">
                                <span>• {balance.name}</span>
                                <span>{balance.currentValue} грн</span>
                                <button className="edit-button" onClick={() => handleEditClick(balance)}>Редагувати</button>
                                <button className="delete-button" onClick={() => handleDeleteClick(balance)}>Видалити</button>
                            </li>
                        )) : 'Загрузка...'}
                    </ul>
                </div>
                {isSidebarOpen && (
                    <CreateBalancesSideBar
                        onClose={handleSidebarClose}
                        onAddBalance={handleAddBalance}
                    />
                )}
                {isEditSidebarOpen && (
                    <EditBalancesSideBar
                        balance={balanceToEdit}
                        onClose={() => setIsEditSidebarOpen(false)}
                        onEditBalance={handleEditBalance}
                    />
                )}
            </div>
        </div>
    );
};

export default Balances;
