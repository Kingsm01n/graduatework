import React, { useEffect, useState } from 'react';
import './Budgets.css';
import Header from '../header/Header';
import CreateBudgetsSideBar from './CreateBudgetsSideBar'
import EditBudgetsSideBar from './EditBudgetsSideBar'
import BudgetChart from '../charts/BudgetChart';

const Budgets = () => {
    const [budgets, setBudgets] = useState(null);
    const [categories, setCategories] = useState(null);
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [isEditSidebarOpen, setIsEditSidebarOpen] = useState(false);
    const [budgetToEdit, setBudgetToEdit] = useState(null);
    const token = localStorage.getItem('token');

    const handleAddButtonClick = () => {
        setIsSidebarOpen(true);
    };

    const handleAddBudget = async (budget) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/budgets', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(budget)
            });

            if (response.ok) {
                const data = await response.json();
                setBudgets((prevBudgets) => [...prevBudgets, data]);
            } else {
            }
        } catch (error) {
        }
    };

    const handleSidebarClose = () => {
        setIsSidebarOpen(false);
    };

    const handleEditClick = (budget) => {
        setBudgetToEdit(budget);
        setIsEditSidebarOpen(true);
    };

    const handleEditBudget = async (updatedBudget) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/budgets/' + updatedBudget.id, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(updatedBudget)
            });

            if (response.ok) {
                const data = await response.json();
                setBudgets((prevBudgets) =>
                    prevBudgets
                        .map((budget) => budget.id === updatedBudget.id ? data : budget)
                );
            } else {
            }
        } catch (error) {
        }
    };

    const handleDeleteClick = async (deletedBudget) => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/budgets/' + deletedBudget.id, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                setBudgets((prevBudgets) =>
                    prevBudgets
                        .filter((budget) => budget.id !== deletedBudget.id)
                );
            } else {
            }
        } catch (error) {
        }
    };

    useEffect(() => {
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
                }
            } catch (error) {
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
                }
            } catch (error) {
            }
        };

        fetchCategories();
        fetchBudgets();
    }, [token]);

    return (
        <div className="budget">
            <Header/>
            <div className="content">
                <div className="budget-header">
                    <h2>Бюджети</h2>
                    <button className="add-button" onClick={handleAddButtonClick}>Додати</button>
                </div>
                <div className="budget-container">
                    <div className="budget-section">
                        <div className="budget-list">
                            <ul>
                                {budgets !== null ? budgets.map((budget, index) => (
                                    <li key={index}>
                                        <div>{budget.name}</div>
                                        <div><span>{budget.currentValue}/{budget.neededValue}</span></div>
                                        <div className="budget-buttons">
                                            <button className="edit-button" onClick={() => handleEditClick(budget)}>Редагувати</button>
                                            <button className="delete-button" onClick={() => handleDeleteClick(budget)}>Видалити</button>
                                        </div>
                                    </li>
                                )) : 'Загрузка...'}
                            </ul>
                        </div>
                    </div>
                    <div className="chart">
                        {budgets !== null ? <BudgetChart budgets={budgets} />  : 'Загрузка...'}
                    </div>
                </div>
                {isSidebarOpen && (
                    <CreateBudgetsSideBar
                        categories={categories}
                        onClose={handleSidebarClose}
                        onAddBudget={handleAddBudget}
                    />
                )}
                {isEditSidebarOpen && (
                    <EditBudgetsSideBar
                        budget={budgetToEdit}
                        categories={categories}
                        onClose={() => setIsEditSidebarOpen(false)}
                        onEditBudget={handleEditBudget}
                    />
                )}
            </div>
        </div>
    );
};

export default Budgets;
