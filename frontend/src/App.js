import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './login/Login';
import Register from './register/Register';
import Dashboard from './dashboard/Dashboard';
import Transactions from './transactions/Transactions';
import PrivateRoute from './PrivateRoute';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/register" element={<Register />} />
                <Route path="/" element={<Login />} />
                <Route element={<PrivateRoute />}>
                    <Route path="/home" element={<Dashboard />} />
                    <Route path="/transactions" element={<Transactions />} />
                </Route>
            </Routes>
        </Router>
    );
};

export default App;
