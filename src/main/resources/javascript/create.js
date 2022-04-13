let loggedInUser;
checkLogin();

async function createUser() {
  let credentials = {
    first:document.getElementById('fName').value,
    last:document.getElementById('lName').value,
    username:document.getElementById('uName').value,
    password:document.getElementById('pass').value,
    role:document.getElementById('roles').value
  };
  let credentialJSON = JSON.stringify(credentials);

  let httpResp = await fetch('http://localhost:8080/create/acct',
      {method:'POST', body:credentialJSON});
  if (httpResp && httpResp.status === 200) {
      loggedInUser = await httpResp.json();
      sessionStorage.setItem('Auth-Token', loggedInUser.user);
      if (loggedInUser.role == 'Editor'){
        location.replace('../html/Editor-Page.html');
      }
      else if (loggedInUser.role == 'Author'){
        location.replace('../html/Author-Page.html');
      }
      else{
        location.replace('../html/Senior-Editor-Page.html');
      }
  }
  else{
    alert("Username already exists");
  }
}

async function createStory() {
  let userId = sessionStorage.getItem('Auth-Token');
  let credentials = {
    author:userId,
    title:document.getElementById('title').value,
    genre:document.getElementById('genre').value,
    blurb:document.getElementById('blurb').value,
    desc:document.getElementById('desc').value,
    len:document.getElementById('len').value,
    day:document.getElementById('day').value
  };
  let credentialJSON = JSON.stringify(credentials);

  let httpResp = await fetch('http://localhost:8080/create/story',
      {method:'POST', body:credentialJSON});
  if (httpResp && httpResp.status === 200) {
      location.replace('../html/Author-Page.html');
  }
      
}
