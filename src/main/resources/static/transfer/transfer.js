function transfer() {
  const toAccount = document.getElementById("toAccount").value.trim();
  const amount = document.getElementById("amount").value.trim();
  const message = document.getElementById("message");

  if (!toAccount || !amount) {
    message.style.color = "red";
    message.innerText = "Please fill all fields";
    return;
  }

  fetch(`http://localhost:8081/api/account/transfer?toAccount=${toAccount}&amount=${amount}`, {
    method: "POST",
    credentials: "include"
  })
  .then(res => res.text())
  .then(data => {
    message.style.color = "green";
    message.innerText = data;
    document.getElementById("toAccount").value = "";
    document.getElementById("amount").value = "";
  })
  .catch(() => {
    message.style.color = "red";
    message.innerText = "Transfer failed";
  });
}

function goBack() {
  window.location.href = "/dashboard/dashboard.html";
}
