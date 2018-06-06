import React, {Component} from 'react';
import {withRouter} from 'react-router-dom'
import {MdEdit, MdHighlightRemove} from "react-icons/lib/md/index";

class FileRow extends Component {

    render() {
        const {file, index} = this.props;

        return (
            <tr>
                <th>
                    {index + 1}
                </th>
                <td>
                    {file.name}
                </td>
                <td>
                    TODO
                </td>
                <td className="file-table--actions-cell">
                    <MdEdit className="file-table--edit-icon"/>
                    <MdHighlightRemove className="file-table--remove-icon" onClick={() => {

                    }}/>
                </td>
            </tr>
        )
    }
}

export default withRouter(FileRow);