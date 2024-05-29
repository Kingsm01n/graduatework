import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const [passwordMatch, setPasswordMatch] = useState(true);

    const handleRepeatPasswordChange = async (e) => {
        setRepeatPassword(e)

        if (e !== password) {
            console.log(repeatPassword !== password, e, password)
            setPasswordMatch(false); // Пароли не совпадают
            return; // Прерываем отправку формы
        } else {
            setPasswordMatch(true); // Пароли совпадают
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (repeatPassword !== password) {
            return; // Прерываем отправку формы
        }

        try {
            const response = await fetch('http://localhost:8080/api/v1/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                navigate('/');
                console.log('Успешный вход!');
            } else {
                // Обработка ошибки
                console.error('Ошибка при входе');
            }
        } catch (error) {
            console.error('Ошибка при отправке запроса:', error);
        }
    };

    return (
        <div className="container">
            <div className="box">
                <h1>Finio</h1>
                <p>Створіть ваш обліковий запис</p>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <input
                            id="repeat-password"
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <input
                            type="password"
                            placeholder="Пароль"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <input
                            type="password"
                            placeholder="Підтвердження пароля"
                            value={repeatPassword}
                            onChange={(e) => handleRepeatPasswordChange(e.target.value)}
                            className={!passwordMatch ? 'input-error' : ''}
                            required
                        />
                    </div>
                    <button type="submit" className="button">Реєстрація</button>
                </form>
                <div className="links">
                    <a href="/">Увійти</a>
                </div>
            </div>
        </div>
    );
};

export default Register;
