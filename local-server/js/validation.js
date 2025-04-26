// Функция для валидации шага формы
function validateStep(step) {
    let isValid = true;

    switch (step) {
        case 1:
            const firstNameInput = document.getElementById('firstName');
            const lastNameInput = document.getElementById('lastName');

            if (!firstNameInput.value.trim()) {
                document.getElementById('firstName-error').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('firstName-error').style.display = 'none';
            }

            if (!lastNameInput.value.trim()) {
                document.getElementById('lastName-error').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('lastName-error').style.display = 'none';
            }
            break;

        case 2:
            const phoneNumberInput = document.getElementById('phoneNumber');
            const postOfficeInput = document.getElementById('postOffice');

            // Простая валидация телефона
            const phoneRegex = /^\+?[0-9]{10,13}$/;
            if (!phoneNumberInput.value.trim() || !phoneRegex.test(phoneNumberInput.value.trim())) {
                document.getElementById('phoneNumber-error').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('phoneNumber-error').style.display = 'none';
            }

            if (!postOfficeInput.value.trim()) {
                document.getElementById('postOffice-error').style.display = 'block';
                isValid = false;
            } else {
                document.getElementById('postOffice-error').style.display = 'none';
            }
            break;
    }

    return isValid;
}

// Функция для обновления сводки данных перед отправкой
function updateSummary() {
    const firstNameInput = document.getElementById('firstName');
    const lastNameInput = document.getElementById('lastName');
    const phoneNumberInput = document.getElementById('phoneNumber');
    const cityInput = document.getElementById('city');
    const postOfficeInput = document.getElementById('postOffice');

    document.getElementById('summary-firstName').textContent = firstNameInput.value;
    document.getElementById('summary-lastName').textContent = lastNameInput.value;
    document.getElementById('summary-phoneNumber').textContent = phoneNumberInput.value;
    document.getElementById('summary-city').textContent = cityInput.value.split(',')[0] || '-';
    document.getElementById('summary-postOffice').textContent = postOfficeInput.value;
}