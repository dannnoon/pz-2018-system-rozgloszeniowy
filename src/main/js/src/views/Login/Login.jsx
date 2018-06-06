import React, {Component} from 'react';
import './Login.css';
import CentredComponentsLayout from "../../commons/layouts/CentredComponentsLayout/CentredComponentsLayout";
import {Button, Form, FormGroup, Input, Label} from "reactstrap";
import {Link} from "react-router-dom";
import TextLogo from "../../commons/components/TextLogo/TextLogo";


class Login extends Component {

    static REGISTER_LINK = '/register';

    render() {

        const {error} = this.props.match.params;

        return (
            <CentredComponentsLayout className="login">
                <div>
                    <div className="login-logo">
                        <TextLogo
                            firstLine="Broadcast"
                            firstLineSmall="System"
                        />
                    </div>
                    {
                        error ? <div className="alert alert-danger">Wrong credentials!</div> : null
                    }
                    <Form className="login-form" method="POST" action="/auth/login">
                        <FormGroup>
                            <Label for="email">Email</Label>
                            <Input type="email" name="email" id="email" placeholder="user@broadcast.com"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="password">Password</Label>
                            <Input type="password" name="password" id="password" placeholder="****"/>
                        </FormGroup>
                        <FormGroup className="login-form--buttons">
                            <Link to={Login.REGISTER_LINK}>Need an account?</Link>
                            <Button type="submit" color="success" className="login-form--submit">Login</Button>
                        </FormGroup>
                    </Form>
                </div>
            </CentredComponentsLayout>
        )
    }
}

export default Login;