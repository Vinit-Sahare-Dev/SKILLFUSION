


// Final Submit Button Functionality
const finalSubmitBtn = document.getElementById('finalSubmitBtn');

finalSubmitBtn.addEventListener('click', function() {
    const resumeUploaded = resumeList.innerHTML !== '<p>No resume uploaded yet.</p>'; // Check if a resume is uploaded

    if (resumeUploaded) {
        // Here you can add code to store the data (e.g., send it to a server)
        console.log('Data submitted successfully!');
        alert('Your data has been submitted successfully!'); // Confirmation alert
    } else {
        alert('Please upload a resume before submitting.'); // Alert if no resume is uploaded
    }
});


// Resume Section
const addResumeBtn = document.getElementById('addResumeBtn');
const resumeForm = document.getElementById('resumeForm');
const resumeUpload = document.getElementById('resumeUpload');
const resumeList = document.getElementById('resumeList');
const uploadResumeForm = document.getElementById('uploadResumeForm');

// Show the form when "Add Resume" button is clicked
addResumeBtn.addEventListener('click', function() {
    resumeForm.style.display = 'block'; // Show the resume upload form
});

// Handle resume upload
uploadResumeForm.addEventListener('submit', function(e) {
    e.preventDefault(); // Prevent the default form submission

    // Get the uploaded file
    const file = resumeUpload.files[0];
    if (file && (file.type === 'application/pdf' || file.name.endsWith('.pdf') || file.name.endsWith('.doc') || file.name.endsWith('.docx'))) {
        // Create a download link for the uploaded resume
        const resumeLink = document.createElement('a');
        resumeLink.href = URL.createObjectURL(file);
        resumeLink.textContent = `Download Resume (${file.name})`;
        resumeLink.download = file.name;

        // Replace the current text with the new resume link
        resumeList.innerHTML = ''; // Clear previous content
        resumeList.appendChild(resumeLink); // Add the new link

        // Hide the form after submission
        resumeForm.style.display = 'none';
        updateCompletionStatus(); // Update completion status if necessary
    } else {
        alert('Please upload a valid PDF or DOC file.'); // Alert for invalid file types
    }
});


const addSkillBtn = document.getElementById('addSkillBtn');
const examForm = document.getElementById('examForm');
const skillsList = document.getElementById('skillsList');
const submitExamBtn = document.getElementById('submitExamBtn');

// Show the exam selection form when "Add Exam" button is clicked
addSkillBtn.addEventListener('click', function() {
    examForm.style.display = 'block'; // Show the exam form
});

// Handle exam submission
submitExamBtn.addEventListener('click', function() {
    const selectedExam = document.getElementById('examSelection').value;

    // Create a new exam entry
    const newExamEntry = document.createElement('span');
    newExamEntry.textContent = selectedExam;
    newExamEntry.className = 'skill-entry'; // Optional: Add a class for styling

    // Create the delete (cross) button
    const closeButton = document.createElement('button');
    closeButton.textContent = 'x';
    closeButton.className = 'close-btn';

    // Remove the exam on close button click
    closeButton.addEventListener('click', function() {
        skillsList.removeChild(newExamEntry);
        updateCompletionStatus();
    });

    // Append the close button to the exam entry
    newExamEntry.appendChild(closeButton);

    // Add the new entry to the list
    skillsList.appendChild(newExamEntry);

    // Hide the form and reset fields
    examForm.style.display = 'none';
    document.getElementById('examSelection').selectedIndex = 0; // Reset selection

    // Update completion status
    updateCompletionStatus();
});

   

// Handle form submission for Graduation
/*document.querySelector('#graduationForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const school = document.getElementById('grad-school').value;
    const degree = document.getElementById('grad-degree').value;
    const branch = document.getElementById('grad-branch').value;
    const percentage = document.getElementById('grad-percentage').value;
    const year = document.getElementById('grad-year').value;

    const graduationInfo = document.querySelector('.graduation-info');
    graduationInfo.innerHTML = `
        <p>${degree}(${branch}) from ${school}</p>
        <p>${percentage}%, Graduating in ${year}</p>
    `;

    // Hide the "Add Graduation Details" link
    const graduationLink = document.querySelector('.edu-link[data-target="#graduationForm"]');
    graduationLink.style.display = 'none';

    // Close the popup
    closePopup(document.getElementById('graduationForm'));
});

*/
// Handle form submission for Class XII
/*
document.querySelector('#classXIIForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const school = document.getElementById('xii-school').value;
    const percentage = document.getElementById('xii-percentage').value;
    const year = document.getElementById('xii-year').value;

    const classXIIInfo = document.querySelector('.class-xii-info');
    classXIIInfo.innerHTML = `
        <p>HSC from ${school}</p>
        <p>${percentage}%, ${year}</p>
    `;

    // Hide the "Add Class XII Details" link
    const classXIIlink = document.querySelector('.edu-link[data-target="#classXIIForm"]');
    classXIIlink.style.display = 'none';

    // Close the popup
    closePopup(document.getElementById('classXIIForm'));
});
*/
// Handle form submission for Class X
/*
document.querySelector('#classXForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const school = document.getElementById('x-school').value;
    const percentage = document.getElementById('x-percentage').value;
    const year = document.getElementById('x-year').value;

    const classXInfo = document.querySelector('.class-x-info');
    classXInfo.innerHTML = `
        <p>SSC from ${school}</p>
        <p>${percentage}%, ${year}</p>
    `;

    // Hide the "Add Class X Details" link
    const classXlink = document.querySelector('.edu-link[data-target="#classXForm"]');
    classXlink.style.display = 'none';

    // Close the popup
    closePopup(document.getElementById('classXForm'));
});
*/

