$(document).ready(function () {
    //sweatalert
        window.Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
    })

    $('#txtDateOfBirth').datetimepicker({
        lang: 'en',
        timepicker: false,
        format: 'd/m/Y',
        formatDate: 'Y/m/d'
    });
    getLoanType();
    getBranch();
    getAddress();
});

var legalImageValue = null;
$('#legalIdImage').on('change', function (evt) {
    const reader = new FileReader();
    reader.onload = function () {
        $('#legalIdImageDisplay').attr('src', event.target.result);
        // alert(event.target.result);
        console.log(event.target.result);
        const base64String = event.target.result.split(',')[1];
        legalImageValue = base64String;
    };
    reader.readAsDataURL(evt.target.files[0]);
});

var selfieImageValue = null;
$('#frontImage').on('change', function (evt) {
    const reader = new FileReader();
    reader.onload = function () {
        $('#imgFrontImageDisplay').attr('src', reader.result)
        const base64String = event.target.result.split(',')[1];
        selfieImageValue = base64String;
    };
    reader.readAsDataURL(evt.target.files[0]);
});

// get value LoanTypeId
var LoanTypeId;
$("#ddlLoanType").change(function () {
    var selectOptionValue = $(this).val();
    LoanTypeId = selectOptionValue;
    // alert(LoanTypeId);
    $.ajax({
        type: "GET",
        url: "/api/v1/master-data/getInterestRate/" + LoanTypeId,
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            console.log(response);
             $('#txtInterestRate').val(response.data);
        },
        error: function (xhr, status, error) {
            console.error(xhr.responseText);
        }
    });
});


function getLoanType() {
    $.ajax({
        type: "GET",
        url: "api/v1/master-data/getLoanType",
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            // console.log(response);
            if (response.data.length > 0) {
                var ddlLoanType = $('#ddlLoanType');
                ddlLoanType.empty();

                var defaultOption = new Option('--- Choose Loan Type ---', '');
                defaultOption.disabled = true; // Disable the option
                defaultOption.selected = true; // Select the option
                ddlLoanType.append(defaultOption);

                for (var i = 0; i < response.data.length; i++) {
                    var LoanType = response.data[i];
                    var loanTypeValue = LoanType.id;
                    var loanTypeText = LoanType.name;
                    var option = new Option(loanTypeText, loanTypeValue);
                    ddlLoanType.append(option); // Append the option to the select element
                }
            }
        }
    });
}

function getBranch() {
    $.ajax({
        type: "GET",
        url: "api/v1/master-data/getBranch",
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            console.log(response);
            if (response.data.length > 0) {
                var ddlBranch = $('#ddlBranch');
                ddlBranch.empty();

                var defaultOption = new Option('--- Choose Branch ---', '');
                defaultOption.disabled = true; // Disable the option
                defaultOption.selected = true; // Select the option
                ddlBranch.append(defaultOption);

                for (var i = 0; i < response.data.length; i++) {
                    var Branch = response.data[i];
                    var branchValue = Branch.code;
                    var branchText = Branch.name;
                    var option = new Option(branchText, branchValue);
                    ddlBranch.append(option); // Append the option to the select element
                }
            }
        }
    });
}

function getAddress() {
    $.ajax({
        type: "GET",
        url: "api/v1/master-data/getBranch",
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            console.log(response);
            if (response.data.length > 0) {
                var ddlAddress = $('#ddlAddress');
                ddlAddress.empty();

                var defaultOption = new Option('--- Choose Address ---', '');
                defaultOption.disabled = true; // Disable the option
                defaultOption.selected = true; // Select the option
                ddlAddress.append(defaultOption);

                for (var i = 0; i < response.data.length; i++) {
                    var Branch = response.data[i];
                    var branchValue = Branch.name;
                    var branchText = Branch.name;
                    var option = new Option(branchText, branchValue);
                    ddlAddress.append(option); // Append the option to the select element
                }

            }
        }
    });
}

$('#txtFirstName,#txtLastName').on('keyup', function () {
    $(this).val($(this).val().toUpperCase())
})

var form = document.getElementsByClassName('need-novalidate-new');
var validation = Array.prototype.filter.call(form, function(forms) {
    forms.addEventListener('submit', function(event) {
        if (forms.checkValidity() === false) {
            event.preventDefault();
        } else {
            event.preventDefault();
            // Check which button triggered the form submission
            var submitButtonId = event.submitter.id;
            if (submitButtonId === 'btnSubmit') {
                 // alert('you are submits');
                var firstName = $('#txtFirstName').val();
                var lastName = $('#txtLastName').val();
                var gender = $('#ddlGender').val();

                var dateOfBirth = $('#txtDateOfBirth').val();
                // Split the date string by the hyphen (-) to extract day, month, and year
                var parts = dateOfBirth.split('/');
                var day = parts[0];
                var month = parts[1];
                var year = parts[2];

                // Create a new date string in the desired format (YYYY-MM-DD)
                var formattedDateOfBirth = year + '-' + month + '-' + day;

                var email = $('#txtEmail').val();
                var tell = $('#txtTell').val();
                var address = $('#ddlAddress').val();
                var maritalStatus = $('#ddlMaritalStatus').val();
                var nationality = $('#txtNationalityId').val();
                var currency = $('#ddlCurrency').val();
                var amount = $('#txtAmount').val();
                var loanTerm = $('#txtLoanTerm').val();
                var loanTypeId = $('#ddlLoanType').val();
                var branch = $('#ddlBranch').val();

                var json = {
                    "request_no": "",
                    "last_name": lastName,
                    "first_name": firstName,
                    "marital_status": maritalStatus,
                    "date_of_birth": formattedDateOfBirth,
                    "phone_number": tell,
                    "gender": gender,
                    "email": email,
                    "nationality_id": nationality,
                    "nationality_image": legalImageValue,
                    "selfie_image": selfieImageValue,
                    "address": address,
                    "loan_amount": parseFloat(amount),
                    "loan_term": parseInt(loanTerm),
                    "currency": currency,
                    "created_date": "",
                    "branch_code": branch,
                    "loan_type_id": loanTypeId
                };
                console.log(JSON.stringify(json));
                showLoading();
                $.ajax({
                    type: "POST",
                    url: "api/v1/loan/register",
                    contentType: 'application/json',
                    dataType: 'json',
                    data: JSON.stringify(json),
                    processData: false,
                    success: function (response) {
                        console.log(response);
                        if (response.status.errorCode === 200) {
                            setTimeout(function () {
                                hideLoading();
                                Swal.fire({
                                    title: "Congratulations!",
                                    text: "loan has been successfully created.",
                                    icon: "success"
                                });
                                // Reset the form
                                $('#frontImage').val(null);
                                $('#imgFrontImageDisplay').attr('src', '/assets/images/image_selfie.jpg');

                                $('#legalIdImage').val(null);
                                $('#legalIdImageDisplay').attr('src', '/assets/images/National_ID_selfie.png');
                                forms.reset();
                                forms.classList.remove('was-validated');
                            }, 2000); // Delay modal
                        } else {
                            hideLoading();
                            Swal.fire({
                                title: "Error",
                                text: "An error occurred while processing your request.",
                                icon: "error"
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        hideLoading();
                        Swal.fire({
                            title: "Error",
                            text: "An error occurred while processing your request.",
                            icon: "error"
                        });
                        console.error(xhr, status, error);
                    }
                });
            }
        }
        forms.classList.add('was-validated');
    }, false);
});

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