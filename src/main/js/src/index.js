import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import {Provider} from "react-redux";
import {createStore} from 'redux';
import reducers from './reducers';
import Homepage from "./views/Homepage/Homepage";
import Login from "./views/Login/Login";
import Register from "./views/Register/Register";
import Users from "./views/Users/Users";
import AddEditUser from "./views/Users/components/AddEditUser/AddEditUser";

const store = createStore(reducers);

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <Switch>
                <Route exact path="/" component={Homepage}/>
                <Route exact path="/users" component={Users}/>
                <Route exact path="/users/add" component={AddEditUser}/>
                <Route path="/users/:id" component={AddEditUser}/>
                <Route exact path="/login" component={Login}/>
                <Route path="/login/:error" component={Login}/>
                <Route path="/register" component={Register}/>
            </Switch>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);
