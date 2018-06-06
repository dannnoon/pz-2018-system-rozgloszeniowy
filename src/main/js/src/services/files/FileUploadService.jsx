class FileUploadService {

    static BASE_URL = '/file/upload';

    static uploadFile(file) {
        const formData = new FormData();
        formData.append('file', file);

        return fetch(
            FileUploadService.BASE_URL,
            {
                method: 'POST',
                credentials: 'same-origin',
                body: formData
            }
        ).then(response => {
            return response.ok;
        });
    }
}

export default FileUploadService;