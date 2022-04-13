let loggedInUser;


async function checkLogin() {
  let userId = sessionStorage.getItem('Auth-Token');
  let httpResp = await fetch('http://localhost:8080/users/' + userId);
  if (httpResp && httpResp.status === 200) {
      loggedInUser = await httpResp.json();
  }
}


async function logIn() {
  let credentials = {
      username:document.getElementById('uName').value,
      password:document.getElementById('pass').value
  };
  let credentialJSON = JSON.stringify(credentials);

  let httpResp = await fetch('http://localhost:8080/login',
      {method:'POST', body:credentialJSON});
  if (httpResp && httpResp.status === 200) {
      loggedInUser = await httpResp.json();
      sessionStorage.setItem('Auth-Token', loggedInUser.user);
      await checkLogin();
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
}