// Get the education links
const eduLinks = document.querySelectorAll('.edu-link');
const overlay = document.getElementById('overlay');

// Add click event listeners for each education link to show the corresponding form
eduLinks.forEach(link => {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        const target = document.querySelector(this.getAttribute('data-target'));
        showPopup(target);
    });
});

// Get all close buttons and add event listeners to close popups
const closeButtons = document.querySelectorAll('.close-popup');
closeButtons.forEach(button => {
    button.addEventListener('click', function() {
        const popup = this.parentElement;
        closePopup(popup);
    });
});

// Function to show popup
function showPopup(popup) {
    popup.style.display = 'block';
    overlay.style.display = 'block'; // Dim background
}

// Function to close popup
function closePopup(popup) {
    popup.style.display = 'none';
    overlay.style.display = 'none'; // Remove background dim
}

// Close the popup if user clicks outside the form
overlay.addEventListener('click', function() {
    document.querySelectorAll('.popup-form').forEach(form => {
        form.style.display = 'none';
    });
    overlay.style.display = 'none';
});

// Profile photo upload logic
const addPhotoBtn = document.getElementById('add-photo-btn');
const photoUpload = document.getElementById('photoUpload');
const profileImage = document.getElementById('profileImage');

addPhotoBtn.addEventListener('click', () => {
    photoUpload.click(); // Trigger file input click
});

photoUpload.addEventListener('change', function() {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            profileImage.src = e.target.result; // Update profile photo with uploaded image
        };
        reader.readAsDataURL(file);
    }
});

// Show or hide the branch dropdown based on education level
educationLevel.addEventListener('change', function() {
    if (this.value === "Bachelors") {
        branchDropdown.style.display = 'block';
    } else {
        branchDropdown.style.display = 'none';
    }
});

// Handle form submission
newEducationForm.addEventListener('submit', function(e) {
    e.preventDefault();

    // Get form values
    const selectedBranch = branchDropdown.style.display === 'block' ? document.getElementById('branch').value : 'N/A';
    const institution = document.getElementById('institution').value;
    const percentage = document.getElementById('percentage').value;
    const year = document.getElementById('year').value;

    // Create a new education entry
    const newEducationEntry = document.createElement('p');
    newEducationEntry.textContent = `${educationLevel.value} (${selectedBranch}) from ${institution} (${year}) - ${percentage}%`;

    // Add the new entry to the list
    educationList.appendChild(newEducationEntry);

    // Hide the form and reset fields
    educationForm.style.display = 'none';
    newEducationForm.reset();

    // Update completion status
    updateCompletionStatus();
});


// Accomplishments Section
const addAccomplishmentBtn = document.getElementById('addAccomplishmentBtn');
const accomplishmentForm = document.getElementById('accomplishmentForm');
const accomplishmentList = document.getElementById('accomplishmentList');
const submitAccomplishmentBtn = document.getElementById('submitAccomplishmentBtn');

// Show the accomplishment form when "Add Accomplishment" button is clicked
addAccomplishmentBtn.addEventListener('click', function() {
    accomplishmentForm.style.display = 'block';
});

// Handle accomplishment submission
submitAccomplishmentBtn.addEventListener('click', function() {
    const accomplishment = document.getElementById('accomplishmentInput').value;

    // Create a new accomplishment entry
    const newAccomplishmentEntry = document.createElement('span');
    newAccomplishmentEntry.textContent = accomplishment;
    newAccomplishmentEntry.className = 'accomplishment-entry'; // Optional: Add a class for styling

    // Create the delete (cross) button
    const closeButton = document.createElement('button');
    closeButton.textContent = 'x';
    closeButton.className = 'close-btn';

    // Remove the accomplishment on close button click
    closeButton.addEventListener('click', function() {
        accomplishmentList.removeChild(newAccomplishmentEntry);
        updateCompletionStatus();
    });

    // Append the close button to the accomplishment entry
    newAccomplishmentEntry.appendChild(closeButton);

    // Add the new entry to the list
    accomplishmentList.appendChild(newAccomplishmentEntry);

    // Hide the form and reset the input field
    accomplishmentForm.style.display = 'none';
    document.getElementById('accomplishmentInput').value = ''; // Clear input

    // Update completion status
    updateCompletionStatus();
});
