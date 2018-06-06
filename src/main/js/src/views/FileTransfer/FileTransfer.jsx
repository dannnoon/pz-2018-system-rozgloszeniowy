import React, {Component} from 'react';
import './FileTransfer.css';
import BaseLayout from "../../commons/layouts/BaseLayout/BaseLayout";
import {Col, Row} from "reactstrap";
import DropZone from 'react-dropzone';
import FileUploadService from "../../services/files/FileUploadService";
import BaseModal from "../../commons/components/modals/BaseModal/BaseModal";

class FileTransfer extends Component {

    constructor(props) {
        super(props);

        this.state = {
            fileName: "",
            errorModalToggle: null,
            confirmationModalToggle: null
        };
    }

    handleDroppedFile(files) {
        const file = files[0];
        this.state.fileName = file.name;

        FileUploadService
            .uploadFile(file)
            .then(
                success => {
                    if (success) {
                        this.state.confirmationModalToggle();
                    } else {
                        this.state.errorModalToggle();
                    }
                }
            );
    }

    render() {
        return (
            <BaseLayout>
                <Row>
                    <Col xs={12}>
                        <div className="file--header">
                            <h1>
                                Upload file
                            </h1>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <DropZone onDrop={this.handleDroppedFile.bind(this)} multiple="false" className="file--drop-zone"
                              activeClassName="file--drop-zone-active">
                        <div>Drop a file or click to add</div>
                    </DropZone>
                </Row>

                <BaseModal
                    toggle={(toggle) => this.state.confirmationModalToggle = toggle}
                    header={<span>Confirmation</span>}
                    body={
                            <span>File {this.state.fileName} has been uploaded!</span>
                    }
                    cancelButtonContent={<span>Understood!</span>}
                    onCancelClick={() => this.state.confirmationModalToggle()}
                />

                <BaseModal
                    toggle={(toggle) => this.state.errorModalToggle = toggle}
                    header={<span>Error</span>}
                    body={
                        <span>File {this.state.fileName} upload failed!</span>
                    }
                    cancelButtonContent={<span>Understood!</span>}
                    onCancelClick={() => this.state.errorModalToggle()}
                />
            </BaseLayout>
        )
    }
}

export default FileTransfer;