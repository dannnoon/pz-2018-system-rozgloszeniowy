import React, {Component} from 'react';
import {Table} from "reactstrap";
import './FileList.css';
import FileRow from "./FileRow";
import FileUploadService from "../../../services/files/FileUploadService";

const FilesHeader = () => (
    <tr>
        <th>ID</th>
        <th>File name</th>
        <th>Groups</th>
        <th>Actions</th>
    </tr>
);

class FileList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            files: [],
            fileToDelete: null,
            deleteModalToggle: null,
            errorModalToggle: null
        }
    }

    getFiles() {
        FileUploadService.getFiles()
            .then(json => {
                this.setState({
                    files: json._embedded.files
                });
            })
            .catch(error => console.log(error))
    }

    removeFile() {
        FileUploadService.deleteFile(file.id)
            .then(response => {

            });
    }

    componentDidMount() {
        this.getFiles();
    }

    render() {
        return (
            <div>
                <Table
                    responsive
                    className="file-table"
                >
                    <thead>
                        <FilesHeader/>
                    </thead>
                    <tbody>
                        {this.state.files.map((file, index) => <FileRow file={file} index={index}/>)}
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default FileList;