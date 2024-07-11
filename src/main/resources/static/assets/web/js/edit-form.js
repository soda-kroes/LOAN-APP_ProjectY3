$('#txtDateOfBirth').datetimepicker({
    lang: 'en',
    timepicker: false,
    format: 'd/m/Y',
    formatDate: 'Y/m/d'
});


$('#txtFirstName,#txtLastName').on('keyup', function () {
    $(this).val($(this).val().toUpperCase())
})

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