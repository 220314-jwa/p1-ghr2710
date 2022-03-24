function reveal(){
  var x = document.getElementById("pass");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}

function direct_to_users(){
  location.replace('file://../html/Account-Creation-Page.html');
}