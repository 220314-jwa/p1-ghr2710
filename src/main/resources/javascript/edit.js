let loggedInUser;
checkLogin();

async function goToEdit() {
  let userId = sessionStorage.getItem('Auth-Token');
  let title = event.target.id;
  title = title.slice(4);
  let httpResponse = await fetch('http://localhost:8080/stories/get/' + userId + "/" + title);

  if (httpResponse && httpResponse.status===200) {
    let responseBody = await httpResponse.json();
    location.replace('../html/Story-Edit-Page.html');
    setValues(responseBody);
  }
}

async function goToSeniorEdit(name) {
  let title = event.target.id;
  title = title.slice(6);
  sessionStorage.setItem('Old-Token', title);
  sessionStorage.setItem('Saved-Token', name);
  location.replace('../html/Assign-Editor-Page.html');
}

function setValues(storyArr){
  let titleInput = document.getElementById('title');
  let genreInput = document.getElementById('genre');
  let blurbInput = document.getElementById('blurb');
  let descInput = document.getElementById('desc');
  let lenInput = document.getElementById('len');
  let dayInput = document.getElementById('day');
  for (let story of storyArr){
    sessionStorage.setItem('Old-Token', story.title);
    titleInput.value = story.title;
    genreInput.value = story.genre;
    blurbInput.value = story.blurb;
    descInput.value = story.desc;
    lenInput.value = story.length;
    dayInput.value = story.CompDate;
  }
}

async function editStory() {
  let userId = sessionStorage.getItem('Auth-Token');
  let oldTitle = sessionStorage.getItem('Old-Token');
  let credentials = {
    author:userId,
    old:oldTitle,
    title:document.getElementById('title').value,
    genre:document.getElementById('genre').value,
    blurb:document.getElementById('blurb').value,
    desc:document.getElementById('desc').value,
    len:document.getElementById('len').value,
    day:document.getElementById('day').value
  };
  let credentialJSON = JSON.stringify(credentials);

  let httpResp = await fetch('http://localhost:8080/edit/story',
      {method:'POST', body:credentialJSON});
  if (httpResp && httpResp.status === 200) {
      location.replace('../html/Author-Page.html');
  }
}

async function assignEditor() {
  let oldTitle = sessionStorage.getItem('Old-Token');
  let savedUser = sessionStorage.getItem('Saved-Token');
  let credentials = {
    author:savedUser,
    old:oldTitle,
    assign:document.getElementById('editor').value
  };
  let credentialJSON = JSON.stringify(credentials);

  let httpResp = await fetch('http://localhost:8080/assign',
      {method:'POST', body:credentialJSON});
  if (httpResp && httpResp.status === 200) {
      location.replace('../html/Senior-Editor-Page.html');
  }
}

async function editorApprove() {
  let userId = sessionStorage.getItem('Auth-Token');
  let title = event.target.id;
  title = title.slice(7);
  let httpResponse = await fetch('http://localhost:8080/stories/editors/approve/' + userId + "/" + title);

  if (httpResponse && httpResponse.status===200) {
    location.replace('../html/Editor-Page.html');
  }
}

async function editorReject() {
  let userId = sessionStorage.getItem('Auth-Token');
  let title = event.target.id;
  title = title.slice(6);
  let httpResponse = await fetch('http://localhost:8080/stories/editors/reject/' + userId + "/" + title);

  if (httpResponse && httpResponse.status===200) {
    location.replace('../html/Editor-Page.html');
  }
}

async function seniorApprove(name) {
  let userId = name
  let title = event.target.id;
  title = title.slice(7);
  let httpResponse = await fetch('http://localhost:8080/stories/senior/approve/' + userId + "/" + title);

  if (httpResponse && httpResponse.status===200) {
    location.replace('../html/Senior-Editor-Page.html');
  }
}

async function seniorReject(name) {
  let userId = name
  let title = event.target.id;
  title = title.slice(6);
  let httpResponse = await fetch('http://localhost:8080/stories/senior/reject/' + userId + "/" + title);

  if (httpResponse && httpResponse.status===200) {
    location.replace('../html/Senior-Editor-Page.html');
  }
}