const adminUrl = 'http://localhost:8080/admin/'

const urlNewUser = 'http://localhost:8080/admin/create';
const form_addUser = document.getElementById('formForNewUser');
const role_new = document.querySelector('#rolesForCreate').selectedOptions;


const deleteForm = document.getElementById('formDelete');
const delete_id = document.getElementById('delete_id');
const delete_username = document.getElementById('delete_username');
const delete_lastname = document.getElementById('delete_lastname');
const delete_age = document.getElementById('delete_age');
const delete_email = document.getElementById('delete_email');
const delete_password = document.getElementById('delete_password');
const delete_roles = document.getElementById('rolesForDeleting')

const editForm = document.getElementById('formEdit');
const edit_id = document.getElementById('edit_id');
const edit_username = document.getElementById('edit_username');
const edit_lastname = document.getElementById('edit_lastname');
const edit_age = document.getElementById('edit_age');
const edit_roles = document.getElementById('rolesForEditing')
const edit_email = document.getElementById('edit_email');
const edit_password = document.getElementById('edit_password');

async function getAdmin() {
    let response = await fetch(adminUrl);

    if (response.ok) {
        let listUsers = await response.json();
        getAllUsers(listUsers);
    } else {
        alert("Error: " + response.status);
    }
}

function getAllUsers(listUsers) {
    const tbody = document.getElementById('my-table');

    let tr = '';
    for (let user of listUsers) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role.name.toString().substring(5))
        }
        tr += `<tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${roles}</td>
        <td>
            <button class="btn btn-info" style="color:white" data-bs-toggle="modal" 
            data-bs-target="#modalEdit"
            onclick="editModal(${user.id})">Edit</button>
        </td>
        <td>
            <button class="btn btn-danger" style="color:white" data-bs-toggle="modal" 
            data-bs-target="#modalDelete"
            onclick="deleteModal(${user.id})">Delete</button>
        </td>
    </tr>`
    }
    tbody.innerHTML = tr;
}


function userAuthInfo() {
    const tbody = document.getElementById('tableUserBody');
    const panel = document.getElementById("admin-header");
    fetch('http://localhost:8080/admin/viewUser')
        .then((res) => res.json())
        .then((user) => {
            let temp = '';
            let roles = [];
            for (let role of user.roles) {
                roles.push(" " + role.name.toString().substring(5))
            }
            temp += `<tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${roles}</td>
        </tr>`;
            tbody.innerHTML = temp;
            panel.innerHTML = `<h5>${user.email} with roles: ${roles}</h5>`
        });
}
async function createNewUser(event) {
    event.preventDefault();
    let roles = [];
    for (let i = 0; i < role_new.length; i++) {
        roles.push("ROLE_" + role_new[i].value);
    }
    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: form_addUser.username.value,
            lastName: form_addUser.lastname.value,
            age: form_addUser.age.value,
            email: form_addUser.email.value,
            password: form_addUser.password.value,
            roles: roles
        })
    }
    await fetch(urlNewUser, method).then(() => {
        form_addUser.reset();
        getAdmin();
        $("#home-tab").click();
    });
}

async function deleteModal(id) {
    const urlDelete = 'http://localhost:8080/admin/user?id=' + id;
    let modalDelete = await fetch(urlDelete);
    if (modalDelete.ok) {
        let userData =
            await modalDelete.json().then(user => {
                delete_id.value = `${user.id}`;
                delete_username.value = `${user.username}`;
                delete_lastname.value = `${user.lastName}`;
                delete_age.value = `${user.age}`;
                delete_email.value = `${user.email}`;
                delete_password.value = `${user.password}`
                delete_roles.value = `${user.roles}`
            })
    } else {
        alert(`Error, ${modalDelete.status}`)
    }
}

async function deleteUser() {
    deleteForm.addEventListener('submit', deletingUser);

    function deletingUser(event) {
        event.preventDefault();
        let urlDeleting = 'admin/delete?id=' + delete_id.value;
        let method = {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json"
            }
        }

        fetch(urlDeleting, method).then(() => {
            $('#deleteCloseBtn').click();
            getAdmin();
        });
    }
}
async function editModal(id) {
    const urlDataEdit = 'http://localhost:8080/admin/user?id=' + id;
    let modalEdit = await fetch(urlDataEdit);
    if (modalEdit.ok) {
        let userData =
            await modalEdit.json().then(async user => {
                edit_id.value = `${user.id}`;
                edit_username.value = `${user.username}`;
                edit_lastname.value = `${user.lastName}`;
                edit_age.value = `${user.age}`;
                edit_email.value = `${user.email}`;
                edit_password.value = `${user.password}`;
                edit_roles.value = `${user.roles}`;
            })
    } else {
        alert(`Error, ${modalEdit.status}`)
    }
}

async function editUser() {
    let urlEdit = 'http://localhost:8080/admin/update?id=' + edit_id.value;
    let roles = [];
    for (let i = 0; i < editForm.roles.options.length; i++) {
        if (editForm.roles.options[i].selected) {
            roles.push("ROLE_" + editForm.roles.options[i].value);
        }
    }
    let method = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: editForm.username.value,
            lastName: editForm.lastname.value,
            age: editForm.age.value,
            email: editForm.email.value,
            password: editForm.password.value,
            roles: roles
        })
    }

    await fetch(urlEdit, method).then(() => {
        $('#editCloseBtn').click();
        getAdmin();
    })
}
getAdmin();
userAuthInfo()

form_addUser.addEventListener('submit', createNewUser);
