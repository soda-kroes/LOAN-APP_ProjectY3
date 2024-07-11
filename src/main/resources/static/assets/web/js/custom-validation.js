
function customFormValidation(inputId, errorMessageId, errorText) {
    var input = document.getElementById(inputId);
    var errorMessage = document.getElementById(errorMessageId);
    input.addEventListener('input', function() {
        var value = input.value;
        if (!/^[A-Za-z ]+$/.test(value)) {
            errorMessage.textContent = errorText;
            errorMessage.style.display = 'block';
            errorMessage.classList.add('invalid-feedback');
            input.setCustomValidity("Invalid input");
        } else {
            errorMessage.style.display = 'none';
            errorMessage.classList.remove('invalid-feedback');
            input.setCustomValidity(""); // Clear the custom validity
        }
    });
}
customFormValidation('txtFirstName', 'firstNameErrorMessage', 'The first name field allows only Latin characters.');
customFormValidation('txtLastName', 'lastNameErrorMessage', 'The last name field allows only Latin characters.');


function customFormValidationLegalId(inputId, errorMessageId, errorText) {
    var input = document.getElementById(inputId);
    var errorMessage = document.getElementById(errorMessageId);
    input.addEventListener('input', function() {
        var value = input.value;
        if (!/^\d{9}$/.test(value)) {
            errorMessage.textContent = errorText;
            errorMessage.style.display = 'block';
            errorMessage.classList.add('invalid-feedback');
            input.setCustomValidity("Invalid input");
        } else {
            errorMessage.style.display = 'none';
            errorMessage.classList.remove('invalid-feedback');
            input.setCustomValidity(""); // Clear the custom validity
        }
    });
}
customFormValidationLegalId('txtNationalityId', 'legalIdErrorMessage', 'Please enter a 9-digit number.');

$('#txtDateOfBirth').on('input change onSelectDate', function () {
    var input = document.getElementById('txtDateOfBirth');
    var errorMessage = document.getElementById('dateOfBirthErrorMessage');

    var selectedDate = $(this).val();
    var currentDate = new Date();

    // Regular expression pattern for date format dd/mm/yyyy
    var datePattern = /^(\d{2})\/(\d{2})\/(\d{4})$/;

    if (!datePattern.test(selectedDate)) {
        errorMessage.textContent = 'Invalid date of birth.';
        errorMessage.style.display = 'block';
        errorMessage.classList.add('invalid-feedback');
        input.setCustomValidity("Invalid input");
    }

    var dateParts = selectedDate.split('/');
    var formattedDate = dateParts[1] + '/' + dateParts[0] + '/' + dateParts[2];
    var birthDate = new Date(formattedDate);

    var age = currentDate.getFullYear() - birthDate.getFullYear();
    var monthDiff = currentDate.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < birthDate.getDate())) {
        age--;
    }

    if (age < 18) {
        errorMessage.textContent = 'The age must be greater than or equal to 18 years';
        errorMessage.style.display = 'block';
        errorMessage.classList.add('invalid-feedback');
        input.setCustomValidity("Invalid input");
    }else{
        errorMessage.style.display = 'none';
        errorMessage.classList.remove('invalid-feedback');
        input.setCustomValidity(""); // Clear the custom validity
    }

    console.log("Age: " + age);
    console.log("selectDate: " + selectedDate);
    console.log("birthDate: " + birthDate);
});


function customFormValidationEmail(inputId, errorMessageId, errorText) {
    var input = document.getElementById(inputId);
    var errorMessage = document.getElementById(errorMessageId);
    input.addEventListener('input', function() {
        var value = input.value;
        if (!/\S+@\S+\.\S+/.test(value)) {
            errorMessage.textContent = errorText;
            errorMessage.style.display = 'block';
            errorMessage.classList.add('invalid-feedback');
            input.setCustomValidity("Invalid email format");
        } else {
            errorMessage.style.display = 'none';
            errorMessage.classList.remove('invalid-feedback');
            input.setCustomValidity(""); // Clear the custom validity
        }
    });
}
customFormValidationEmail('txtEmail','emailErrorMessage','Invalid email format');
