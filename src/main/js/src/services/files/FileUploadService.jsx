class FileUploadService {

    static BASE_UPLOAD_URL = '/file/upload';

    static BASE_PROJECTION = 'baseUserProjection';
    static BASE_GET_URL = '/api/files';
    static BASE_FETCH_HEADERS = {
        'Content-Type': 'application/json'
    };

    static uploadFile(file) {
        const formData = new FormData();
        formData.append('file', file);

        return fetch(
            FileUploadService.BASE_UPLOAD_URL,
            {
                method: 'POST',
                credentials: 'same-origin',
                body: formData
            }
        ).then(response => {
            return response.ok;
        });
    }

    static getFiles() {
        const fetchUrl = `${FileUploadService.BASE_GET_URL}?page=${0}&size=${10}&projection=${this.BASE_PROJECTION}`;

        return fetch(
            fetchUrl,
            {
                method: 'GET',
                credentials: 'same-origin',
                headers: FileUploadService.BASE_FETCH_HEADERS
            }
        ).then(response => response.json());
    }
}

export default FileUploadService;