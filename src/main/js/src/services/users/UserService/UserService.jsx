class UserService {

    static BASE_PROJECTION = 'baseUserProjection';

    static BASE_URL = '/api/users';
    static BASE_FETCH_HEADERS = {
        'Content-Type': 'application/json'
    };

    static getUsers(page, size) {
        const fetchUrl = `${UserService.BASE_URL}?page=${page}&size=${size}&projection=${this.BASE_PROJECTION}`;

        return fetch(
            fetchUrl,
            {
                method: 'GET',
                credentials: 'same-origin',
                headers: UserService.BASE_FETCH_HEADERS
            }
        ).then(response => response.json());
    }

    static getUserById(id) {
        const fetchUrl = `${UserService.BASE_URL}/${id}?projection=${this.BASE_PROJECTION}`;

        return fetch(
            fetchUrl,
            {
                method: 'GET',
                credentials: 'same-origin',
                headers: UserService.BASE_FETCH_HEADERS
            }
        ).then(response => response.json());
    }

    static deleteUser(id) {
        const fetchUrl = `${UserService.BASE_URL}/${id}`;

        return fetch(
            fetchUrl,
            {
                method: 'DELETE',
                credentials: 'same-origin',
                headers: UserService.BASE_FETCH_HEADERS
            }
        );
    }

    static createUser(user, roles, groups) {
        const fetchUrl = `${UserService.BASE_URL}`;

        const convertedRoles = roles.map(role => role._links.self.href.replace("{?projection}", "")).join('\n');
        const convertedGroups = groups.map(group => group._links.self.href.replace("{?projection}", "")).join('\n');

        return fetch(
            fetchUrl,
            {
                method: 'POST',
                credentials: 'same-origin',
                body: JSON.stringify(user),
                headers: UserService.BASE_FETCH_HEADERS
            }
        ).then(response => {

            if (response.ok) {
                return response.json()
                    .then(user => {
                        const rolesPromise = fetch(
                            user._links.roles.href.replace("{?projection}", ""),
                            {
                                method: 'PUT',
                                credentials: 'same-origin',
                                headers: {
                                    'Accept': 'text/uri-list',
                                    'Content-Type': 'text/uri-list'
                                },
                                body: convertedRoles
                            }
                        );

                        const groupsPromise = fetch(
                            user._links.groups.href.replace("{?projection}", ""),
                            {
                                method: 'PUT',
                                credentials: 'same-origin',
                                headers: {
                                    'Accept': 'text/uri-list',
                                    'Content-Type': 'text/uri-list'
                                },
                                body: convertedGroups
                            }
                        );

                        return Promise.all([rolesPromise, groupsPromise])
                            .then(responses => {
                                return responses.every(response => response.ok)
                            })
                    });
            }

            return false;
        });
    }

    static updateUser(id, user, roles, groups) {
        const fetchUrl = `${UserService.BASE_URL}/${id}`;

        const convertedRoles = roles.map(role => role._links.self.href.replace("{?projection}", "")).join('\n');
        const convertedGroups = groups.map(group => group._links.self.href.replace("{?projection}", "")).join('\n');

        return fetch(
            fetchUrl,
            {
                method: 'PUT',
                credentials: 'same-origin',
                body: JSON.stringify(user),
                headers: UserService.BASE_FETCH_HEADERS
            }
        ).then(response => {

            if (response.ok) {
                return response.json()
                    .then(user => {
                        const rolesPromise = fetch(
                            user._links.roles.href.replace("{?projection}", ""),
                            {
                                method: 'PUT',
                                credentials: 'same-origin',
                                headers: {
                                    'Accept': 'text/uri-list',
                                    'Content-Type': 'text/uri-list'
                                },
                                body: convertedRoles
                            }
                        );

                        const groupsPromise = fetch(
                            user._links.groups.href.replace("{?projection}", ""),
                            {
                                method: 'PUT',
                                credentials: 'same-origin',
                                headers: {
                                    'Accept': 'text/uri-list',
                                    'Content-Type': 'text/uri-list'
                                },
                                body: convertedGroups
                            }
                        );

                        return Promise.all([rolesPromise, groupsPromise])
                            .then(responses => {
                                return responses.every(response => response.ok)
                            })
                    });
            }

            return false;
        });
    }

}

export default UserService;