window.onload =function calculateLocalStartDateAndLocalize() {
  let elements = document.getElementsByClassName('toLocalDate')
    for (const element of elements){
        let startDate = new Date(parseInt(element.textContent));
            startDate.setMinutes(startDate.getMinutes() - startDate.getTimezoneOffset());
        let tempDate=startDate.toISOString().slice(0,16);
            tempDate=tempDate.replaceAll("-",".");
            tempDate=tempDate.replaceAll("T"," ");
            element.textContent=tempDate;
        }
  localizeMatchStatus();
}

function localizeMatchStatus() {
  let elements = document.getElementsByClassName('status')
    for (const element of elements){
          switch(element.textContent) {
            case "available":
              element.textContent = "Fogadható";
              break;
            case "in_progress":
              element.textContent = "Folyamatban";
              break;
            case "finished":
              element.textContent = "Lezárult";
              break;
          }
    }
}