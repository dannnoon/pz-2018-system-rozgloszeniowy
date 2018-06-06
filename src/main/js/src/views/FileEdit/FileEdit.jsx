import React, {Component} from 'react';
import BaseLayout from "../../commons/layouts/BaseLayout/BaseLayout";
import {Col, Row} from "reactstrap";
import FileList from "./components/FileList";

class FileEdit extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <BaseLayout>
                <Row>
                    <Col xs={12}>
                        <div className="file-edit--header">
                            <h1>
                                Files
                            </h1>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col md={12}>
                        <FileList/>
                    </Col>
                </Row>
            </BaseLayout>
        )
    }
}

export default FileEdit;