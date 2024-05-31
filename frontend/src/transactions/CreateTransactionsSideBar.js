import React, { useState } from 'react';
import './TransactionsSideBar.css';

const CreateTransactionsSideBar = ({ categories, balances, onClose, onAddTransaction }) => {
    const [transactionName, setTransactionName] = useState('');
    const [transactionBalance, setTransactionBalance] = useState('');
    const [transactionCategory, setTransactionCategory] = useState('');
    const [transactionValue, setTransactionValue] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onAddTransaction({
            name: transactionName,
            balance: transactionBalance,
            category: transactionCategory,
            value: parseFloat(transactionValue)
        });
        onClose();
    };

    return (
        <div className="sidebar-form">
            <button className="close-button" onClick={onClose}>Закрити</button>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Назва Транзакції:</label>
                    <input
                        type="text"
                        value={transactionName}
                        onChange={(e) => setTransactionName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Баланс:</label>
                    <select
                        value={transactionBalance}
                        onChange={(e) => setTransactionBalance(e.target.value)}
                        required
                    >
                        <option value="">Виберіть баланс</option>
                        {balances.map((balance) => (
                            <option key={balance.id} value={balance.id}>
                                {balance.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label>Категорія:</label>
                    <select
                        value={transactionCategory}
                        onChange={(e) => setTransactionCategory(e.target.value)}
                        required
                    >
                        <option value="">Виберіть категорію</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label>Сума:</label>
                    <input
                        type="number"
                        value={transactionValue}
                        onChange={(e) => setTransactionValue(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">Додати</button>
            </form>
        </div>
    );
};

export default CreateTransactionsSideBar;
