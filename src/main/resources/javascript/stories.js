let storyTable = document.getElementById('story');
checkLogin();

async function sendSeniorRequest() {
    let httpResponse = await fetch('http://localhost:8080/stories');

    if (httpResponse && httpResponse.status===200) {
        let responseBody = await httpResponse.json();
        showSeniorStories(responseBody);
    }
}
async function sendAuthorRequest() {
  let userId = sessionStorage.getItem('Auth-Token');
  let httpResponse = await fetch('http://localhost:8080/stories/author/' + userId);

  if (httpResponse && httpResponse.status===200) {
      let responseBody = await httpResponse.json();
      showAuthorStories(responseBody);
  }
}
async function sendEditorRequest() {
  let userId = sessionStorage.getItem('Auth-Token');
  let httpResponse = await fetch('http://localhost:8080/stories/editor/' + userId);

  if (httpResponse && httpResponse.status===200) {
      let responseBody = await httpResponse.json();
      showEditorStories(responseBody);
  }
}

function showSeniorStories(storyArr) {
    storyTable.innerHTML = `<tr>
    <th>Author</th>
    <th>Editor</th>
    <th>Title</th>
    <th>Genre</th>
    <th>Blurb</th>
    <th>Description</th>
    <th>Completion Date</th>
    <th>Status</th>
    </tr>`;
    
    // for each story in the story array from the http response
    for (let story of storyArr) {
        // these stories are coming from Java so the fields are the same
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${story.authorName}</td>
            <td>${story.editorName}</td>
            <td>${story.title}</td>
            <td>${story.genre}</td>
            <td>${story.blurb}</td>
            <td>${story.desc}</td>
            <td>${story.stringDate}</td>
            <td>${story.status}</td>
            <td><button id="approve${story.title}">approve</button></td>
            <td><button id="reject${story.title}">reject</button></td>
            <td><button id="assign${story.title}">assign</button></td>
        `;
        // add the row to the table
        storyTable.appendChild(row);
        let goodBtn = document.getElementById('approve' + story.title);
        goodBtn.addEventListener('click', function(){seniorApprove(story.authorName);});
        let badBtn = document.getElementById('reject' + story.title);
        badBtn.addEventListener('click', function(){seniorReject(story.authorName);});
        let asBtn = document.getElementById('assign' + story.title);
        asBtn.addEventListener('click', function(){goToSeniorEdit(story.authorName);});
    }
}

function showAuthorStories(storyArr) {
    storyTable.innerHTML = `<tr>
    <th>Author</th>
    <th>Editor</th>
    <th>Title</th>
    <th>Genre</th>
    <th>Blurb</th>
    <th>Description</th>
    <th>Completion Date</th>
    <th>Status</th>
    <th>Est Length</th>
    </tr>`;
    
    // for each story in the story array from the http response
    for (let story of storyArr) {
        // these stories are coming from Java so the fields are the same
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${story.authorName}</td>
            <td>${story.editorName}</td>
            <td>${story.title}</td>
            <td>${story.genre}</td>
            <td>${story.blurb}</td>
            <td>${story.desc}</td>
            <td>${story.stringDate}</td>
            <td>${story.status}</td>
            <td>${story.length}</td>
            <td><button id="edit${story.title}">edit</button></td>
        `;
        // add the row to the table
        storyTable.appendChild(row);
        let editBtn = document.getElementById('edit' + story.title);
        editBtn.addEventListener('click', goToEdit);
    }
}

function showEditorStories(storyArr) {
    storyTable.innerHTML = `<tr>
    <th>Author</th>
    <th>Editor</th>
    <th>Title</th>
    <th>Genre</th>
    <th>Blurb</th>
    <th>Description</th>
    <th>Completion Date</th>
    <th>Status</th>
    <th>Est Length</th>
    </tr>`;
    
    // for each story in the story array from the http response
    for (let story of storyArr) {
        // these stories are coming from Java so the fields are the same
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${story.authorName}</td>
            <td>${story.editorName}</td>
            <td>${story.title}</td>
            <td>${story.genre}</td>
            <td>${story.blurb}</td>
            <td>${story.desc}</td>
            <td>${story.stringDate}</td>
            <td>${story.status}</td>
            <td>${story.length}</td>
            <td><button id="approve${story.title}">approve</button></td>
            <td><button id="reject${story.title}">reject</button></td>
        `;
        // add the row to the table
        storyTable.appendChild(row);
        let goodBtn = document.getElementById('approve' + story.title);
        goodBtn.addEventListener('click', editorApprove);
        let badBtn = document.getElementById('reject' + story.title);
        badBtn.addEventListener('click', editorReject);
    }
}