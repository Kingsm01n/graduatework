import React, { useState } from 'react';
import './BudgetsSideBar.css';

const CreateBudgetsSideBar = ({ categories, balances, onClose, onAddBudget }) => {
    const [budgetName, setBudgetName] = useState('');
    const [budgetCategory, setBudgetCategory] = useState('');
    const [budgetNeededValue, setBudgetNeededValue] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onAddBudget({
            name: budgetName,
            category: budgetCategory,
            currentValue: parseFloat(0),
            neededValue: parseFloat(budgetNeededValue)
        });
        onClose();
    };

    return (
        <div className="sidebar-form">
            <button className="close-button" onClick={onClose}>Закрити</button>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Назва бюджету:</label>
                    <input
                        type="text"
                        value={budgetName}
                        onChange={(e) => setBudgetName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Категорія:</label>
                    <select
                        value={budgetCategory}
                        onChange={(e) => setBudgetCategory(e.target.value)}
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
                    <label>Ліміт:</label>
                    <input
                        type="number"
                        value={budgetNeededValue}
                        onChange={(e) => setBudgetNeededValue(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">Додати</button>
            </form>
        </div>
    );
};

export default CreateBudgetsSideBar;
