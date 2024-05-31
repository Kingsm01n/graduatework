import React, { useState, useEffect } from 'react';
import './BudgetsSideBar.css';

const EditBudgetsSideBar = ({ budget, categories, onClose, onEditBudget }) => {
    const [budgetName, setBudgetName] = useState(budget.name);
    const [budgetCategory, setBudgetCategory] = useState(budget.category);
    const [budgetNeededValue, setBudgetNeededValue] = useState(budget.neededValue);

    const handleSubmit = (e) => {
        e.preventDefault();
        onEditBudget({
            ...budget,
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
                    <label>Название бюджету:</label>
                    <input
                        type="text"
                        value={budgetName}
                        onChange={(e) => setBudgetName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Категория:</label>
                    <select
                        value={budgetCategory}
                        onChange={(e) => setBudgetCategory(e.target.value)}
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
                        value={budgetNeededValue}
                        onChange={(e) => setBudgetNeededValue(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">Сохранить</button>
            </form>
        </div>
    );
};

export default EditBudgetsSideBar;
