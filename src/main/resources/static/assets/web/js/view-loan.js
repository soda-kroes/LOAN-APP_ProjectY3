$(document).ready(function () {
    getData();
});

$('#idMemberView').DataTable({
    data: Loans,
    columns: [
        {
            render: function (data, type, full, meta) {
                return meta.row + 1;
            }
        },
        {data: "firstName"},
        {data: "lastName"},
        {data: "gender"},
        {data: "maritalStatus"},
        {data: "dateOfBirth"},
        {data: "nationalityId"},
        {data: "address"},
        {
            render: function (data, type, full, meta) {
                return '<div class="dropdown">' +
                    '<button class="btn btn-sm btn- dropdown-toggle" style="font-size:12px; margin: 0px; padding:0px; border: none;" type="button" id="dropdownMenuButton_' + '" data-bs-toggle="dropdown" aria-expanded="false">' +
                    '<i class="fas fa-list-ul"></i>' +
                    '</button>' +
                    '<ul class="dropdown-menu dropdown-menu-sm" aria-labelledby="dropdownMenuButton_' + full.id_card + '">' +
                    '<li><button class="dropdown-item" style="font-size:12px;" onclick="edit(' + full.id_card + ')"><i class="far fa-edit"></i> Edit</button></li>' +
                    '<li><button class="dropdown-item" style="font-size:12px;" onclick="closeUser(' + full.id_card + ')"><i class="fas fa-lock"></i> Disable</button></li>' +
                    '<li><button class="dropdown-item" style="font-size:12px;" onclick="enableUser(' + full.id_card + ')"><i class="fas fa-unlock"></i> Enable</button></li>' +
                    '<li><button class="dropdown-item" style="font-size:12px;" onclick="resetPassword(' + full.id_card + ')"><i class="fas fa-power-off"></i> Reset</button></li>' +
                    '<li><button class="dropdown-item" style="font-size:12px;" onclick="remove(' + full.id_card + ')"><i class="fas fa-trash"></i> Remove</button></li>' +
                    '</ul>' +
                    '</div>';
            },
        }
        // {
        //     render: function (data, type, full, meta) {
        //         return '<button class="btn btn-sm btn-danger" style="margin-right: 3px; color:white; font-size:8px;" onclick="remove(' + full.id + ')"><i class="fas fa-trash" style="color: white;"></i> Delete</button>' +
        //                '<button class="btn btn-sm btn-primary" style="font-size:8px; margin-right: 3px;" onclick="edit(' + full.id + ')"><i class="fa-regular fa-pen-to-square"></i> Edit</button>' +
        //                '<button class="btn btn-sm btn-info" style="font-size:8px;" onclick="details(' + full.id + ')"><i class="fa-regular fa-pen-to-square"></i> Details</button>';
        //     }
        // }
    ]
});

var table = $('#idMemberView').DataTable();
var Loans = [];

function getData() {
    // showLoading();
    $.ajax({
        type: "GET",
        url: "api/v1/loan/getLoan",
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            if (response.status.errorCode === 200) {
                // alert('ok');
                console.log(response);
                // hideLoading();
                Loans = [];
                Loans = response.data;

                table.clear();
                table.rows.add(Loans);
                table.draw();
            } else {

            }
        }
    });
}

function remove(loanId) {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            showLoading();
            $.ajax({
                type: "DELETE", // Use the appropriate HTTP method for deletion
                url: `api/v1/loan/delete/${loanId}`, // Use template literals for string interpolation
                contentType: 'application/json',
                dataType: 'json',
                success: function (response) {
                    if (response.status.errorCode === 200) {
                        setTimeout(function () {
                            hideLoading();
                            getData();
                            Swal.fire({
                                title: "Deleted!",
                                text: "Your record has been deleted.",
                                icon: "success"
                            });
                        }, 2000); // Delay modal
                    } else {
                        setTimeout(function () {
                            hideLoading();
                            Swal.fire({
                                title: "Bad Request!",
                                text: "An error occurred while deleting the file.",
                                icon: "error" // Use 'error' icon for error message
                            });
                        }, 2000); // Delay modal
                    }
                },
                error: function (xhr, status, error) {
                    // Handle any errors that occur during the request
                    console.error(xhr.responseText);
                    setTimeout(function () {
                        hideLoading();
                        Swal.fire({
                            title: "Error!",
                            text: "An error occurred while deleting the file.",
                            icon: "error"
                        });
                    }, 2000); // Delay modal
                }
            });
        }
    });
}

function edit(loanId){
    window.location.href = `/edit?loanId=${loanId}`;
}
function details(loanId){
    window.location.href = `/detail?loanId=${loanId}`;
}



//------------------------------------- For Loading -------------------------------
function showLoading() {
    var loadingIndicatorOverlay = document.createElement('div');
    loadingIndicatorOverlay.classList.add('loading-indicator-overlay');

    var loadingIndicator = document.createElement('div');
    loadingIndicator.classList.add('loading-indicator');

    var loadingText = document.createElement('div');
    loadingText.classList.add('loading-text');
    loadingText.textContent = 'Please wait...';

    loadingIndicatorOverlay.appendChild(loadingIndicator);
    loadingIndicatorOverlay.appendChild(loadingText);
    document.body.appendChild(loadingIndicatorOverlay);

    return true;
}

function hideLoading() {
    var loadingIndicator = document.querySelector('.loading-indicator-overlay');
    if (loadingIndicator) {
        loadingIndicator.remove();
    }
}