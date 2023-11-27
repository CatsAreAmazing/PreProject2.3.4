function userAuthInfo() {
    const tbody = document.getElementById('user-table');
    const panel = document.getElementById("user-header");
    fetch('http://localhost:8080/user/')
        .then((res) => res.json())
        .then((userInfo) => {
            let temp = '';
            let roles = [];
            for (let role of userInfo.roles) {
                roles.push(" " + role.name.toString().substring(5))
            }
            temp += `<tr>
        <td>${userInfo.id}</td>
        <td>${userInfo.username}</td>
        <td>${userInfo.lastName}</td>
        <td>${userInfo.age}</td>
        <td>${userInfo.email}</td>
        <td>${roles}</td>
        </tr>`;
            tbody.innerHTML = temp;
            panel.innerHTML = `<h5>${userInfo.email} with roles: ${roles}</h5>`
        });
}

userAuthInfo()