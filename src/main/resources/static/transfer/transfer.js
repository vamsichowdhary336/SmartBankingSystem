function transfer() {
  const toAccount = document.getElementById("toAccount").value.trim();
  const amountInput = document.getElementById("amount").value.trim();
  const message = document.getElementById("message");

  // Reset message
  message.innerText = "";
  message.style.color = "red";

  if (!toAccount || !amountInput) {
    message.innerText = "Please fill all fields";
    return;
  }

  const amount = Number(amountInput);
  if (isNaN(amount) || amount <= 0) {
    message.innerText = "Enter a valid amount";
    return;
  }

  fetch(`http://localhost:8081/api/account/transfer?toAccount=${toAccount}&amount=${amount}`, {
    method: "POST",
    credentials: "include"
  })
    .then(res => res.text().then(text => ({ ok: res.ok, text })))
    .then(({ ok, text }) => {
      if (!ok) {
        message.innerText = text || "Transfer failed";
        return;
      }

      message.style.color = "green";
      message.innerText = text;

      document.getElementById("toAccount").value = "";
      document.getElementById("amount").value = "";
    })
    .catch(() => {
      message.innerText = "Server error. Please try again.";
    });
}

function goBack() {
  window.location.href = "/dashboard/dashboard.html";
}
