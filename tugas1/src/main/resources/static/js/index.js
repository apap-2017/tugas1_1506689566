// Wait for the DOM to be ready
$(function() {
	
	$.validator.addMethod(
		    "australianDate",
		    function(value, element) {
		        // put your own logic here, this is just a (crappy) example
		        return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
		    },
		    "Please enter a date in the format dd/mm/yyyy."
		);
  // Initialize form validation on the registration form.
  // It has the name attribute "registration"
  $("form[name='add']").validate({
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      nama: "required",
      id_keluarga: "required",
      tanggal_lahir : {
          australianDate : true
      },
    
    },
    // Specify validation error messages
    messages: {
      nama: "Please enter your firstname",
      id_keluarga : "Please enter your id_keluarga",
      date:  "Please provide a date",
       
     
    },
    // Make sure the form is submitted to the destination defined
    // in the "action" attribute of the form when valid
    submitHandler: function(form) {
      form.submit();
    }
  });
});