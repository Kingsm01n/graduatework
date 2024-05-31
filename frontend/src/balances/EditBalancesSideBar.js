import React, { useState, useEffect } from 'react';
import './BalancesSideBar.css';

const EditBalancesSideBar = ({ balance, onClose, onEditBalance }) => {
    const [balanceName, setBalanceName] = useState(balance.name);
    const [balanceCurrentValue, setBalanceCurrentValue] = useState(balance.currentValue);

    const handleSubmit = (e) => {
        e.preventDefault();
        onEditBalance({
            ...balance,
            name: balanceName,
            currentValue: parseFloat(balanceCurrentValue)
        });
        onClose();
    };

    return (
        <div className="sidebar-form">
            <button className="close-button" onClick={onClose}>Закрити</button>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Назва балансу:</label>
                    <input
                        type="text"
                        value={balanceName}
                        onChange={(e) => setBalanceName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Сума:</label>
                    <input
                        type="number"
                        value={balanceCurrentValue}
                        onChange={(e) => setBalanceCurrentValue(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">Зберегти</button>
            </form>
        </div>
    );
};

export default EditBalancesSideBar;
