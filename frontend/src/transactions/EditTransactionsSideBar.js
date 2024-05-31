import React, { useState, useEffect } from 'react';
import './TransactionsSideBar.css';

const EditTransactionsSideBar = ({ transaction, balances, categories, onClose, onEditTransaction }) => {
    const [transactionName, setTransactionName] = useState(transaction.name);
    const [transactionCategory, setTransactionCategory] = useState(transaction.category);
    const [transactionBalance, setTransactionBalance] = useState(transaction.balance);
    const [transactionValue, setTransactionValue] = useState(transaction.value);

    const handleSubmit = (e) => {
        e.preventDefault();
        onEditTransaction({
            ...transaction,
            name: transactionName,
            category: transactionCategory,
            balance: transactionBalance,
            value: parseFloat(transactionValue)
        });
        onClose();
    };

    return (
        <div className="sidebar-form">
            <button className="close-button" onClick={onClose}>Закрити</button>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Название Транзакции:</label>
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
                        <option value="">Выберите баланс</option>
                        {balances.map((balance) => (
                            <option key={balance.id} value={balance.id}>
                                {balance.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label>Категория:</label>
                    <select
                        value={transactionCategory}
                        onChange={(e) => setTransactionCategory(e.target.value)}
                        required
                    >
                        <option value="">Выберите категорию</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label>Сумма:</label>
                    <input
                        type="number"
                        value={transactionValue}
                        onChange={(e) => setTransactionValue(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">Сохранить</button>
            </form>
        </div>
    );
};

export default EditTransactionsSideBar;
