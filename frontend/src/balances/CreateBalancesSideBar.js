import React, { useState } from 'react';
import './BalancesSideBar.css';

const CreateBalancesSideBar = ({ onClose, onAddBalance }) => {
    const [balanceName, setBalanceName] = useState('');
    const [balanceCurrentValue, setBalanceCurrentValue] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onAddBalance({
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
                <button type="submit" className="submit-button">Додати</button>
            </form>
        </div>
    );
};

export default CreateBalancesSideBar;
