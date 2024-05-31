import React, { useEffect, useState } from 'react';
import './Settings.css';
import Header from '../header/Header';

const Settings = () => {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [categories, setCategories] = useState(null);
  const [newCategory, setNewCategory] = useState('');
  const [editCategoryId, setEditCategoryId] = useState(null);
  const [editCategoryName, setEditCategoryName] = useState('');
  const token = localStorage.getItem('token');

  const handleAddCategory = async () => {
    try {
      const response = await fetch('http://localhost:8081/api/v1/categories', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ name: newCategory })
      });

      if (response.ok) {
        const data = await response.json();
        setCategories([...categories, data]);
        setNewCategory('');
      } else {
      }
    } catch (error) {
    }
  };

  const handleEditClick = (category) => {
    setEditCategoryId(category.id);
    setEditCategoryName(category.name);
  };

  const handleSaveUser = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/v1/user', {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ name: name, password: password, email: email, phoneNumber: phone })
      });

      if (response.ok) {
        const updatedCategory = await response.json();
        setCategories(categories.map(cat => (cat.id === updatedCategory.id ? updatedCategory : cat)));
        setEditCategoryId(null);
        setEditCategoryName('');
      } else {
      }
    } catch (error) {
    }
  };

  const handleSaveClick = async (category) => {
    try {
      const response = await fetch('http://localhost:8081/api/v1/categories/' + category.id, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ name: editCategoryName })
      });

      if (response.ok) {
        const updatedCategory = await response.json();
        setCategories(categories.map(cat => (cat.id === updatedCategory.id ? updatedCategory : cat)));
        setEditCategoryId(null);
        setEditCategoryName('');
      } else {
      }
    } catch (error) {
    }
  };

  const handleDeleteClick = async (deletedCategory) => {
    try {
      const response = await fetch('http://localhost:8081/api/v1/categories/' + deletedCategory.id, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        setCategories(categories.filter(category => category.id !== deletedCategory.id));
      } else {
      }
    } catch (error) {
    }
  };

  useEffect(() => {
    const fetchUser = async () => {
        try {
          const response = await fetch('http://localhost:8080/api/v1/user', {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`
            }
          });

          if (response.ok) {
            const data = await response.json();
            setName(data.name);
            setPassword('')
            setPhone(data.phoneNumber);
            setEmail(data.email);
          } else {
          }
        } catch (error) {
        }
    }
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

    fetchUser();
    fetchCategories();
  }, [token]);

  return (
    <div className="settings-container">
      <Header />
      <div className="settings-content">
        <div className="profile-block">
          <h2>Налаштування</h2>
          <div className="profile-settings">
              <input type="text" placeholder="Ім'я" value={name} onChange={(e) => setName(e.target.value)} />
              <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
              <input type="password" placeholder="Новий пароль" value={password} onChange={(e) => setPassword(e.target.value)} />
              <input type="text" placeholder="Телефон" value={phone} onChange={(e) => setPhone(e.target.value)} />
              <button onClick={handleSaveUser}>Зберегти</button>
          </div>
        </div>
        <div className="category-block">
          <div className="category-header">
            <h2>Категорії</h2>
            <div>
                <input
                  type="text"
                  placeholder="Нова категорія"
                  value={newCategory}
                  onChange={(e) => setNewCategory(e.target.value)}
                />
                <button onClick={handleAddCategory}>Додати</button>
            </div>
          </div>
          <div className="category-settings">
            <ul>
              {categories !== null ? categories.map((category) => (
                <li key={category.id}>
                  {editCategoryId === category.id ? (
                    <input
                      type="text"
                      value={editCategoryName}
                      onChange={(e) => setEditCategoryName(e.target.value)}
                    />
                  ) : (
                    category.name
                  )}
                  <div className="category-buttons">
                    {editCategoryId === category.id ? (
                      <button className="save-button" onClick={() => handleSaveClick(category)}>Зберегти</button>
                    ) : (
                      <button className="edit-button" onClick={() => handleEditClick(category)}>Редагувати</button>
                    )}
                    <button className="delete-button" onClick={() => handleDeleteClick(category)}>Видалити</button>
                  </div>
                </li>
              )) : 'Загрузка...'}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Settings;
